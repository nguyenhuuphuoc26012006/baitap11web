<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<title>Quản lý Category</title>

<h2>Quản lý Category</h2>
<a class="btn" href="${pageContext.request.contextPath}/admin/categories?action=new">+ Thêm Category</a>

<table class="data-table">
    <tr>
        <th>ID</th><th>Tên danh mục</th><th>Ảnh</th><th>Trạng thái</th><th>Hành động</th>
    </tr>
    <c:forEach var="c" items="${categories}">
        <tr>
            <td>${c.categoryId}</td>
            <td>${c.categoryName}</td>
            <td>${c.images}</td>
            <td>${c.status == 1 ? 'Hoạt động' : 'Ẩn'}</td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/categories?action=edit&id=${c.categoryId}">Sửa</a>
                |
                <a href="${pageContext.request.contextPath}/admin/categories?action=delete&id=${c.categoryId}"
                   onclick="return confirm('Xóa danh mục này?')">Xóa</a>
            </td>
        </tr>
    </c:forEach>
</table>

<div class="pagination">
    <c:forEach begin="1" end="${totalPages}" var="i">
        <a class="${i == currentPage ? 'active' : ''}"
           href="${pageContext.request.contextPath}/admin/categories?page=${i}">${i}</a>
    </c:forEach>
</div>
