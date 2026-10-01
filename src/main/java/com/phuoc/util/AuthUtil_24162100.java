package com.phuoc.util;

import com.phuoc.model.Users_24162100;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Tien ich xac thuc dua tren JWT (thay cho HttpSession):
 *  - Lay token tu header "Authorization: Bearer <token>" (goi API/Postman)
 *    hoac tu cookie HttpOnly "access_token" (trinh duyet).
 *  - Giai ma token thanh Users (khong truy van DB) va gan vao request attribute
 *    "currentUser" / "role" de JSP dung nhu truoc.
 */
public final class AuthUtil_24162100 {

    public static final String COOKIE_NAME = "access_token";
    private static final String ATTR_USER = "currentUser";
    private static final String ATTR_ROLE = "role";
    private static final String ATTR_CHECKED = "jwt.checked";

    private AuthUtil_24162100() {
    }

    /** Lay JWT tu request: uu tien header Bearer, sau do toi cookie. Tra ve null neu khong co. */
    public static String extractToken(HttpServletRequest req) {
        String auth = req.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            return auth.substring(7).trim();
        }
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (COOKIE_NAME.equals(c.getName()) && c.getValue() != null && !c.getValue().isBlank()) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Tra ve user hien tai neu JWT hop le (dung chu ky, chua het han), nguoc lai null.
     * Ket qua duoc cache trong request de khong giai ma nhieu lan.
     */
    public static Users_24162100 getCurrentUser(HttpServletRequest req) {
        if (req.getAttribute(ATTR_CHECKED) != null) {
            return (Users_24162100) req.getAttribute(ATTR_USER);
        }
        req.setAttribute(ATTR_CHECKED, Boolean.TRUE);

        String token = extractToken(req);
        if (token == null) {
            return null;
        }
        try {
            Claims c = JwtUtil_24162100.parseToken(token);
            Users_24162100 u = new Users_24162100();
            u.setUserId(c.get("uid", Integer.class));
            u.setRoleId(c.get("rid", Integer.class));
            u.setUsername(c.getSubject());
            u.setFullname(c.get("name", String.class));
            req.setAttribute(ATTR_USER, u);
            req.setAttribute(ATTR_ROLE, c.get("role", String.class));
            return u;
        } catch (JwtException | IllegalArgumentException | NullPointerException e) {
            // token sai chu ky / het han / bi sua / thieu claim -> coi nhu chua dang nhap
            return null;
        }
    }

    /** Ghi JWT vao cookie HttpOnly (JavaScript/XSS khong doc duoc), SameSite=Lax. */
    public static void addTokenCookie(HttpServletRequest req, HttpServletResponse resp, String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(req.isSecure()); // chi gui qua HTTPS khi site chay HTTPS
        cookie.setPath(cookiePath(req));
        cookie.setMaxAge((int) (JwtUtil_24162100.getExpirationMs() / 1000));
        cookie.setAttribute("SameSite", "Lax");
        resp.addCookie(cookie);
    }

    /** Xoa cookie JWT (dang xuat / token khong con hop le). */
    public static void clearTokenCookie(HttpServletRequest req, HttpServletResponse resp) {
        Cookie cookie = new Cookie(COOKIE_NAME, "");
        cookie.setHttpOnly(true);
        cookie.setSecure(req.isSecure());
        cookie.setPath(cookiePath(req));
        cookie.setMaxAge(0);
        cookie.setAttribute("SameSite", "Lax");
        resp.addCookie(cookie);
    }

    private static String cookiePath(HttpServletRequest req) {
        String ctx = req.getContextPath();
        return (ctx == null || ctx.isEmpty()) ? "/" : ctx;
    }
}
