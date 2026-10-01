package com.phuoc.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.phuoc.model.Users_24162100;
import com.phuoc.service.IUserService_24162100;
import com.phuoc.service.UserService_24162100;
import com.phuoc.util.AuthUtil_24162100;
import com.phuoc.util.JwtUtil_24162100;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * API JSON de test JWT bang Postman:
 *  POST /api/login  body {"username":"...","password":"..."} -> {"token":"...","expiresIn":3600000}
 *  GET  /api/me     header Authorization: Bearer <token>       -> thong tin user hien tai (khong co password)
 */
@WebServlet({"/api/login", "/api/me"})
public class ApiAuthController_24162100 extends HttpServlet {

    private final IUserService_24162100 userService = new UserService_24162100();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getServletPath().equals("/api/login")) {
            writeJson(resp, 405, Map.of("error", "Method Not Allowed"));
            return;
        }
        req.setCharacterEncoding("UTF-8");
        String username = null;
        String password = null;
        try {
            JsonObject body = gson.fromJson(req.getReader(), JsonObject.class);
            if (body != null) {
                username = body.has("username") && !body.get("username").isJsonNull() ? body.get("username").getAsString() : null;
                password = body.has("password") && !body.get("password").isJsonNull() ? body.get("password").getAsString() : null;
            }
        } catch (JsonParseException | IllegalStateException | UnsupportedOperationException e) {
            writeJson(resp, 400, Map.of("error", "Body phai la JSON hop le"));
            return;
        }
        if (username == null || password == null) {
            writeJson(resp, 400, Map.of("error", "Thieu username hoac password"));
            return;
        }

        Users_24162100 user = userService.login(username, password);
        if (user == null) {
            writeJson(resp, 401, Map.of("error", "Sai tai khoan/mat khau hoac tai khoan chua kich hoat OTP"));
            return;
        }

        Map<String, Object> out = new LinkedHashMap<>();
        out.put("token", JwtUtil_24162100.generateToken(user));
        out.put("expiresIn", JwtUtil_24162100.getExpirationMs());
        writeJson(resp, 200, out);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getServletPath().equals("/api/me")) {
            writeJson(resp, 405, Map.of("error", "Method Not Allowed"));
            return;
        }
        Users_24162100 user = AuthUtil_24162100.getCurrentUser(req);
        if (user == null) {
            writeJson(resp, 401, Map.of("error", "Token thieu, sai chu ky hoac da het han"));
            return;
        }
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("userId", user.getUserId());
        out.put("username", user.getUsername());
        out.put("fullname", user.getFullname());
        out.put("roleId", user.getRoleId());
        out.put("role", JwtUtil_24162100.roleName(user));
        writeJson(resp, 200, out);
    }

    private void writeJson(HttpServletResponse resp, int status, Object body) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(body));
    }
}
