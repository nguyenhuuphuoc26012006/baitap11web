<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><sitemesh:write property='title'/> - OnlineShop phuoc.com</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_24162100.css?v=2">
    <sitemesh:write property='head'/>
</head>
<body>

<header class="site-header">
    <div class="logo">OnlineShop <span>phuoc.com</span></div>
    <nav>
        <a href="${pageContext.request.contextPath}/home">Trang Chủ</a>
        <a href="${pageContext.request.contextPath}/products">Sản phẩm</a>
        <a href="${pageContext.request.contextPath}/cart">Giỏ hàng</a>
        <c:choose>
            <c:when test="${empty currentUser}">
                <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
            </c:when>
            <c:otherwise>
                <span class="hello">Xin chào, ${currentUser.fullname}</span>
                <c:if test="${role == 'ADMIN'}">
                    <a href="${pageContext.request.contextPath}/admin/users">Trang quản trị</a>
                </c:if>
                <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </c:otherwise>
        </c:choose>
    </nav>
</header>

<main class="site-content">
    <sitemesh:write property='body'/>
</main>

<footer class="site-footer">
    <p>Họ tên: Nguyễn Hữu Phước &nbsp;|&nbsp; MSSV: 24162100 &nbsp;|&nbsp; Mã đề: 06</p>
</footer>

</body>
</html>
