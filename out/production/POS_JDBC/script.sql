DROP DATABASE IF EXISTS SuperMarket;
CREATE DATABASE IF NOT EXISTS SuperMarket;
SHOW DATABASES ;
USE SuperMarket;

DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer(
    CustId VARCHAR(15),
    CustTitle VARCHAR(5),
    CustName VARCHAR(30) NOT NULL DEFAULT 'Unknown',
    CustAddress VARCHAR(30),
    City VARCHAR(20),
    Province VARCHAR(20),
    PostalCode VARCHAR(9),
    CONSTRAINT PRIMARY KEY (CustId)
    );
SHOW TABLES ;
DESCRIBE Customer;

DROP TABLE IF EXISTS `Orders`;
CREATE TABLE IF NOT EXISTS `Orders`(
    orderId VARCHAR(15),
    cId VARCHAR(15),
    orderDate DATE,
    CONSTRAINT PRIMARY KEY (orderId),
    CONSTRAINT FOREIGN KEY (cId) REFERENCES Customer(CustId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE `Orders`;

DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item(
    itemCode VARCHAR(6),
    description VARCHAR(50),
    packSize VARCHAR(20),
    qtyOnHand INT(5) DEFAULT 0,
    unitProfit DECIMAL(6,2),
    unitPrice DECIMAL(6,2) DEFAULT 0.00,
    CONSTRAINT PRIMARY KEY (itemCode)
    );
SHOW TABLES ;
DESCRIBE Item;

DROP TABLE IF EXISTS `OrderDetail`;
CREATE TABLE IF NOT EXISTS `OrderDetail`(
    orderId VARCHAR(15),
    itemCode VARCHAR(15),
    orderQty INT(11),
    discount DECIMAL(6,2) DEFAULT 0.00,
    OrderedItemTotal DECIMAL(6,2) DEFAULT 0.00,
    CONSTRAINT PRIMARY KEY (orderId,itemCode),
    CONSTRAINT FOREIGN KEY (orderId) REFERENCES `Orders`(orderId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (itemCode) REFERENCES Item(itemCode) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE `OrderDetail`;

INSERT INTO Customer VALUES('C-001','T-01','Janith','Colombo','Piliyandala','Western','P-999'),
('C-002','T-02','Sandaru','Galle','Sooriyawewa','Southern','P-899'),
('C-004','T-04','Tharindu','Colombo','Panadura','Central','P-201'),
('C-005','T-05','Vijaya','','Colombo','Panadura','P-199'),
('C-006','T-06','Ajith','Horana','Pokunuwita','Western','P-891');

SELECT * FROM Customer;

--INSERT INTO Orders VALUES('O-001','C-001','2021-09-21'),
--('O-002','C-003','2021-09-21'),
--('O-003','C-003','2021-09-21'),
--('O-004','C-002','2021-09-20'),
--('O-005','C-002','2021-09-20'),
--('O-006','C-004','2021-09-20'),
--('O-007','C-005','2021-09-20'),
--('O-008','C-006','2021-09-19'),
--('O-009','C-002','2021-09-19');

SELECT * FROM Orders;

INSERT INTO Item VALUES('I-001','Sugar','1 kg',50,16.00,110.00),
('I-002','Samba Rice','1 kg',30,21.00,140.00),
('I-003','Flour','1 kg',50,20.00,80.00),
('I-004','Potato','500 g',30,18.00,120.00),
('I-005','Onions','500 g',30,27.00,110.00),
('I-006','Chocolate Biscuit','300 g',40,5.00,170.00),
('I-007','Cream Cracker','300 g',40,6.00,160.00),
('I-008','Chicken','1 kg',60,34.00,630.00),
('I-009','Kakulu Rice','1 kg',50,21.00,105.00),
('I-010','Noodles','400 g',70,12.00,190.00),
('I-011','Dhal','1 kg',50,23.00,108.00);

SELECT * FROM Item;

--INSERT INTO `OrderDetail` VALUES('O-001','I-001',5,12.00,538.00),
--('O-001','I-002',7,14.00,966.00),
--('O-001','I-004',8,16.00,1104.00),
--('O-002','I-003',9,23.00,967.00),
--('O-002','I-004',8,16.00,1104.00),
--('O-002','I-005',8,16.00,1104.00),
--('O-003','I-007',8,16.00,1104.00),
--('O-004','I-010',8,16.00,1104.00),
--('O-004','I-011',8,16.00,1104.00),
--('O-004','I-005',8,16.00,1104.00),
--('O-005','I-003',8,16.00,1104.00),
--('O-006','I-009',8,16.00,1104.00),
--('O-006','I-008',8,16.00,1104.00),
--('O-007','I-001',8,16.00,1104.00),
--('O-008','I-004',8,16.00,1104.00),
--('O-008','I-002',8,16.00,1104.00),
--('O-008','I-006',8,16.00,1104.00),
--('O-008','I-009',8,16.00,1104.00),
--('O-009','I-002',8,16.00,1104.00);

SELECT * FROM OrderDetail;

SELECT itemDTO.itemCode, itemDTO.unitProfit, OrderDetail.orderQty, Orders.orderDate
FROM itemDTO JOIN OrderDetail ON itemDTO.itemCode=orderDetail.itemCode JOIN orders ON orderDetail.orderId=orders.orderId
WHERE orderDate='2021-08-21';

SELECT DISTINCT itemDTO.itemCode, itemDTO.description, itemDTO.unitProfit, OrderDetail.orderQty,OrderDetail.discount, orderDetail.orderedItemTotal
FROM itemDTO JOIN OrderDetail ON itemDTO.itemCode=orderDetail.itemCode JOIN orders ON orderDetail.orderId=orders.orderId
WHERE orderDate='2021-8-^[0-9]*$';

SELECT DISTINCT itemDTO.itemCode, itemDTO.description, itemDTO.unitProfit, OrderDetail.orderQty,OrderDetail.discount, orderDetail.orderedItemTotal
FROM itemDTO JOIN OrderDetail ON itemDTO.itemCode=orderDetail.itemCode JOIN orders ON orderDetail.orderId=orders.orderId
WHERE orderDate BETWEEN '2021-8-1' AND '2021-8-31' GROUP BY orderDetail.itemCode;

SELECT DISTINCT itemDTO.itemCode, itemDTO.description, itemDTO.unitProfit, OrderDetail.orderQty,OrderDetail.discount, orderDetail.orderedItemTotal
FROM itemDTO JOIN OrderDetail ON itemDTO.itemCode=orderDetail.itemCode JOIN orders ON orderDetail.orderId=orders.orderId
WHERE cId='C-002';

DELETE FROM OrderDetail;

-----------------------------------------------------------------------------------------------------------
SELECT itemDTO.itemCode, itemDTO.unitProfit, SUM(OrderDetail.orderQty), Orders.orderDate
FROM itemDTO JOIN OrderDetail ON itemDTO.itemCode=orderDetail.itemCode JOIN orders ON orderDetail.orderId=orders.orderId
WHERE orderDate='2021-08-21' GROUP BY itemDTO.itemCode;

SELECT itemDTO.itemCode, itemDTO.unitProfit, OrderDetail.orderQty, Orders.orderDate
FROM itemDTO JOIN OrderDetail ON itemDTO.itemCode=orderDetail.itemCode JOIN orders ON Item.itemCode=OrderDetail.itemCode
WHERE orderDate='2021-08-21';

SELECT orderDetail.itemCode, itemDTO.unitProfit, OrderDetail.orderQty, Orders.orderDate
FROM itemDTO JOIN OrderDetail ON itemDTO.itemCode=orderDetail.itemCode JOIN orders ON orderDetail.orderId=orders.orderId
WHERE orderDate='2021-08-21' GROUP BY orderDetail.itemCode;

//----------------------------------------------------------------------------------------------------

DROP TABLE IF EXISTS `Users`;
CREATE TABLE IF NOT EXISTS `Users`(
    userName VARCHAR(15),
    password VARCHAR(15),
    accountType VARCHAR(15),
    CONSTRAINT PRIMARY KEY (userName)
    );
SHOW TABLES ;
DESCRIBE `Users`;

INSERT INTO Users VALUES('Janith','2001','Admin'),
('Sandaru','2000','Reception'),
('Kamal','4000','Reception');

SELECT * FROM Users;


//---------------------------------------------------------------------------------------------------

--SELECT itemDTO.itemCode, itemDTO.description,SUM(OrderDetail.orderQty), itemDTO.unitProfit, OrderDetail.discount
--FROM itemDTO JOIN OrderDetail ON itemDTO.itemCode=orderDetail.itemCode JOIN orders ON orderDetail.orderId=orders.orderId
--ORDER BY cId='C-006' ;
--
--SELECT OrderDetail.itemCode, OrderDetail.description,SUM(OrderDetail.orderQty), itemDTO.unitProfit, OrderDetail.discount
--FROM Orders INNER JOIN OrderDetail ON Orders.orderId=OrderDetail.orderId
--WHERE cId='C-006';

SELECT DISTINCT orders.cId, OrderDetail.orderQty,OrderDetail.discount, orderDetail.orderedItemTotal
FROM itemDTO JOIN OrderDetail ON itemDTO.itemCode=orderDetail.itemCode JOIN orders ON orderDetail.orderId=orders.orderId
GROUP BY orders.cId;