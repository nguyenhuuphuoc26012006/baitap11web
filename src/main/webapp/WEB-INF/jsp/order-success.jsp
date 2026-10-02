<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<title>Chi tiết đơn hàng</title>

<c:choose>
    <c:when test="${order.orderStatus == 1}">
        <h2>Đặt hàng thành công</h2>
        <p class="msg-success">Cảm ơn bạn đã mua hàng! Đơn hàng sẽ được giao và bạn thanh toán tiền mặt khi nhận hàng (COD).</p>
    </c:when>
    <c:otherwise>
        <h2>Chi tiết đơn hàng</h2>
    </c:otherwise>
</c:choose>
<div class="order-box">
    <p><b>Mã đơn hàng:</b> <c:out value="${order.cartId}"/></p>
    <p><b>Ngày đặt:</b> <fmt:formatDate value="${order.buyDate}" pattern="dd/MM/yyyy HH:mm"/></p>
    <p><b>Trạng thái:</b>
        <span class="order-status ${order.orderStatusClass}">
            <c:out value="${order.orderStatusName}"/>
        </span>
    </p>
    <p><b>Người nhận:</b> <c:out value="${order.receiverName}"/> - <c:out value="${order.receiverPhone}"/></p>
    <p><b>Địa chỉ:</b> <c:out value="${order.receiverAddress}"/></p>
    <c:if test="${not empty order.note}">
        <p><b>Ghi chú:</b> <c:out value="${order.note}"/></p>
    </c:if>
    <p><b>Thanh toán:</b> Thanh toán khi nhận hàng (COD)</p>

    <table class="data-table">
        <thead>
            <tr><th>Sản phẩm</th><th>Đơn giá</th><th>SL</th><th>Thành tiền</th></tr>
        </thead>
        <tbody>
            <c:forEach var="it" items="${order.items}">
                <tr>
                    <td><c:out value="${it.productName}"/></td>
                    <td><fmt:formatNumber value="${it.unitPrice}" type="number"/> đ</td>
                    <td>${it.quantity}</td>
                    <td><fmt:formatNumber value="${it.lineTotal}" type="number"/> đ</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div class="cart-total">Tổng thanh toán: <b><fmt:formatNumber value="${order.totalAmount}" type="number"/> đ</b></div>
</div>

<p>
    <a class="btn" href="${pageContext.request.contextPath}/order-history">Xem lịch sử đặt hàng</a>
    <c:if test="${order.orderStatus == 1}">
        <form class="inline-form" method="post"
              action="${pageContext.request.contextPath}/order-cancel"
              onsubmit="return confirm('Bạn có chắc muốn hủy đơn hàng này không?');">
            <input type="hidden" name="id" value="${order.cartId}">
            <button type="submit" class="btn btn-danger">Hủy đơn hàng</button>
        </form>
    </c:if>
    <a class="btn btn-cancel" href="${pageContext.request.contextPath}/products">Tiếp tục mua sắm</a>
</p>
