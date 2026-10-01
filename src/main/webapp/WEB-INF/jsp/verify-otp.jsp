<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<title>Xác thực OTP</title>

<div class="form-box">
    <h2>Xác thực OTP</h2>

    <c:if test="${not empty message}">
        <p class="msg-success">${message}</p>
    </c:if>
    <c:if test="${not empty error}">
        <p class="msg-error">${error}</p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/verify-otp">
        <label>Email</label>
        <input type="email" name="email" value="${email}" required>

        <label>Mã OTP (6 số, gửi qua email)</label>
        <input type="text" name="otp" required>

        <button type="submit" class="btn">Xác thực</button>
    </form>
</div>
