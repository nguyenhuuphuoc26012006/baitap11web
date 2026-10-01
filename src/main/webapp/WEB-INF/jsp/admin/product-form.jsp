<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<title>${isEdit ? 'Sửa sản phẩm' : 'Thêm sản phẩm'}</title>

<div class="form-box">
    <h2>${isEdit ? 'Cập nhật sản phẩm' : 'Thêm sản phẩm mới'}</h2>

    <c:if test="${not empty error}">
        <p class="msg-error">${error}</p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/admin/products">
        <input type="hidden" name="action" value="${isEdit ? 'update' : 'create'}">
        <c:if test="${isEdit}">
            <input type="hidden" name="productId" value="${product.productId}">
        </c:if>

        <label>Tên sản phẩm</label>
        <input type="text" name="productName" value="${product.productName}" required>

        <label>Mã sản phẩm</label>
        <input type="number" name="productCode" value="${product.productCode}" required>

        <label>Danh mục</label>
        <select name="categoryId" required>
            <c:forEach var="c" items="${categories}">
                <option value="${c.categoryId}" ${product.categoryId == c.categoryId ? 'selected' : ''}>${c.categoryName}</option>
            </c:forEach>
        </select>

        <label>Cửa hàng (Seller)</label>
        <select name="sellerId" required>
            <c:forEach var="s" items="${sellers}">
                <option value="${s.sellerId}" ${product.sellerId == s.sellerId ? 'selected' : ''}>${s.sellername}</option>
            </c:forEach>
        </select>

        <label>Giá (đ)</label>
        <input type="number" name="price" min="0" step="1" required
               value="<c:if test='${not empty product}'><fmt:formatNumber value='${product.price}' pattern='0' groupingUsed='false'/></c:if>">

        <label>Amount</label>
        <input type="number" name="amount" min="0" value="${empty product ? 0 : product.amount}" required>

        <label>Stock</label>
        <input type="number" name="stock" min="0" value="${empty product ? 0 : product.stock}" required>

        <label>Tên file ảnh (nằm trong thư mục images/)</label>
        <input type="text" name="images" value="${product.images}" placeholder="vi-du: iphone17.png">

        <label>Mô tả</label>
        <textarea name="description" rows="4" maxlength="500">${product.description}</textarea>

        <label>Trạng thái</label>
        <select name="status">
            <option value="1" ${empty product or product.status == 1 ? 'selected' : ''}>Đang bán</option>
            <option value="0" ${not empty product and product.status == 0 ? 'selected' : ''}>Ẩn</option>
        </select>

        <button type="submit" class="btn">Lưu</button>
        <a class="btn btn-cancel" href="${pageContext.request.contextPath}/admin/products">Hủy</a>
    </form>
</div>