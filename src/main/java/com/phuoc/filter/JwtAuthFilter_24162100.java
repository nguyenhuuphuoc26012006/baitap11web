package com.phuoc.filter;

import com.phuoc.model.Users_24162100;
import com.phuoc.util.AuthUtil_24162100;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Filter JWT: voi moi request, doc token (cookie/Bearer), xac thuc chu ky + han su dung,
 * roi gan "currentUser" va "role" vao request de JSP hien thi "Xin chao, ...".
 * Filter KHONG chan request - viec chan quyen nam o AdminAuthFilter va cac servlet /api.
 * Neu cookie chua token khong con hop le (het han/bi sua) thi xoa cookie.
 */
@WebFilter("/*")
public class JwtAuthFilter_24162100 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (!isStaticResource(req)) {
            Users_24162100 user = AuthUtil_24162100.getCurrentUser(req);
            if (user == null && AuthUtil_24162100.extractToken(req) != null
                    && req.getHeader("Authorization") == null) {
                AuthUtil_24162100.clearTokenCookie(req, resp);
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isStaticResource(HttpServletRequest req) {
        String path = req.getRequestURI().substring(req.getContextPath().length());
        return path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/");
    }
}
