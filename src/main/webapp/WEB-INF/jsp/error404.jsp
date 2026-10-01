<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Không tìm thấy trang</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_24162100.css">
</head>
<body>
<main class="site-content">
    <div class="form-box">
        <h2>404 - Không tìm thấy trang</h2>
        <p><b>URL yêu cầu:</b> <c:out value="${requestScope['jakarta.servlet.error.request_uri']}"/></p>
        <p><b>Tài nguyên không tồn tại:</b> <c:out value="${requestScope['jakarta.servlet.error.message']}"/></p>
        <a class="btn" href="${pageContext.request.contextPath}/home">Về trang chủ</a>
    </div>
</main>
</body>
</html>
