<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<title>Sản phẩm</title>

<h2>Danh sách sản phẩm theo cửa hàng</h2>

<c:if test="${not empty param.err}"><p class="msg-error"><c:out value="${param.err}"/></p></c:if>

<c:forEach var="entry" items="${groupedProducts}">
    <div class="seller-group">
        <h3>Mã cửa hàng: ${entry.key}</h3>
        <div class="product-grid">
            <c:forEach var="p" items="${entry.value}">
                <div class="product-card">
                    <a href="${pageContext.request.contextPath}/product-detail?id=${p.productId}">
                        <img src="${pageContext.request.contextPath}/images/${p.images}"
                             onerror="this.src='${pageContext.request.contextPath}/images/no-image.png'"
                             alt="${p.productName}">
                        <p class="p-name">Tên sản phẩm: ${p.productName}</p>
                    </a>
                    <p>Mã sản phẩm: ${p.productCode}</p>
                    <p>Danh mục: ${p.categoryName}</p>
                    <p>Giá: <fmt:formatNumber value="${p.price}" type="number"/> đ</p>
                    <p>Amount: ${p.amount}</p>
                    <c:if test="${p.status == 1 && p.amount > 0 && not empty currentUser}">
                        <form class="inline-form" method="post" action="${pageContext.request.contextPath}/cart">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="productId" value="${p.productId}">
                            <input type="hidden" name="quantity" value="1">
                            <input type="hidden" name="back" value="list">
                            <button type="submit" class="btn btn-sm">Thêm vào giỏ</button>
                        </form>
                    </c:if>
                </div>
            </c:forEach>
        </div>
    </div>
</c:forEach>

<c:if test="${empty groupedProducts}">
    <p>Chưa có sản phẩm nào.</p>
</c:if>
