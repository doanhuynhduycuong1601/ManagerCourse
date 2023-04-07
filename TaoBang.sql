create database EduSys
go
use EduSys

go
CREATE TABLE NHANVIEN(
	STaffID NVARCHAR(20) PRIMARY KEY,
	PassWords VARCHAR(50) NOT NULL,
	NAMES NVARCHAR(50) NOT NULL,
	ROLES BIT Default 0
)

GO
CREATE TABLE CHUYENDE(
	MACD NCHAR(5) PRIMARY KEY,
	NAMECD NVARCHAR(50) NOT NULL,
	Fee MONEY NOT NULL,
	Times int NOT NULL,
	Images Nvarchar(50) NOT NULL,
	Describe NVARCHAR(255) NOT NULL,
)

GO
CREATE TABLE Course(
	CourseID INT IDENTITY(1,1) PRIMARY KEY,
	STaffID NVARCHAR(20) NOT NULL,
	MACD NCHAR(5) NOT NULL,
	Fee money NOT NULL,
	Times int NOT NULL,
	OpenDate DATE NOT NULL,
	Note NVARCHAR(255),
	CreateDate DATE Default getdate(),
	FOREIGN KEY (MACD) REFERENCES CHUYENDE(MACD) ON DELETE NO ACTION ON UPDATE CASCADE,
	FOREIGN KEY(STaffID) REFERENCES NHANVIEN(STaffID) ON DELETE NO ACTION ON UPDATE CASCADE
)

GO
CREATE TABLE NGUOIHOC(
	MANH NVARCHAR(7) PRIMARY KEY,
	STaffID NVARCHAR(20) NOT NULL,
	Names NVARCHAR(50) NOT NULL,
	GENDER BIT DEFAULT 1,
	DateBorn DATE NOT NULL,
	Email NVARCHAR(50) NOT NULL,
	Phone CHAR(10) NOT NULL,
	Note NVARCHAR(255),
	RegisterDate DATE Default getdate(),
	FOREIGN KEY(STaffID) REFERENCES NHANVIEN(STaffID) ON DELETE NO ACTION ON UPDATE CASCADE
) 

GO
CREATE TABLE HOCVIEN(
	MAHV INT IDENTITY(1,1) PRIMARY KEY,
	MANH NVARCHAR(7) NOT NULL,
	CourseID INT NOT NULL,
	Grade float DEFAULT -1,
	FOREIGN KEY(MANH) REFERENCES NGUOIHOC(MANH) ON DELETE NO ACTION ON UPDATE CASCADE,
	FOREIGN KEY(CourseID) REFERENCES Course(CourseID)
)
drop proc if exists SP_LuongNguoiHoc
go
CREATE PROC SP_LuongNguoiHoc
as
BEGIN
	SELECT 
		YEAR(RegisterDate) years, 
		COUNT(*) quantity, 
		MIN(RegisterDate) firstt, 
		MAX(RegisterDate) lastt
	FROM NGUOIHOC group by YEAR(RegisterDate)
END


drop proc if exists sp_GradeTable
GO
CREATE PROC sp_GradeTable @CourseID int
as
begin
	select 
		NH.MANH, 
		NH.Names, 
		HV.Grade
	from HOCVIEN HV JOIN NGUOIHOC NH ON NH.MANH = HV.MANH
	WHERE HV.CourseID = @CourseID
	ORDER BY HV.Grade
end
GO
CREATE PROC sp_GradeCD
as
begin
	SELECT
			NAMECD CHUYENDE,
			COUNT(MAHV) SOHV,
			MIN(Grade) ThapNhat,
			MAX(Grade) CaoNhat,
			AVG(Grade) TrungBinh
	FROM Course C
		JOIN HOCVIEN HV ON C.CourseID = HV.CourseID
		JOIN CHUYENDE CD ON CD.MACD = C.MACD
	GROUP BY NAMECD
end


drop proc if exists sp_Turnover
go
CREATE PROC sp_Turnover @Year int
as
begin
	SELECT 
			NAMECD ChuyenDe,
			COUNT(HV.MAHV) SoHV,
			SUM(c.Fee) Turnover,
			MIN(c.Fee) ThapNhat,
			MAX(c.Fee) CaoNhat,
			AVG(c.Fee) TrungBinh
	FROM Course C
		JOIN HOCVIEN HV ON C.CourseID = HV.CourseID
		JOIN CHUYENDE CD ON CD.MACD = C.MACD
	WHERE YEAR(OpenDate) = @Year
	GROUP BY NAMECD
end
exec sp_Turnover 2022

insert into NHANVIEN values('nv1','12345',N'Cýõng',1)
insert into NHANVIEN values('nv2','12345',N'Cýõng',0)

select * from NGUOIHOC
select * from course
select * from CHUYENDE
select * from NHANVIEN


SELECT DISTINCT YEAR(OpenDate) FROM Course ORDER BY YEAR(OpenDate) desc