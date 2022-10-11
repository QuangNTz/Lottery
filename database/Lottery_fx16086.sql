CREATE DATABASE Lottery
GO
USE [Lottery]
/*------------------------------------------------------*/
CREATE TABLE Account(
	[user_mail] VARCHAR(100) NOT NULL PRIMARY KEY,
	[password] VARCHAR(64) NOT NULL,
	[account_role] INT NOT NULL,
	[user_name] NVARCHAR(50) NOT NULL,
	[phone_number] VARCHAR(15) NOT NULL,
	[user_status] INT NOT NULL,
)
GO
/*------------------------------------------------------*/
CREATE TABLE LotteryCOM(
	[lottery_com] NVARCHAR(100) PRIMARY KEY,
)
GO
/*------------------------------------------------------*/
CREATE TABLE ResultSheet(
	[lottery_id] INT NOT NULL PRIMARY KEY IDENTITY(1,1),
	[lottery_com] NVARCHAR(100) NOT NULL,
	[lottery_date] DATE NOT NULL,
	[lottery_8] VARCHAR(10) NOT NULL,
	[lottery_7] VARCHAR(10) NOT NULL,
	[lottery_6] VARCHAR(50) NOT NULL,
	[lottery_5] VARCHAR(50) NOT NULL,
	[lottery_4] VARCHAR(50) NOT NULL,
	[lottery_3] VARCHAR(50) NOT NULL,
	[lottery_2] VARCHAR(10) NOT NULL,
	[lottery_1] VARCHAR(10) NOT NULL,
	[lottery_sp] VARCHAR(10) NOT NULL,
	UNIQUE(lottery_com,lottery_date),
	FOREIGN KEY (lottery_com) REFERENCES dbo.LotteryCOM(lottery_com)
)
GO
/*------------------------------------------------------*/
CREATE TABLE History(
	[history_id] INT NOT NULL PRIMARY KEY IDENTITY(1,1),
	[history_date] DATETIME NOT NULL DEFAULT GETDATE(),
	[user_mail] VARCHAR(100) NOT NULL,
	[lottery_id] INT NOT NULL,
	[history_number] VARCHAR(10) NOT NULL,
	[history_result] NVARCHAR(100) NOT NULL,
	FOREIGN KEY(user_mail) REFERENCES dbo.Account(user_mail),
	FOREIGN KEY(lottery_id) REFERENCES dbo.Resultsheet(lottery_id),
)
GO
/*------------------------------------------------------*/
INSERT INTO dbo.LotteryCOM (lottery_com)
VALUES (N'TP.HCM')
INSERT INTO dbo.LotteryCOM (lottery_com)
VALUES (N'Tây Ninh')
INSERT INTO dbo.LotteryCOM (lottery_com)
VALUES (N'Bình Dương')
GO
/*------------------------------------------------------*/
SET IDENTITY_INSERT dbo.ResultSheet ON
GO

INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (66, N'TP.HCM', CAST(N'2022-08-01' AS Date), N'72', N'305', N'0973,5493,4485', N'9997', N'11632,75594,30138,85137,89015,90019,42842', N'75506,83643', N'68477', N'80049', N'306493')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (67, N'TP.HCM', CAST(N'2022-08-02' AS Date), N'77', N'170', N'9496,1421,1513', N'6313', N'39266,69409,27646,29810,34477,84551,84306', N'03965,96695', N'78910', N'07284', N'481534')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (68, N'TP.HCM', CAST(N'2022-08-03' AS Date), N'92', N'797', N'2471,3474,1778', N'0119', N'35718,30602,40102,54723,59782,84905,57921', N'00314,24539', N'37903', N'13693', N'959695')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (69, N'TP.HCM', CAST(N'2022-08-04' AS Date), N'08', N'364', N'1596,8128,5348', N'7635', N'63621,21252,71015,57538,33855,83173,32257', N'98729,34846', N'98261', N'64359', N'900955')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (70, N'TP.HCM', CAST(N'2022-08-05' AS Date), N'24', N'295', N'7954,5279,9745', N'9760', N'37187,67186,72750,19706,45193,73027,71682', N'77338,90532', N'71867', N'99197', N'447907')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (71, N'Tây Ninh', CAST(N'2022-08-06' AS Date), N'25', N'325', N'2544,5493,2546', N'3658', N'21542,25632,30138,85137,89015,21452,42842', N'75506,25412', N'21478', N'84526', N'684421')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (72, N'Tây Ninh', CAST(N'2022-08-07' AS Date), N'56', N'214', N'2547,1421,6842', N'2254', N'25648,69409,25465,29810,21458,25488,66478', N'21547,25698', N'21475', N'25468', N'214587')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (73, N'Tây Ninh', CAST(N'2022-08-08' AS Date), N'25', N'554', N'5458,5549,3345', N'2458', N'52174,56874,25412,25478,59782,25477,55488', N'25489,24539', N'25488', N'25477', N'245218')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (74, N'Tây Ninh', CAST(N'2022-08-09' AS Date), N'08', N'221', N'2547,3685,1155', N'5477', N'25455,21252,25477,57538,62544,24456,57788', N'24576,21476', N'95246', N'89546', N'875234')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (75, N'Tây Ninh', CAST(N'2022-08-10' AS Date), N'45', N'123', N'6524,5874,5879', N'5412', N'36424,22456,21146,19706,25421,73027,25485', N'21456,87452', N'54126', N'62489', N'247786')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (76, N'TP.HCM', CAST(N'2022-08-11' AS Date), N'72', N'305', N'0973,5493,4485', N'9997', N'11632,75594,30138,85137,89015,90019,42842', N'75506,83643', N'68477', N'80049', N'306493')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (77, N'TP.HCM', CAST(N'2022-08-12' AS Date), N'77', N'170', N'9496,1421,1513', N'6313', N'39266,69409,27646,29810,34477,84551,84306', N'03965,96695', N'78910', N'07284', N'481534')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (78, N'TP.HCM', CAST(N'2022-08-13' AS Date), N'92', N'797', N'2471,3474,1778', N'0119', N'35718,30602,40102,54723,59782,84905,57921', N'00314,24539', N'37903', N'13693', N'959695')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (79, N'TP.HCM', CAST(N'2022-08-14' AS Date), N'08', N'364', N'1596,8128,5348', N'7635', N'63621,21252,71015,57538,33855,83173,32257', N'98729,34846', N'98261', N'64359', N'900955')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (80, N'TP.HCM', CAST(N'2022-08-15' AS Date), N'24', N'295', N'7954,5279,9745', N'9760', N'37187,67186,72750,19706,45193,73027,71682', N'77338,90532', N'71867', N'99197', N'447907')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (81, N'Tây Ninh', CAST(N'2022-08-16' AS Date), N'25', N'325', N'2544,5493,2546', N'3658', N'21542,25632,30138,85137,89015,21452,42842', N'75506,25412', N'21478', N'84526', N'684421')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (82, N'Tây Ninh', CAST(N'2022-08-17' AS Date), N'56', N'214', N'2547,1421,6842', N'2254', N'25648,69409,25465,29810,21458,25488,66478', N'21547,25698', N'21475', N'25468', N'214587')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (83, N'Tây Ninh', CAST(N'2022-08-18' AS Date), N'25', N'554', N'5458,5549,3345', N'2458', N'52174,56874,25412,25478,59782,25477,55488', N'25489,24539', N'25488', N'25477', N'245218')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (84, N'Tây Ninh', CAST(N'2022-08-19' AS Date), N'08', N'221', N'2547,3685,1155', N'5477', N'25455,21252,25477,57538,62544,24456,57788', N'24576,21476', N'95246', N'89546', N'875234')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (85, N'Tây Ninh', CAST(N'2022-08-20' AS Date), N'45', N'123', N'6524,5874,5879', N'5412', N'36424,22456,21146,19706,25421,73027,25485', N'21456,87452', N'54126', N'62489', N'247786')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (86, N'Bình Dương', CAST(N'2022-08-21' AS Date), N'45', N'123', N'6524,5874,5879', N'5412', N'36424,22456,21146,19706,25421,73027,25485', N'21456,87452', N'54126', N'62489', N'247786')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (87, N'Bình Dương', CAST(N'2022-08-22' AS Date), N'25', N'325', N'2544,5493,2546', N'3658', N'21542,25632,30138,85137,89015,21452,42842', N'75506,25412', N'21478', N'84526', N'684421')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (88, N'Bình Dương', CAST(N'2022-08-23' AS Date), N'56', N'214', N'2547,1421,6842', N'2254', N'25648,69409,25465,29810,21458,25488,66478', N'21547,25698', N'21475', N'25468', N'214587')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (89, N'Bình Dương', CAST(N'2022-08-24' AS Date), N'25', N'554', N'5458,5549,3345', N'2458', N'52174,56874,25412,25478,59782,25477,55488', N'25489,24539', N'25488', N'25477', N'245218')
INSERT [dbo].[ResultSheet] ([lottery_id], [lottery_com], [lottery_date], [lottery_8], [lottery_7], [lottery_6], [lottery_5], [lottery_4], [lottery_3], [lottery_2], [lottery_1], [lottery_sp]) VALUES (90, N'Bình Dương', CAST(N'2022-08-25' AS Date), N'08', N'221', N'2547,3685,1155', N'5477', N'25455,21252,25477,57538,62544,24456,57788', N'24576,21476', N'95246', N'89546', N'875234')

SET IDENTITY_INSERT dbo.ResultSheet OFF
GO
/*------------------------------------------------------*/
INSERT [dbo].[Account] ([user_mail], [password], [account_role], [user_name], [phone_number], [user_status]) VALUES (N'001@gmail.com', N'202cb962ac59075b964b07152d234b70', 0, N'Quang 001', N'0935.214.568', 1)
INSERT [dbo].[Account] ([user_mail], [password], [account_role], [user_name], [phone_number], [user_status]) VALUES (N'002@gmail.com', N'202cb962ac59075b964b07152d234b70', 0, N'Quang 002', N'0925.412.549', 1)
INSERT [dbo].[Account] ([user_mail], [password], [account_role], [user_name], [phone_number], [user_status]) VALUES (N'003@gmail.com', N'202cb962ac59075b964b07152d234b70', 0, N'Quang 003', N'0924.852.368', 1)
INSERT [dbo].[Account] ([user_mail], [password], [account_role], [user_name], [phone_number], [user_status]) VALUES (N'004@gmail.com', N'202cb962ac59075b964b07152d234b70', 0, N'Quang 004', N'0958.745.965', 1)
INSERT [dbo].[Account] ([user_mail], [password], [account_role], [user_name], [phone_number], [user_status]) VALUES (N'005@gmail.com', N'202cb962ac59075b964b07152d234b70', 0, N'Quang 005', N'0912.489.648', 1)
INSERT [dbo].[Account] ([user_mail], [password], [account_role], [user_name], [phone_number], [user_status]) VALUES (N'006@gmail.com', N'202cb962ac59075b964b07152d234b70', 0, N'Quang 006', N'0932.145.214', 0)
INSERT [dbo].[Account] ([user_mail], [password], [account_role], [user_name], [phone_number], [user_status]) VALUES (N'007@gmail.com', N'202cb962ac59075b964b07152d234b70', 0, N'Quang 007', N'0965.847.535', 1)
INSERT [dbo].[Account] ([user_mail], [password], [account_role], [user_name], [phone_number], [user_status]) VALUES (N'008@gmail.com', N'202cb962ac59075b964b07152d234b70', 2, N'Quang 008', N'0956.478.542', 1)
INSERT [dbo].[Account] ([user_mail], [password], [account_role], [user_name], [phone_number], [user_status]) VALUES (N'admin@gmail.com', N'202cb962ac59075b964b07152d234b70', 1, N'Hùng Nguyễn Admin', N'0911.111.111', 1)
/*INSERT [dbo].[Account] ([user_mail], [password], [account_role], [user_name], [phone_number], [user_status]) VALUES (N'quangntk9b@gmail.com', N'202cb962ac59075b964b07152d234b70', 0, N'Quang Nguyễn', N'0935.214.568', 1)*/
/*------------------------------------------------------*/
SET IDENTITY_INSERT [dbo].[History] ON 
GO

INSERT [dbo].[History] ([history_id], [history_date], [user_mail], [lottery_id], [history_number], [history_result]) VALUES (34, CAST(N'2022-09-28T13:41:05.703' AS DateTime), N'001@gmail.com', 85, N'125874', N'Bạn đã trúng giải sáu trị giá 400.000 đ')
INSERT [dbo].[History] ([history_id], [history_date], [user_mail], [lottery_id], [history_number], [history_result]) VALUES (35, CAST(N'2022-09-28T13:42:51.160' AS DateTime), N'001@gmail.com', 85, N'125123', N'Bạn đã trúng giải bảy trị giá 200.000 đ')
INSERT [dbo].[History] ([history_id], [history_date], [user_mail], [lottery_id], [history_number], [history_result]) VALUES (36, CAST(N'2022-09-28T13:43:01.440' AS DateTime), N'001@gmail.com', 85, N'154126', N'Bạn đã trúng giải nhì trị giá 15.000.000 đ')
INSERT [dbo].[History] ([history_id], [history_date], [user_mail], [lottery_id], [history_number], [history_result]) VALUES (38, CAST(N'2022-09-28T13:43:17.310' AS DateTime), N'001@gmail.com', 85, N'254255', N'Rất tiếc bạn đã không trúng giải')
INSERT [dbo].[History] ([history_id], [history_date], [user_mail], [lottery_id], [history_number], [history_result]) VALUES (40, CAST(N'2022-09-28T14:07:22.083' AS DateTime), N'001@gmail.com', 85, N'647786', N'Bạn đã trúng giải phụ đặc biệt trị giá 50.000.000 đ')

SET IDENTITY_INSERT [dbo].[History] OFF
GO
