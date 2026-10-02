IF DB_ID('OnlineShop_24162100') IS NULL
BEGIN
    CREATE DATABASE OnlineShop_24162100;
END
GO
USE OnlineShop_24162100;
GO

IF OBJECT_ID('dbo.UserRoles', 'U') IS NULL
BEGIN
    CREATE TABLE UserRoles (
        roleId   INT IDENTITY(1,1) PRIMARY KEY,
        roleName NVARCHAR(50)
    );
END
GO

IF OBJECT_ID('dbo.Seller', 'U') IS NULL
BEGIN
    CREATE TABLE Seller (
        sellerId   INT IDENTITY(1,1) PRIMARY KEY,
        sellername NVARCHAR(50),
        images     NVARCHAR(500),
        status     INT
    );
END
GO

IF OBJECT_ID('dbo.Users', 'U') IS NULL
BEGIN
    CREATE TABLE Users (
        userId   INT IDENTITY(1,1) PRIMARY KEY,
        username NVARCHAR(50),
        email    NVARCHAR(100),
        fullname NVARCHAR(50),
        password NVARCHAR(50),      -- luu MD5 hash (32 ky tu)
        images   NVARCHAR(500),
        phone    NVARCHAR(20),
        status   INT,               -- 0 = chua kich hoat, 1 = da kich hoat
        code     NVARCHAR(50),      -- ma OTP
        roleId   INT,
        sellerid INT,
        CONSTRAINT FK_Users_Role FOREIGN KEY (roleId) REFERENCES UserRoles(roleId),
        CONSTRAINT FK_Users_Seller FOREIGN KEY (sellerid) REFERENCES Seller(sellerId)
    );
END
GO

IF OBJECT_ID('dbo.Category', 'U') IS NULL
BEGIN
    CREATE TABLE Category (
        categoryId   INT IDENTITY(1,1) PRIMARY KEY,
        categoryName NVARCHAR(200),
        images       NVARCHAR(500),
        status       INT
    );
END
GO

IF OBJECT_ID('dbo.Product', 'U') IS NULL
BEGIN
    CREATE TABLE Product (
        productId   INT IDENTITY(1,1) PRIMARY KEY,
        productName NVARCHAR(200),
        productCode BIGINT,
        categoryId  INT,
        description NVARCHAR(500),
        price       FLOAT,
        amount      INT,
        stock       INT,
        images      NVARCHAR(500),
        wishlist    INT,
        status      INT,
        createDate  DATE,
        sellerId    INT,
        CONSTRAINT FK_Product_Category FOREIGN KEY (categoryId) REFERENCES Category(categoryId),
        CONSTRAINT FK_Product_Seller FOREIGN KEY (sellerId) REFERENCES Seller(sellerId)
    );
END
GO

IF OBJECT_ID('dbo.Cart', 'U') IS NULL
BEGIN
    CREATE TABLE Cart (
        cartId   NVARCHAR(50) PRIMARY KEY,
        userId   INT,
        buyDate  DATETIME,
        status   INT,
        CONSTRAINT FK_Cart_User FOREIGN KEY (userId) REFERENCES Users(userId)
    );
END
GO

IF OBJECT_ID('dbo.CartItem', 'U') IS NULL
BEGIN
    CREATE TABLE CartItem (
        cartItemId NVARCHAR(50) PRIMARY KEY,
        quantity   INT,
        unitPrice  FLOAT,
        productId  INT,
        cartId     NVARCHAR(50),
        CONSTRAINT FK_CartItem_Product FOREIGN KEY (productId) REFERENCES Product(productId),
        CONSTRAINT FK_CartItem_Cart FOREIGN KEY (cartId) REFERENCES Cart(cartId)
    );
END
GO

/* ================= Trang thai lich su don hang ================= */
/* Cart.status van giu 0 = gio hang dang dung, 1 = da dat hang.
   Cart.orderStatus:
   1 = Don hang moi
   2 = Da xac nhan
   3 = Chuan bi hang
   4 = Van chuyen
   5 = Giao hang
   6 = Da giao
   7 = Don hang huy
   8 = Don hang hoan
*/
IF COL_LENGTH('dbo.Cart', 'orderStatus') IS NULL
BEGIN
    ALTER TABLE Cart ADD orderStatus INT NOT NULL
        CONSTRAINT DF_Cart_OrderStatus DEFAULT 1;
END
GO

/* Don hang da ton tai truoc khi them cot se duoc xem la "Don hang moi". */
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

/* ================= Du lieu mau (chi insert neu bang con trong) ================= */
IF NOT EXISTS (SELECT 1 FROM UserRoles)
BEGIN
    INSERT INTO UserRoles (roleName) VALUES (N'ADMIN'), (N'USER'), (N'SELLER');
END
GO

IF NOT EXISTS (SELECT 1 FROM Seller)
BEGIN
    INSERT INTO Seller (sellername, images, status) VALUES
    (N'Shop Phuoc Store', N'seller1.png', 1),
    (N'Shop Tech World', N'seller2.png', 1);
END
GO

-- password mau da hash MD5 cua '123456' = e10adc3949ba59abbe56e057f20f883e
IF NOT EXISTS (SELECT 1 FROM Users)
BEGIN
    INSERT INTO Users (username, email, fullname, password, images, phone, status, code, roleId, sellerid) VALUES
    (N'admin', N'admin@phuoc.com', N'Quan Tri Vien', N'e10adc3949ba59abbe56e057f20f883e', N'', N'0900000000', 1, NULL, 1, NULL),
    (N'user01', N'user01@phuoc.com', N'Nguyen Van A', N'e10adc3949ba59abbe56e057f20f883e', N'', N'0900000001', 1, NULL, 2, NULL),
    (N'seller01', N'seller01@phuoc.com', N'Tran Thi B', N'e10adc3949ba59abbe56e057f20f883e', N'', N'0900000002', 1, NULL, 3, 1);
END
GO

IF NOT EXISTS (SELECT 1 FROM Category)
BEGIN
    INSERT INTO Category (categoryName, images, status) VALUES
    (N'Dien thoai', N'cat1.png', 1),
    (N'Laptop', N'cat2.png', 1),
    (N'Phu kien', N'cat3.png', 1);
END
GO

IF NOT EXISTS (SELECT 1 FROM Product)
BEGIN
    INSERT INTO Product (productName, productCode, categoryId, description, price, amount, stock, images, wishlist, status, createDate, sellerId) VALUES
    (N'iPhone 17', 100001, 1, N'Dien thoai iPhone 17 chinh hang', 25990000, 10, 10, N'iphone17.png', 0, 1, GETDATE(), 1),
    (N'Macbook Air M4', 100002, 2, N'Laptop Macbook Air chip M4', 27990000, 5, 5, N'macbook.png', 0, 1, GETDATE(), 1),
    (N'Samsung S26', 100003, 1, N'Dien thoai Samsung Galaxy S26', 21990000, 8, 8, N'samsung.png', 0, 1, GETDATE(), 2),
    (N'Tai nghe Bluetooth', 100004, 3, N'Tai nghe khong day', 990000, 20, 20, N'tainghe.png', 0, 1, GETDATE(), 2);
END
GO

/* ================= Bo sung cot cho don hang COD (chay duoc nhieu lan, DB cu cung chay duoc) ================= */
/* Cart.status: 0 = gio hang dang dung, 1 = da dat hang (COD) */
IF COL_LENGTH('dbo.Cart', 'receiverName') IS NULL
BEGIN
    ALTER TABLE Cart ADD
        receiverName    NVARCHAR(100) NULL,
        receiverPhone   NVARCHAR(20)  NULL,
        receiverAddress NVARCHAR(300) NULL,
        note            NVARCHAR(300) NULL,
        paymentMethod   NVARCHAR(20)  NULL,
        totalAmount     FLOAT         NULL;
END
GO

/* Moi user chi co toi da 1 gio hang dang dung (status = 0) */
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'UX_Cart_User_Active' AND object_id = OBJECT_ID('dbo.Cart'))
BEGIN
    CREATE UNIQUE INDEX UX_Cart_User_Active ON Cart(userId) WHERE status = 0;
END
GO
