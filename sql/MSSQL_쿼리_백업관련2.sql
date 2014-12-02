USE [MILES]
DELETE FROM HERDIN
EXEC HERDIN_INC_100
SELECT COUNT(*) FROM HERDIN
EXEC HERDIN_BACKUP_FULL

EXEC HERDIN_INC_100
SELECT COUNT(*) FROM HERDIN
EXEC HERDIN_BACKUP_DIFF

DELETE FROM HERDIN
SELECT COUNT(*) FROM HERDIN
----------------------------------------------------
USE [MASTER]
DECLARE @FILE INT
DECLARE @E INT
SET @FILE = 1

RESTORE DATABASE [MILES] FROM DISK = 'D:\MILES_BACKUP.bak' WITH FILE = @FILE, NORECOVERY,
						MOVE 'DT_1229' TO 'C:\MILES.mdf',
						MOVE 'DT_1229_log' TO 'C:\MILES_log.ldf'
SET @E = (SELECT @@ERROR)
WHILE @E = 0
	BEGIN
--	SELECT	CONVERT(VARCHAR, @FILE) + ' RESTORE..' AS [RESTORE STATUS],
--		@E AS [ERROR NUMBER]
	SET @FILE = @FILE + 1
	RESTORE HEADERONLY FROM DISK = 'D:\MILES_BACKUP.bak' WITH FILE = @FILE
	SET @E = (SELECT @@ERROR)
	END
SET @FILE = @FILE - 1
SELECT @FILE AS [LAST FILE NUM]
RESTORE DATABASE [MILES] FROM DISK = 'D:\MILES_BACKUP.bak' WITH FILE = @FILE, RECOVERY,
						MOVE 'DT_1229' TO 'C:\MILES.mdf',
						MOVE 'DT_1229_log' TO 'C:\MILES_log.ldf'
--------------------------------------------------------------
alter database MILES 
set single_user 
with rollback immediate





declare @spid varchar(5) 
declare @count int 
set @count = (select count(spid) from master.dbo.sysprocesses where program_name = 'Miles') 
if @count = 3 begin 
set @spid = (select max(spid) from master.dbo.sysprocesses where program_name = 'Miles') 
declare @sqlExec varchar(50) 
set @sqlExec = 'KILL ' + @spid 
exec(@sqlExec) end
drop database MILES

--------------------------------------------------------------
exec sp_who2
RESTORE FILELISTONLY FROM DISK = 'D:\MILES_BACKUP.bak'


USE [MASTER]
DECLARE @FILE INT
SET @FILE = 1

RESTORE DATABASE [MILES] FROM DISK = 'D:\MILES_BACKUP.bak' WITH FILE = 1, NORECOVERY
DECLARE @E INT
SET @E = (SELECT @@ERROR)
WHILE @E = 0
	BEGIN
	SET @FILE = @FILE + 1
	SELECT	CONVERT(VARCHAR, @FILE) + ' RESTORE' AS [RESTORE STATUS],
		@E AS [ERROR NUMBER]
	RESTORE HEADERONLY FROM DISK = 'D:\MILES_BACKUP.bak' WITH FILE = @FILE
	SET @E = (SELECT @@ERROR)
	END
SET @FILE = @FILE - 1
SELECT @FILE AS [LAST FILE NUM]
RESTORE DATABASE [MILES] FROM DISK = 'D:\MILES_BACKUP.bak' WITH FILE =4, RECOVERY

DECLARE @E INT
RESTORE DATABASE [MILES] FROM DISK = 'D:\MILES_BACKUP.bak' WITH FILE = 5, NORECOVERY
SET @E = (SELECT @@ERROR)
SELECT @E AS E