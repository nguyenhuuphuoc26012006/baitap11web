<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<title>Lịch sử đặt hàng</title>

<h2>Lịch sử đặt hàng</h2>

<c:if test="${not empty param.msg}">
    <p class="msg-success"><c:out value="${param.msg}"/></p>
</c:if>

<div class="order-filter">
    <a class="${selectedStatus == 0 ? 'active' : ''}"
       href="${pageContext.request.contextPath}/order-history">Tất cả</a>
    <a class="${selectedStatus == 1 ? 'active' : ''}"
       href="${pageContext.request.contextPath}/order-history?status=1">Đơn hàng mới</a>
    <a class="${selectedStatus == 2 ? 'active' : ''}"
       href="${pageContext.request.contextPath}/order-history?status=2">Đã xác nhận</a>
    <a class="${selectedStatus == 3 ? 'active' : ''}"
       href="${pageContext.request.contextPath}/order-history?status=3">Chuẩn bị hàng</a>
    <a class="${selectedStatus == 4 ? 'active' : ''}"
       href="${pageContext.request.contextPath}/order-history?status=4">Vận chuyển</a>
    <a class="${selectedStatus == 5 ? 'active' : ''}"
       href="${pageContext.request.contextPath}/order-history?status=5">Giao hàng</a>
    <a class="${selectedStatus == 6 ? 'active' : ''}"
       href="${pageContext.request.contextPath}/order-history?status=6">Đã giao</a>
    <a class="${selectedStatus == 7 ? 'active' : ''}"
       href="${pageContext.request.contextPath}/order-history?status=7">Đơn hàng hủy</a>
    <a class="${selectedStatus == 8 ? 'active' : ''}"
       href="${pageContext.request.contextPath}/order-history?status=8">Đơn hàng hoàn</a>
</div>

<c:choose>
    <c:when test="${empty orders}">
        <div class="order-empty">Không có đơn hàng ở trạng thái này.</div>
    </c:when>
    <c:otherwise>
        <c:forEach var="order" items="${orders}">
            <div class="order-card">
                <div class="order-card-head">
                    <div>
                        <b>Mã đơn: <c:out value="${order.cartId}"/></b>
                        <span class="order-date">
                            <fmt:formatDate value="${order.buyDate}" pattern="dd/MM/yyyy HH:mm"/>
                        </span>
                    </div>
                    <span class="order-status ${order.orderStatusClass}">
                        <c:out value="${order.orderStatusName}"/>
                    </span>
                </div>

                <div class="order-card-body">
                    <c:forEach var="it" items="${order.items}">
                        <div class="order-item">
                            <img
                                src="${pageContext.request.contextPath}/images/<c:out value='${it.images}'/>"
                                onerror="this.src='${pageContext.request.contextPath}/images/no-image.png'"
                                alt="<c:out value='${it.productName}'/>">
                            <div class="order-item-info">
                                <a href="${pageContext.request.contextPath}/product-detail?id=${it.productId}">
                                    <c:out value="${it.productName}"/>
                                </a>
                                <div>Số lượng: ${it.quantity}</div>
                            </div>
                            <div>
                                <fmt:formatNumber value="${it.lineTotal}" type="number"/> đ
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="order-card-foot">
                    <div>
                        Người nhận: <b><c:out value="${order.receiverName}"/></b>
                        - <c:out value="${order.receiverPhone}"/>
                    </div>
                    <div class="order-total">
                        Tổng tiền:
                        <b><fmt:formatNumber value="${order.totalAmount}" type="number"/> đ</b>
                    </div>
                </div>

                <div class="order-card-actions">
                    <a class="btn btn-sm"
                       href="${pageContext.request.contextPath}/order-success?id=${order.cartId}">
                        Xem chi tiết
                    </a>

                    <c:if test="${order.orderStatus == 1}">
                        <form class="inline-form" method="post"
                              action="${pageContext.request.contextPath}/order-cancel"
                              onsubmit="return confirm('Bạn có chắc muốn hủy đơn hàng này không?');">
                            <input type="hidden" name="id" value="${order.cartId}">
                            <button type="submit" class="btn btn-sm btn-danger">Hủy đơn hàng</button>
                        </form>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </c:otherwise>
</c:choose>
