<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<title>${empty user ? 'Thêm User' : 'Sửa User'}</title>

<div class="form-box">
    <h2>${empty user ? 'Thêm User mới' : 'Cập nhật User'}</h2>

    <form method="post" action="${pageContext.request.contextPath}/admin/users">
        <input type="hidden" name="action" value="${empty user ? 'create' : 'update'}">
        <c:if test="${not empty user}">
            <input type="hidden" name="userId" value="${user.userId}">
        </c:if>

        <label>Tên đăng nhập</label>
        <input type="text" name="username" value="${user.username}" required>

        <label>Email</label>
        <input type="email" name="email" value="${user.email}" required>

        <label>Họ tên</label>
        <input type="text" name="fullname" value="${user.fullname}">

        <label>Số điện thoại</label>
        <input type="text" name="phone" value="${user.phone}">

        <label>Ảnh (đường dẫn)</label>
        <input type="text" name="images" value="${user.images}">

        <label>Mật khẩu ${not empty user ? '(để trống nếu không đổi)' : ''}</label>
        <input type="password" name="password" ${empty user ? 'required' : ''}>

        <label>Trạng thái</label>
        <select name="status">
            <option value="1" ${user.status == 1 ? 'selected' : ''}>Đã kích hoạt</option>
            <option value="0" ${user.status == 0 ? 'selected' : ''}>Chưa kích hoạt</option>
        </select>

        <label>Vai trò</label>
        <select name="roleId">
            <option value="1" ${user.roleId == 1 ? 'selected' : ''}>ADMIN</option>
            <option value="2" ${user.roleId == 2 ? 'selected' : ''}>USER</option>
            <option value="3" ${user.roleId == 3 ? 'selected' : ''}>SELLER</option>
        </select>

        <button type="submit" class="btn">Lưu</button>
        <a class="btn btn-cancel" href="${pageContext.request.contextPath}/admin/users">Hủy</a>
    </form>
</div>
