<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<title>Đăng ký</title>

<div class="form-box">
    <h2>Đăng ký tài khoản</h2>

    <c:if test="${not empty error}">
        <p class="msg-error">${error}</p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/register">
        <label>Họ tên</label>
        <input type="text" name="fullname" required>

        <label>Tên đăng nhập</label>
        <input type="text" name="username" required>

        <label>Email (nhận mã OTP)</label>
        <input type="email" name="email" required>

        <label>Số điện thoại</label>
        <input type="text" name="phone">

        <label>Mật khẩu</label>
        <input type="password" name="password" required>

        <button type="submit" class="btn">Đăng ký</button>
    </form>
    <p>Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a></p>
</div>
