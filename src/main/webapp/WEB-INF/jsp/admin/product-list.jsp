<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<title>Quản lý Sản phẩm</title>

<h2>Quản lý Sản phẩm</h2>
<a class="btn" href="${pageContext.request.contextPath}/admin/products?action=new">+ Thêm sản phẩm</a>

<c:if test="${param.error == 'delete'}">
    <p class="msg-error">Không thể xóa sản phẩm này (có thể đang nằm trong giỏ hàng/đơn hàng).</p>
</c:if>

<table class="data-table">
    <tr>
        <th>ID</th><th>Ảnh</th><th>Tên sản phẩm</th><th>Mã SP</th><th>Danh mục</th>
        <th>Cửa hàng</th><th>Giá</th><th>Amount</th><th>Trạng thái</th><th>Hành động</th>
    </tr>
    <c:forEach var="p" items="${products}">
        <tr>
            <td>${p.productId}</td>
            <td>
                <img src="${pageContext.request.contextPath}/images/${p.images}"
                     onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/no-image.png'"
                     alt="${p.productName}" style="width:60px;height:45px;object-fit:cover;border-radius:6px;">
            </td>
            <td>${p.productName}</td>
            <td>${p.productCode}</td>
            <td>${p.categoryName}</td>
            <td>${p.sellerName}</td>
            <td><fmt:formatNumber value="${p.price}" type="number"/> đ</td>
            <td>${p.amount}</td>
            <td>${p.status == 1 ? 'Đang bán' : 'Ẩn'}</td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/products?action=edit&id=${p.productId}">Sửa</a>
                |
                <a href="${pageContext.request.contextPath}/admin/products?action=delete&id=${p.productId}"
                   onclick="return confirm('Xóa sản phẩm này?')">Xóa</a>
            </td>
        </tr>
    </c:forEach>
</table>

<div class="pagination">
    <c:forEach begin="1" end="${totalPages}" var="i">
        <a class="${i == currentPage ? 'active' : ''}"
           href="${pageContext.request.contextPath}/admin/products?page=${i}">${i}</a>
    </c:forEach>
</div>