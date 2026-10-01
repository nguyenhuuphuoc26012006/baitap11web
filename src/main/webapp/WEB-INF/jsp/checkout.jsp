<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<title>Thanh toán</title>

<h2>Thanh toán đơn hàng</h2>

<c:if test="${not empty err}"><p class="msg-error"><c:out value="${err}"/></p></c:if>

<div class="checkout-grid">
    <!-- BÊN TRÁI: thông tin sản phẩm -->
    <div class="order-box">
        <h3>Đơn hàng của bạn</h3>
        <table class="data-table">
            <thead>
                <tr><th>Sản phẩm</th><th>SL</th><th>Thành tiền</th></tr>
            </thead>
            <tbody>
                <c:forEach var="it" items="${cartItems}">
                    <tr>
                        <td><c:out value="${it.productName}"/></td>
                        <td>${it.quantity}</td>
                        <td><fmt:formatNumber value="${it.lineTotal}" type="number"/> đ</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div class="cart-total">Tổng cộng: <b><fmt:formatNumber value="${cartTotal}" type="number"/> đ</b></div>
    </div>

    <!-- BÊN PHẢI: thông tin khách hàng -->
    <form class="form-box" method="post" action="${pageContext.request.contextPath}/checkout">
        <h2>Thông tin giao hàng</h2>

        <label for="receiverName">Họ tên người nhận</label>
        <input type="text" id="receiverName" name="receiverName" maxlength="100" required
               value="<c:out value='${receiverName}'/>">

        <label for="receiverPhone">Số điện thoại</label>
        <input type="tel" id="receiverPhone" name="receiverPhone" maxlength="15" required
               placeholder="0901234567" value="<c:out value='${receiverPhone}'/>">

        <label for="receiverAddress">Địa chỉ giao hàng</label>
        <textarea id="receiverAddress" name="receiverAddress" rows="3" maxlength="300" required><c:out value="${receiverAddress}"/></textarea>

        <label for="note">Ghi chú (không bắt buộc)</label>
        <textarea id="note" name="note" rows="2" maxlength="300"><c:out value="${note}"/></textarea>

        <label>Phương thức thanh toán</label>
        <p class="cod-box">Thanh toán khi nhận hàng (COD)</p>

        <button type="submit" class="btn">Đặt hàng</button>
        <a class="btn btn-cancel" href="${pageContext.request.contextPath}/cart">Quay lại giỏ hàng</a>
    </form>
</div>