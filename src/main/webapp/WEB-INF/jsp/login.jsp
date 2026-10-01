<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<title>Đăng nhập</title>

<div class="form-box">
    <h2>Đăng nhập</h2>

    <c:if test="${not empty error}">
        <p class="msg-error">${error}</p>
    </c:if>
    <c:if test="${not empty message}">
        <p class="msg-success">${message}</p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/login">
        <label>Tên đăng nhập hoặc Email</label>
        <input type="text" name="username" required>

        <label>Mật khẩu</label>
        <input type="password" name="password" required>

        <button type="submit" class="btn">Đăng nhập</button>
    </form>
    <p>Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký ngay</a></p>
</div>
