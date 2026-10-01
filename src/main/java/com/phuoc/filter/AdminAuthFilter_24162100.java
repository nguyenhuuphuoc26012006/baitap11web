package com.phuoc.filter;

import com.phuoc.model.Users_24162100;
import com.phuoc.util.AuthUtil_24162100;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Filter chan truy cap "/admin/*" neu chua co JWT hop le hoac khong phai ADMIN.
 * Dam bao chi ADMIN moi thay/dung duoc "Trang quan tri" (theo yeu cau Cau 1).
 */
@WebFilter("/admin/*")
public class AdminAuthFilter_24162100 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // JWT hop le (dung chu ky, chua het han) moi cho vao; role lay tu claim trong token
        Users_24162100 user = AuthUtil_24162100.getCurrentUser(req);

        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        chain.doFilter(request, response);
    }
}
