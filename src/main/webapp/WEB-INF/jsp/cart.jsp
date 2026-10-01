<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<title>Giỏ hàng</title>

<h2>Giỏ hàng của bạn</h2>

<c:if test="${not empty msg}">
	<p class="msg-success">
		<c:out value="${msg}" />
	</p>
</c:if>
<c:if test="${not empty err}">
	<p class="msg-error">
		<c:out value="${err}" />
	</p>
</c:if>

<c:choose>
	<c:when test="${empty cartItems}">
		<p>Giỏ hàng đang trống.</p>
		<a class="btn" href="${pageContext.request.contextPath}/products">Tiếp
			tục mua sắm</a>
	</c:when>
	<c:otherwise>
		<table class="data-table cart-table">
			<thead>
				<tr>
					<th>Sản phẩm</th>
					<th>Đơn giá</th>
					<th>Số lượng</th>
					<th>Thành tiền</th>
					<th>Thao tác</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="it" items="${cartItems}">
					<tr>
						<td>
							<div class="cart-product">
								<img width="64" height="64"
									style="width: 64px; height: 64px; object-fit: cover"
									src="${pageContext.request.contextPath}/images/<c:out value='${it.images}'/>"
									onerror="this.src='${pageContext.request.contextPath}/images/no-image.png'"
									alt="<c:out value='${it.productName}'/>">
								<div>
									<a
										href="${pageContext.request.contextPath}/product-detail?id=${it.productId}">
										<c:out value="${it.productName}" />
									</a>
									<c:choose>
										<c:when test="${not it.available}">
											<div class="cart-warn">Hết hàng hoặc ngừng bán</div>
										</c:when>
										<c:when test="${it.quantity > it.availableAmount}">
											<div class="cart-warn">Chỉ còn ${it.availableAmount}
												sản phẩm</div>
										</c:when>
										<c:otherwise>
											<div class="cart-stock">Còn ${it.availableAmount} sản
												phẩm</div>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</td>
						<td><fmt:formatNumber value="${it.unitPrice}" type="number" />
							đ</td>
						<td>
							<form class="inline-form" method="post"
								action="${pageContext.request.contextPath}/cart">
								<input type="hidden" name="action" value="update"> <input
									type="hidden" name="productId" value="${it.productId}">
								<input class="qty-input" type="number" name="quantity"
									value="${it.quantity}" min="1"
									max="${it.availableAmount > 0 ? it.availableAmount : 1}"
									required>
								<button type="submit" class="btn btn-sm">Cập nhật</button>
							</form>
						</td>
						<td><b><fmt:formatNumber value="${it.lineTotal}"
									type="number" /> đ</b></td>
						<td>
							<form class="inline-form" method="post"
								action="${pageContext.request.contextPath}/cart"
								onsubmit="return confirm('Xóa sản phẩm này khỏi giỏ hàng?');">
								<input type="hidden" name="action" value="remove"> <input
									type="hidden" name="productId" value="${it.productId}">
								<button type="submit" class="btn btn-sm btn-danger">Xóa</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div class="cart-summary">
			<div class="cart-total">
				Tổng cộng: <b><fmt:formatNumber value="${cartTotal}"
						type="number" /> đ</b>
			</div>
			<div class="cart-actions">
				<a class="btn btn-cancel"
					href="${pageContext.request.contextPath}/products">Tiếp tục mua
					sắm</a>
				<form class="inline-form" method="post"
					action="${pageContext.request.contextPath}/cart"
					onsubmit="return confirm('Xóa toàn bộ giỏ hàng?');">
					<input type="hidden" name="action" value="clear">
					<button type="submit" class="btn btn-danger">Xóa toàn bộ</button>
				</form>
				<c:choose>
					<c:when test="${cartHasProblem}">
						<span class="btn btn-disabled"
							title="Vui lòng chỉnh sửa các sản phẩm bị cảnh báo">Thanh
							toán COD</span>
					</c:when>
					<c:otherwise>
						<a class="btn" href="${pageContext.request.contextPath}/checkout">Thanh
							toán COD</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</c:otherwise>
</c:choose>
