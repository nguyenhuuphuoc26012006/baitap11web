<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<title>Chi tiết sản phẩm</title>

<c:if test="${not empty param.err}"><p class="msg-error"><c:out value="${param.err}"/></p></c:if>

<div class="product-detail">
    <img src="${pageContext.request.contextPath}/images/${product.images}"
         onerror="this.src='${pageContext.request.contextPath}/images/no-image.png'"
         alt="${product.productName}">
    <div class="detail-info">
        <p><b>Tên sản phẩm:</b> ${product.productName}</p>
        <p><b>Mã sản phẩm:</b> ${product.productCode}</p>
        <p><b>Danh mục:</b> ${product.categoryName}</p>
        <p><b>Giá:</b> <fmt:formatNumber value="${product.price}" type="number"/> đ</p>
        <p><b>Amount:</b> ${product.amount}</p>
        <p><b>Description:</b> ${product.description}</p>
        <c:choose>
            <c:when test="${product.status == 1 && product.amount > 0}">
                <c:choose>
                    <c:when test="${empty currentUser}">
                        <p><a href="${pageContext.request.contextPath}/login">Đăng nhập</a> để thêm sản phẩm vào giỏ hàng.</p>
                    </c:when>
                    <c:otherwise>
                        <form class="inline-form add-cart-form" method="post" action="${pageContext.request.contextPath}/cart">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="productId" value="${product.productId}">
                            <input type="hidden" name="back" value="detail">
                            <label for="qty"><b>Số lượng:</b></label>
                            <input class="qty-input" id="qty" type="number" name="quantity" value="1"
                                   min="1" max="${product.amount}" required>
                            <button type="submit" class="btn">Thêm vào giỏ hàng</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <p class="cart-warn">Sản phẩm tạm hết hàng.</p>
            </c:otherwise>
        </c:choose>
        <a class="btn btn-cancel" href="${pageContext.request.contextPath}/products">&laquo; Quay lại danh sách</a>
    </div>
</div>
