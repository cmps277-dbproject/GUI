package program;

import java.awt.EventQueue;
import java.sql.SQLException;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

//					createdb();
					Window.createWindow(args);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void createdb() {
		try {
			DatabaseRetrieval.executeUpdate("DROP SCHEMA IF EXISTS clothesshop;");
			DatabaseRetrieval.executeUpdate("CREATE SCHEMA clothesshop;");

			DatabaseRetrieval.executeUpdate("DROP TABLE IF EXISTS clothesshop.Worker;");
			DatabaseRetrieval.executeUpdate("CREATE TABLE clothesshop.Worker\r\n" + "(\r\n"
					+ "	WorkerID INT AUTO_INCREMENT,\r\n" + "    Name VARCHAR(60) NOT NULL,\r\n"
					+ "    Address VARCHAR(60) NOT NULL,\r\n" + "    Phone VARCHAR(60) NOT NULL,\r\n"
					+ "  	Email VARCHAR(60) NOT NULL,\r\n" + "    PRIMARY KEY(WorkerID)\r\n" + ");");
			DatabaseRetrieval.executeUpdate("DROP TABLE IF EXISTS clothesshop.Manager;");
			DatabaseRetrieval.executeUpdate("CREATE TABLE clothesshop.Manager\r\n" + "(\r\n" + "	WorkerID INT,\r\n"
					+ "    PRIMARY KEY(WorkerID),\r\n" + "    FOREIGN KEY(WorkerID) REFERENCES Worker(WorkerID)\r\n"
					+ ");");
			DatabaseRetrieval.executeUpdate("DROP TABLE IF EXISTS clothesshop.Owner;");
			DatabaseRetrieval.executeUpdate("CREATE TABLE clothesshop.Owner\r\n" + "(\r\n" + "	WorkerID INT,\r\n"
					+ "    PRIMARY KEY(WorkerID),\r\n" + "    FOREIGN KEY(WorkerID) REFERENCES Worker(WorkerID)\r\n"
					+ ");");
			DatabaseRetrieval.executeUpdate("DROP TABLE IF EXISTS clothesshop.Employees;");
			DatabaseRetrieval.executeUpdate("CREATE TABLE clothesshop.Employees\r\n" + "(\r\n" + "	WorkerID INT,\r\n"
					+ "    Salary VARCHAR(60) NOT NULL,\r\n" + "    `Type` VARCHAR(60) NOT NULL,\r\n"
					+ "    Schedule VARCHAR(60) NOT NULL,\r\n" + "  	Department VARCHAR(60) NOT NULL,\r\n"
					+ "    PRIMARY KEY(WorkerID),\r\n" + "    FOREIGN KEY(WorkerID) REFERENCES Worker(WorkerID)\r\n"
					+ ");");
			DatabaseRetrieval.executeUpdate("DROP TABLE IF EXISTS clothesshop.Manages;");
			DatabaseRetrieval
					.executeUpdate("CREATE TABLE clothesshop.Manages\r\n" + "(\r\n" + "	EmployeeWorkerID INT,\r\n"
							+ "	ManagerWorkerID INT,\r\n" + "    PRIMARY KEY(EmployeeWorkerID, ManagerWorkerID),\r\n"
							+ "    FOREIGN KEY(EmployeeWorkerID) REFERENCES Worker(WorkerID),\r\n"
							+ "    FOREIGN KEY(ManagerWorkerID) REFERENCES Manager(WorkerID)\r\n" + ");");
			DatabaseRetrieval.executeUpdate("DROP TABLE IF EXISTS clothesshop.Clients;");
			DatabaseRetrieval.executeUpdate("create table clothesshop.Clients (\r\n"
					+ "   ClientID int  NOT NULL AUTO_INCREMENT,\r\n" + "   Name VARCHAR(50),          \r\n"
					+ "   Phone VARCHAR(12),           \r\n" + "   Address VARCHAR(100),\r\n"
					+ "   Email VARCHAR(20),\r\n" + "   PRIMARY KEY (ClientID)\r\n" + ");");
			DatabaseRetrieval.executeUpdate("DROP TABLE IF EXISTS clothesshop.Receipt;");
			DatabaseRetrieval.executeUpdate("create table clothesshop.Receipt (\r\n" + "   ClientID int NOT NULL,\r\n"
					+ "   ReceiptID int NOT NULL AUTO_INCREMENT,\r\n" + "   Date_Time datetime,\r\n"
					+ "   MethodOfPayment VARCHAR(20),\r\n" + "   WorkerID int,\r\n"
					+ "   PRIMARY KEY (ReceiptID, ClientID),\r\n"
					+ "   FOREIGN KEY (ClientID) REFERENCES Clients (ClientID)\r\n" + ");");
			DatabaseRetrieval.executeUpdate("CREATE INDEX ReceiptIdIndex on clothesshop.Receipt (ReceiptID ASC);");
			DatabaseRetrieval.executeUpdate("DROP TABLE IF EXISTS clothesshop.Suppliers;");
			DatabaseRetrieval.executeUpdate("create table clothesshop.Suppliers (\r\n"
					+ "   SupplierID int NOT NULL AUTO_INCREMENT,\r\n" + "   Name VARCHAR(50),\r\n"
					+ "   Phone VARCHAR(12) ,\r\n" + "   Country VARCHAR(50),\r\n" + "   `State` VARCHAR(50),\r\n"
					+ "   ZIP int,\r\n" + "   PRIMARY KEY (SupplierID)\r\n" + ");");
			DatabaseRetrieval
					.executeUpdate("CREATE INDEX SuppliersSupplierIdIndex on clothesshop.Suppliers (SupplierID ASC);");
			DatabaseRetrieval.executeUpdate("DROP TABLE IF EXISTS clothesshop.Clothes;");
			DatabaseRetrieval.executeUpdate("CREATE TABLE clothesshop.Clothes (\r\n"
					+ "    `ClothesID` int  NOT NULL AUTO_INCREMENT,\r\n" + "    `ClientID` int,\r\n"
					+ "    `WorkerID` int,\r\n" + "    `Color` VARCHAR(50),\r\n" + "    `Brand` VARCHAR(50),\r\n"
					+ "    `Material` VARCHAR(50),\r\n" + "    `Price` DECIMAL(4,2),\r\n" + "    `Gender` CHAR,\r\n"
					+ "    `Category` VARCHAR(100),\r\n" + "	PRIMARY KEY (ClothesID),\r\n"
					+ "	FOREIGN KEY (WorkerID) REFERENCES Worker (WorkerID),\r\n"
					+ "	FOREIGN KEY (ClientID) REFERENCES Clients (ClientID)\r\n" + ");");
			DatabaseRetrieval.executeUpdate("CREATE INDEX ClothesIdIndex on clothesshop.clothes (ClothesID ASC);");
			DatabaseRetrieval.executeUpdate("DROP TABLE IF EXISTS clothesshop.Tops;");
			DatabaseRetrieval.executeUpdate("CREATE TABLE clothesshop.Tops (\r\n"
					+ "    `ClothesID` int NOT NULL PRIMARY KEY REFERENCES `Clothes` (ClothesID),\r\n"
					+ "    `Sleeves` VARCHAR(50),\r\n" + "    `Collar` VARCHAR(50),\r\n" + "    `Size` VARCHAR(10),\r\n"
					+ "    `Type` VARCHAR(50)\r\n" + ");");
			DatabaseRetrieval.executeUpdate("CREATE INDEX TopsClothesIdIndex on clothesshop.Tops (ClothesID ASC);");
			DatabaseRetrieval.executeUpdate("DROP TABLE IF EXISTS clothesshop.Pants;");
			DatabaseRetrieval.executeUpdate("CREATE TABLE clothesshop.Pants (\r\n"
					+ "    `ClothesID` int NOT NULL PRIMARY KEY REFERENCES `Clothes` (ClothesID),\r\n"
					+ "    `Length` VARCHAR(50),\r\n" + "    `Fit` VARCHAR(50),\r\n"
					+ "    `WaistSize` VARCHAR(10),\r\n" + "    `Type` VARCHAR(50),\r\n"
					+ "    `Inseam` VARCHAR(50)\r\n" + ");");
			DatabaseRetrieval.executeUpdate("CREATE INDEX PantsClothesIdIndex on clothesshop.Pants (ClothesID ASC);");
			DatabaseRetrieval.executeUpdate("DROP TABLE IF EXISTS clothesshop.Hats;");
			DatabaseRetrieval.executeUpdate("CREATE TABLE clothesshop.Hats (\r\n"
					+ "    `ClothesID` int NOT NULL PRIMARY KEY REFERENCES `Clothes` (ClothesID),\r\n"
					+ "    `Size` VARCHAR(50),\r\n" + "    `Type` VARCHAR(50)\r\n" + ");");
			DatabaseRetrieval.executeUpdate("CREATE INDEX HatsClothesIdIndex on clothesshop.Hats (ClothesID ASC);");
			DatabaseRetrieval.executeUpdate("DROP TABLE IF EXISTS clothesshop.Shoes;");
			DatabaseRetrieval.executeUpdate("CREATE TABLE clothesshop.Shoes (\r\n"
					+ "    `ClothesID` int NOT NULL PRIMARY KEY REFERENCES `Clothes` (ClothesID),\r\n"
					+ "    `Size` VARCHAR(50),\r\n" + "    `Type` VARCHAR(50),\r\n" + "    `Laces` VARCHAR(50)\r\n"
					+ ");");
			DatabaseRetrieval.executeUpdate("CREATE INDEX ShoesClothesIdIndex on clothesshop.Shoes (ClothesID ASC);");
			DatabaseRetrieval.executeUpdate("DROP TABLE IF EXISTS clothesshop.Dresses;");
			DatabaseRetrieval.executeUpdate("CREATE TABLE clothesshop.Dresses (\r\n"
					+ "    `ClothesID` int NOT NULL PRIMARY KEY REFERENCES `Clothes` (ClothesID),\r\n"
					+ "    `Length` VARCHAR(50),\r\n" + "    `Type` VARCHAR(50)\r\n" + ");");
			DatabaseRetrieval
					.executeUpdate("CREATE INDEX DressesClothesIdIndex on clothesshop.Dresses (ClothesID ASC);");
			DatabaseRetrieval.executeUpdate("DROP TABLE IF EXISTS clothesshop.ClothesReceipt;");
			DatabaseRetrieval
					.executeUpdate("create table clothesshop.ClothesReceipt (\r\n" + "    ReceiptID int NOT NULL,\r\n"
							+ "    ClothesID int,\r\n" + "    Primary Key (ReceiptID, ClothesID),\r\n"
							+ "    FOREIGN KEY (ReceiptID) REFERENCES Receipt (ReceiptID),\r\n"
							+ "    FOREIGN KEY (ClothesID) REFERENCES Clothes (ClothesID)\r\n" + ");");
			DatabaseRetrieval.executeUpdate(
					"CREATE INDEX ClothesReceiptClothesIdIndex on clothesshop.ClothesReceipt (ClothesID ASC);");
			DatabaseRetrieval.executeUpdate(
					"CREATE INDEX ClothesReceiptReceiptIDIndex on clothesshop.ClothesReceipt (ReceiptID ASC);");
			DatabaseRetrieval.executeUpdate("DROP TABLE IF EXISTS clothesshop.Supply;");
			DatabaseRetrieval.executeUpdate("create table clothesshop.Supply (\r\n" + "   SupplierID int NOT NULL,\r\n"
					+ "   ClothesID int NOT NULL,\r\n" + "   PRIMARY KEY (SupplierID, ClothesID), \r\n"
					+ "   FOREIGN KEY (SupplierID) REFERENCES Suppliers (SupplierID),\r\n"
					+ "   FOREIGN KEY (ClothesId) REFERENCES Clothes (ClothesId)\r\n" + ");");
			DatabaseRetrieval.executeUpdate("CREATE INDEX SupplyClothesIdIndex on clothesshop.Supply (ClothesID ASC);");
			DatabaseRetrieval
					.executeUpdate("CREATE INDEX SupplySupplierIdIndex on clothesshop.Supply (SupplierID ASC);");
			DatabaseRetrieval.executeUpdate("DROP VIEW IF EXISTS clothesshop.AllClothes;");
			DatabaseRetrieval.executeUpdate("CREATE VIEW clothesshop.AllClothes AS\r\n"
					+ "SELECT clothes.ClothesID, clothes.Category, clothes.Color, clothes.Brand, clothes.Material, clothes.Price, clothes.Gender,\r\n"
					+ "    dresses.Length AS 'Dress length', \r\n" + "    dresses.Type AS 'Dress type',\r\n"
					+ "    hats.Size AS 'Hat size',\r\n" + "    hats.Type AS 'Hat type',\r\n"
					+ "    pants.Fit AS 'Pants fit',\r\n" + "    pants.Inseam AS 'Pants inseam',\r\n"
					+ "    pants.Length AS 'Pants length',\r\n" + "    pants.Type AS 'Pants type',\r\n"
					+ "    pants.WaistSize AS 'Pants waist size',\r\n" + "    shoes.Laces AS 'Shoe laces',\r\n"
					+ "    shoes.Size AS 'Shoe size',\r\n" + "    shoes.Type AS 'Shoe type',\r\n"
					+ "    tops.Collar AS 'Top collar',\r\n" + "    tops.Size AS 'Top size',\r\n"
					+ "    tops.Sleeves AS 'Top Sleeve',\r\n" + "    tops.Type AS 'Top type'\r\n"
					+ "    FROM clothesshop.clothes \r\n"
					+ "    	LEFT JOIN clothesshop.dresses ON clothes.ClothesID = dresses.ClothesID\r\n"
					+ "        LEFT JOIN clothesshop.hats ON clothes.ClothesID = hats.ClothesID\r\n"
					+ "        LEFT JOIN clothesshop.pants ON clothes.ClothesID = pants.ClothesID\r\n"
					+ "        LEFT JOIN clothesshop.shoes ON clothes.ClothesID = shoes.ClothesID\r\n"
					+ "        LEFT JOIN clothesshop.tops ON clothes.ClothesID = tops.ClothesID;");
			DatabaseRetrieval.executeUpdate("DROP VIEW IF EXISTS clothesshop.SuppliersClothes;");
			DatabaseRetrieval.executeUpdate("CREATE VIEW clothesshop.SuppliersClothes AS \r\n"
					+ "SELECT Name, Phone, Country, State, ZIP, clothesSupply.ClothesID, Brand, Category, Color, Gender, Material, Price\r\n"
					+ "FROM ( SELECT SupplierID, clothes.ClothesID, Brand, Category, ClientID, Color, Gender, Material, Price, WorkerID \r\n"
					+ "	FROM clothesshop.clothes \r\n"
					+ "	INNER JOIN clothesshop.supply ON clothes.ClothesID=supply.ClothesID ) AS clothesSupply \r\n"
					+ "INNER JOIN clothesshop.suppliers ON suppliers.SupplierID=clothesSupply.SupplierID;");
			DatabaseRetrieval.executeUpdate("DROP VIEW IF EXISTS clothesshop.AllClientsReceipts;");
			DatabaseRetrieval.executeUpdate("CREATE VIEW clothesshop.AllClientsReceipts AS \r\n"
					+ "SELECT clientid, receipt.ReceiptID, ClothesID,date_time, methodofpayment, category, color, brand, material, price, gender, workerid  \r\n"
					+ "FROM clothesshop.receipt\r\n"
					+ "JOIN (SELECT clothesreceipt.ReceiptID, clothesreceipt.ClothesID, allclothes.Category, allclothes.Color, allclothes.Brand, allclothes.Material, allclothes.Price, allclothes.Gender \r\n"
					+ "      FROM clothesshop.allclothes \r\n"
					+ "      JOIN clothesshop.clothesreceipt ON allclothes.ClothesID=clothesreceipt.ClothesID) AS clothesInReceipt\r\n"
					+ "ON receipt.ReceiptID=clothesinreceipt.receiptid;");

//		
//		
//		Inserting data
//		
//		
			DatabaseRetrieval
					.executeUpdate("INSERT INTO clothesshop.Worker\r\n" + "(`Name`, `Address`, `Phone`, `Email`) \r\n"
							+ "VALUES \r\n" + "( 'Keanu Reeves', 'America', '239 709-6359', 'keanu@reeves.com'), \r\n"
							+ "( 'Jack Black', 'England', '884 604-0425', 'jack@black.com'),\r\n"
							+ "( 'Joaquin Phoenix', 'America', '205 590-7140', 'joaquin@phoenix.org'), \r\n"
							+ "( 'Jennifer Lawrence', 'America' ,'223 548-5220', 'jennifer@lawrence.com'), \r\n"
							+ "( 'Lady Gaga', 'Atlantis', '214 156-4651', 'lady@gaga.com');");
			DatabaseRetrieval.executeUpdate("INSERT INTO clothesshop.Employees\r\n"
					+ "(`WorkerID`, `Salary`, `Type`, `Schedule`, `Department`) \r\n" + "VALUES \r\n"
					+ "('1', '1010', 'Cashier', 'M T W TR F', 'Shoes'), \r\n"
					+ "('2', '10000', 'Owner', 'M W F', 'Management'), \r\n"
					+ "('3', '7000', 'Manager', 'Weekdays', 'Management'), \r\n"
					+ "('4', '900', 'Cashier', 'Weekdays', 'Hats'), \r\n"
					+ "('5', '5000', 'Assistant Manager', 'Weekdays', 'Management');");
			DatabaseRetrieval.executeUpdate(
					"INSERT INTO clothesshop.Owner\r\n" + "(`WorkerID`) \r\n" + "VALUES \r\n" + "('2');");
			DatabaseRetrieval.executeUpdate(
					"INSERT INTO clothesshop.Manager\r\n" + "(`WorkerID`) \r\n" + "VALUES \r\n" + "('3');");
			DatabaseRetrieval.executeUpdate(
					"INSERT INTO clothesshop.Manages\r\n" + "(`EmployeeWorkerID`, `ManagerWorkerID`) \r\n"
							+ "VALUES \r\n" + "('1', '3'), \r\n" + "('4', '3');");
			DatabaseRetrieval
					.executeUpdate("INSERT INTO clothesshop.Clients\r\n" + "(`Name`, `Phone`, `Address`, `Email`) \r\n"
							+ "VALUES \r\n" + "( 'Ali', '03000000', 'Lebanon', 'ali@gmail.com'), \r\n"
							+ "( 'Bassam', '03000001', 'France', 'bassam@gmail.com'), \r\n"
							+ "( 'Carla', '03000002', 'Saida', 'carla@gmail.com'), \r\n"
							+ "( 'Daoud', '03000003', 'Sudan', 'daoud@gmail.com'), \r\n"
							+ "( 'Ema', '03000004', 'Brazil', 'ema@gmail.com');");
			DatabaseRetrieval.executeUpdate("INSERT INTO clothesshop.Receipt\r\n"
					+ "(`ClientID`, `Date_Time`, `MethodOfPayment`, `WorkerID`)  \r\n" + "VALUES \r\n"
					+ "('1', '1000-01-01 00:00:00.000000', 'Credit Card', '1'), \r\n"
					+ "('2', '1000-01-01 00:00:00.000000', 'Credit Card', '4'), \r\n"
					+ "('3', '1000-01-01 00:00:00.000000', 'Credit Card', '4'), \r\n"
					+ "('1', '1000-01-01 00:00:00.000000', 'Cash', '4'), \r\n"
					+ "('5', '1000-01-01 00:00:00.000000', 'Cash', '1');");
			DatabaseRetrieval.executeUpdate("INSERT INTO clothesshop.Clothes\r\n"
					+ "( `ClientID`, `WorkerID`, `Color`, `Brand`, `Material`, `Price`, `Gender`, `Category`) \r\n"
					+ "VALUES \r\n" + "( '1', '1', 'Yellow', 'H&M', 'silk', '22.22', 'F', 'dresses'),\r\n"
					+ "( '1', '4', 'Orange', 'gucci', 'cloth', '99.99', 'F', 'tops'), \r\n"
					+ "( '3', '4', 'black', 'converse', 'jeans', '50.76', 'X', 'shoes'),\r\n"
					+ "(NULL,NULL, 'blue', 'nike', 'cotton', '12.00', 'X', 'hats'),\r\n"
					+ "( '2', '4', 'blak', 'docker', 'leather', '49.99', 'F', 'pants'),\r\n"
					+ "( '2', '4', 'pink', 'polo', 'silk', '33.33', 'M', 'tops'), \r\n"
					+ "(NULL,NULL, 'white', 'filas', 'leather', '66.66', 'M', 'shoes'),\r\n"
					+ "(NULL,NULL, 'black', 'hanes', 'cotton', '10.00', 'X', 'tops'),\r\n"
					+ "( '5', '1', 'purple', 'H&M', 'silk', '59.00', 'F', 'dresses'),\r\n"
					+ "(NULL,NULL, 'green', 'under armour', 'khaki', '22.34', 'F', 'pants'), \r\n"
					+ "( '3', '4', 'yellow', 'adidas', 'cotton', '44.44', 'X', 'hats');");
			DatabaseRetrieval
					.executeUpdate("INSERT INTO clothesshop.Dresses \r\n" + "(`ClothesID`, `Length`, `Type`) \r\n"
							+ "VALUES \r\n" + "('1', '20cm', 'sun dress'), ('9', '40cm', 'night gown');");
			DatabaseRetrieval.executeUpdate(
					"INSERT INTO clothesshop.Tops\r\n" + "(`ClothesID`, `Sleeves`, `Collar`, `Size`, `Type`) \r\n"
							+ "VALUES \r\n" + "('2', 'long', 'no collar', 'XL', 'dress shirt'),\r\n"
							+ "('6', 'no sleeves', 'low collar', 'M', 'tank top'),\r\n"
							+ "('8', 'short', 'no collar', 'L', 'dress shirt');");
			DatabaseRetrieval.executeUpdate(
					"INSERT INTO clothesshop.Shoes\r\n" + "(`ClothesID`, `Size`, `Type`, `Laces`) \r\n" + "VALUES \r\n"
							+ "('3', '10', 'dress shoes', 'long black'),\r\n" + "('7', '13', 'sandals', 'no laces');");
			DatabaseRetrieval.executeUpdate("INSERT INTO clothesshop.Hats\r\n" + "(`ClothesID`, `Size`, `Type`) \r\n"
					+ "VALUES \r\n" + "('3', 'M', 'baseball hat'),\r\n" + "('11', 'S', 'bucket hat');\r\n" + "");
			DatabaseRetrieval.executeUpdate("INSERT INTO clothesshop.Pants\r\n"
					+ "(`ClothesID`, `Length`, `Fit`, `WaistSize`, `Type`, `Inseam`) \r\n" + "VALUES \r\n"
					+ "('5', '36in', 'slim', '30in', 'dress pants', '25in'),\r\n"
					+ "('10', '29in', 'baggy', '30in', 'cargo', '27in');");
			DatabaseRetrieval.executeUpdate("INSERT INTO clothesshop.ClothesReceipt\r\n" + "(ClothesID, ReceiptID)\r\n"
					+ "VALUES\r\n" + "('1','1'),\r\n" + "('5','2'),\r\n" + "('6','2'), 	\r\n" + "('3','3'),\r\n"
					+ "('11','3'), \r\n" + "('2','4'),\r\n" + "('9','5');");
			DatabaseRetrieval.executeUpdate(
					"INSERT INTO clothesshop.Suppliers\r\n" + "(`Name`, `Phone`, `Country`, `State`, `ZIP`)\r\n"
							+ "VALUES\r\n" + "('AliBaba', '0014 3185102', 'China', 'Xiang', '01120'),\r\n"
							+ "('Dior', '0014 4484512', 'Portugal', 'Lisbon', '01120');");
			DatabaseRetrieval.executeUpdate("INSERT INTO clothesshop.Supply\r\n" + "(`ClothesID`, `SupplierID`)\r\n"
					+ "VALUES\r\n" + "('1', '1'),\r\n" + "('2', '1'),\r\n" + "('3', '2'),\r\n" + "('4', '1'),\r\n"
					+ "('5', '2'),\r\n" + "('6', '2'),\r\n" + "('7', '1'),\r\n" + "('8', '2'),\r\n" + "('9', '2'),\r\n"
					+ "('10', '1'),\r\n" + "('11', '1');");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
