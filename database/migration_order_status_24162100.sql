USE OnlineShop_24162100;
GO

/* Chay file nay neu database da ton tai va khong muon chay lai schema. */
IF COL_LENGTH('dbo.Cart', 'orderStatus') IS NULL
BEGIN
    ALTER TABLE Cart ADD orderStatus INT NOT NULL
        CONSTRAINT DF_Cart_OrderStatus DEFAULT 1;
END
GO

UPDATE Cart
SET orderStatus = 1
WHERE status = 1 AND (orderStatus IS NULL OR orderStatus NOT BETWEEN 1 AND 8);
GO

IF NOT EXISTS (
    SELECT 1 FROM sys.indexes
    WHERE name = 'IX_Cart_User_OrderStatus'
      AND object_id = OBJECT_ID('dbo.Cart')
)
BEGIN
    CREATE INDEX IX_Cart_User_OrderStatus
        ON Cart(userId, orderStatus, buyDate DESC);
END
GO
