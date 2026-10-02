USE OnlineShop_24162100;
GO

/* Xem cac don da dat */
SELECT cartId, userId, buyDate, orderStatus,
       CASE orderStatus
           WHEN 1 THEN N'Đơn hàng mới'
           WHEN 2 THEN N'Đã xác nhận'
           WHEN 3 THEN N'Chuẩn bị hàng'
           WHEN 4 THEN N'Vận chuyển'
           WHEN 5 THEN N'Giao hàng'
           WHEN 6 THEN N'Đã giao'
           WHEN 7 THEN N'Đơn hàng hủy'
           WHEN 8 THEN N'Đơn hàng hoàn'
       END AS TrangThai
FROM Cart
WHERE status = 1
ORDER BY buyDate DESC;
GO

/* ==========================================================
   ĐỔI TRẠNG THÁI ĐỂ TEST
   Thay N'MA-DON-HANG' bằng cartId thực tế.
   Chỉ thay orderStatus, không thay Cart.status.
   ========================================================== */

/* 1 - Đơn hàng mới */
-- UPDATE Cart SET orderStatus = 1 WHERE cartId = N'MA-DON-HANG' AND status = 1;

/* 2 - Đã xác nhận */
-- UPDATE Cart SET orderStatus = 2 WHERE cartId = N'MA-DON-HANG' AND status = 1;

/* 3 - Chuẩn bị hàng */
-- UPDATE Cart SET orderStatus = 3 WHERE cartId = N'MA-DON-HANG' AND status = 1;

/* 4 - Vận chuyển */
-- UPDATE Cart SET orderStatus = 4 WHERE cartId = N'MA-DON-HANG' AND status = 1;

/* 5 - Giao hàng */
-- UPDATE Cart SET orderStatus = 5 WHERE cartId = N'MA-DON-HANG' AND status = 1;

/* 6 - Đã giao */
-- UPDATE Cart SET orderStatus = 6 WHERE cartId = N'MA-DON-HANG' AND status = 1;

/* 7 - Đơn hàng hủy */
-- UPDATE Cart SET orderStatus = 7 WHERE cartId = N'MA-DON-HANG' AND status = 1;

/* 8 - Đơn hàng hoàn */
-- UPDATE Cart SET orderStatus = 8 WHERE cartId = N'MA-DON-HANG' AND status = 1;

/* Kiểm tra lại */
-- SELECT cartId, orderStatus FROM Cart WHERE cartId = N'MA-DON-HANG';
