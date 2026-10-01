package com.phuoc.util;

import com.phuoc.model.Users_24162100;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * Tien ich JWT: tao token (ky HS256 bang secret) va kiem tra/giai ma token.
 * Cau hinh doc tu config_24162100.properties (jwt.secret, jwt.expiration-ms, jwt.issuer).
 *
 * Cau truc token: header.payload.signature
 *  - sub  : username
 *  - uid  : userId, rid : roleId, role : ADMIN/SELLER/USER, name : fullname
 *  - iss, iat, exp : registered claims
 * KHONG dua mat khau vao payload (payload chi la Base64, ai cung doc duoc).
 */
public final class JwtUtil_24162100 {

    private static final SecretKey KEY;
    private static final long EXPIRATION_MS;
    private static final String ISSUER;

    static {
        Properties props = new Properties();
        try (InputStream is = JwtUtil_24162100.class.getClassLoader()
                .getResourceAsStream("config_24162100.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (Exception e) {
            throw new RuntimeException("Khong the nap cau hinh JWT: " + e.getMessage(), e);
        }
        String secret = props.getProperty("jwt.secret");
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("Thieu jwt.secret trong config_24162100.properties");
        }
        KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret.trim()));
        EXPIRATION_MS = Long.parseLong(props.getProperty("jwt.expiration-ms", "3600000").trim());
        ISSUER = props.getProperty("jwt.issuer", "phuoc.com").trim();
    }

    private JwtUtil_24162100() {
    }

    /** Ten vai tro dung chung cho JSP (sessionScope.role cu). */
    public static String roleName(Users_24162100 user) {
        return user.isAdmin() ? "ADMIN" : (user.isSeller() ? "SELLER" : "USER");
    }

    public static long getExpirationMs() {
        return EXPIRATION_MS;
    }

    /** Tao JWT cho user vua dang nhap thanh cong. */
    public static String generateToken(Users_24162100 user) {
        Date now = new Date();
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("uid", user.getUserId())
                .claim("rid", user.getRoleId())
                .claim("role", roleName(user))
                .claim("name", user.getFullname())
                .issuer(ISSUER)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + EXPIRATION_MS))
                .signWith(KEY, Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Kiem tra chu ky + han su dung + issuer, tra ve claims.
     * Nem JwtException (SignatureException, ExpiredJwtException, MalformedJwtException...)
     * hoac IllegalArgumentException neu token khong hop le.
     */
    public static Claims parseToken(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(KEY)
                .requireIssuer(ISSUER)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
