<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<title>Quản lý Users</title>

<h2>Quản lý User</h2>
<a class="btn" href="${pageContext.request.contextPath}/admin/users?action=new">+ Thêm User</a>

<table class="data-table">
    <tr>
        <th>ID</th><th>Username</th><th>Email</th><th>Họ tên</th><th>SĐT</th>
        <th>Trạng thái</th><th>Role</th><th>Hành động</th>
    </tr>
    <c:forEach var="u" items="${users}">
        <tr>
            <td>${u.userId}</td>
            <td>${u.username}</td>
            <td>${u.email}</td>
            <td>${u.fullname}</td>
            <td>${u.phone}</td>
            <td>${u.status == 1 ? 'Đã kích hoạt' : 'Chưa kích hoạt'}</td>
            <td>${u.roleId}</td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/users?action=edit&id=${u.userId}">Sửa</a>
                |
                <a href="${pageContext.request.contextPath}/admin/users?action=delete&id=${u.userId}"
                   onclick="return confirm('Xóa user này?')">Xóa</a>
            </td>
        </tr>
    </c:forEach>
</table>

<div class="pagination">
    <c:forEach begin="1" end="${totalPages}" var="i">
        <a class="${i == currentPage ? 'active' : ''}"
           href="${pageContext.request.contextPath}/admin/users?page=${i}">${i}</a>
    </c:forEach>
</div>
