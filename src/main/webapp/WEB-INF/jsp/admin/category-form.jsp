<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<title>${empty category ? 'Thêm Category' : 'Sửa Category'}</title>

<div class="form-box">
    <h2>${empty category ? 'Thêm Category mới' : 'Cập nhật Category'}</h2>

    <form method="post" action="${pageContext.request.contextPath}/admin/categories">
        <input type="hidden" name="action" value="${empty category ? 'create' : 'update'}">
        <c:if test="${not empty category}">
            <input type="hidden" name="categoryId" value="${category.categoryId}">
        </c:if>

        <label>Tên danh mục</label>
        <input type="text" name="categoryName" value="${category.categoryName}" required>

        <label>Ảnh (đường dẫn)</label>
        <input type="text" name="images" value="${category.images}">

        <label>Trạng thái</label>
        <select name="status">
            <option value="1" ${category.status == 1 ? 'selected' : ''}>Hoạt động</option>
            <option value="0" ${category.status == 0 ? 'selected' : ''}>Ẩn</option>
        </select>

        <button type="submit" class="btn">Lưu</button>
        <a class="btn btn-cancel" href="${pageContext.request.contextPath}/admin/categories">Hủy</a>
    </form>
</div>
