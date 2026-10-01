<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><sitemesh:write property='title'/> - Trang quản trị - OnlineShop phuoc.com</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_24162100.css">
    <sitemesh:write property='head'/>
</head>
<body class="admin-body">

<header class="site-header admin-header">
    <div class="logo">OnlineShop <span>phuoc.com</span> - Quản trị</div>
    <nav>
        <a href="${pageContext.request.contextPath}/home">Trang Chủ</a>
        <a href="${pageContext.request.contextPath}/products">Sản phẩm</a>
        <a href="${pageContext.request.contextPath}/admin/users">Quản lý Users</a>
        <a href="${pageContext.request.contextPath}/admin/categories">Quản lý Category</a>
        <a href="${pageContext.request.contextPath}/admin/products">Quản lý Sản phẩm</a>
        <span class="hello">Xin chào, ${currentUser.fullname}</span>
        <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
    </nav>
</header>

<main class="site-content">
    <sitemesh:write property='body'/>
</main>

<footer class="site-footer">
    <p>Họ tên: [Họ tên sinh viên] &nbsp;|&nbsp; MSSV: 24162100 &nbsp;|&nbsp; Mã đề: 06</p>
</footer>

</body>
</html>