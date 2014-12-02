-- ���DBA�� ������/�ϻ��� ����
-- 1. ����� �α��� ����
-- 2. �ε��� ������
-- 3. DBCC (DB ��������)
-- 4. ���ο� ����� ���, ���� �ο�
-- 5. UPDATE STATISTICS - ���� �������� ����
-- 6. ���ʿ��� �α� ���ϵ��� ����

-- ���DB ���� Ȯ��
EXEC SP_HELPDB					-- ��� ���� ���� Ȯ�� SP
SELECT DATABASEPROPERTYEX('MILES', 'RECOVERY')	-- �ش� ��� ���� �� Ȯ��
ALTER DATABASE [MILES] SET RECOVERY SIMPLE		-- �ش� ��� ���� �� ���� (SIMPLE / FULL)
EXEC SP_HELPINDEX [���̺��]				-- �ش� ���̺� �ε��� ���� Ȯ��
SELECT @@CONNECTIONS				-- Ŀ�ؼ� �� Ȯ��?
EXEC SP_WHO						-- Ŀ�ؼ� ���� Ȯ��
DBCC LOGINFO						-- ��� �α� ũ�� �� ���� Ȯ��
EXEC SP_HELPCONSTRAINT [���̺��]			--�ش� ���̺��� ���� ���� Ȯ��, �Ǵ� SP_HELP �� ����

-- ���DB �� ���� ������ ���� �����ϴ� ����
IF EXISTS (
	SELECT name
	FROM master.dbo.sysdatabases 
	WHERE name = 'MILES'
	)
	BEGIN
	ALTER DATABASE [MILES] SET  SINGLE_USER WITH ROLLBACK IMMEDIATE
	DROP DATABASE [MILES]
	END
GO						-- ��� ����

  --// �α� �߰� �� [�ɼ�]���� �ּ� ũ�� �� �ִ�ġ(MAXSIZE)�� �����Ѵ�. (FILEGROWTH : ����ġ ����)
ALTER DATABASE [DB_NAME]
ADD LOG FILE(
	NAME     = '[FILENAME]',
	FILENAME   = '[PATH]\[FILENAME]',
	SIZE      = 10MB,
	MAXSIZE   = 20MB,
	FILEGROWTH = 10%   
	)

ALTER DATABASE Database_Name
MODIFY FILE (
NAME = Database_Name_LOG,
SIZE = 2 MB )
GO



-- ���DB ���̺� �÷� ��� ����
SELECT	TABLE_NAME, 
	COLUMN_NAME,columnproperty(OBJECT_ID(TABLE_NAME),
	COLUMN_NAME, 'IsIdentity') as IsIdentity,
	ORDINAL_POSITION, IS_NULLABLE, DATA_TYPE, 
	CHARACTER_OCTET_LENGTH, NUMERIC_PRECISION, COLLATION_NAME, 
	CHARACTER_SET_NAME 
FROM 
INFORMATION_SCHEMA.COLUMNS 
-- WHERE TABLE_NAME = '���̺��'
ORDER BY TABLE_NAME, ORDINAL_POSITION, COLUMN_NAME

-- ����Լ� ����
ROUND(����, ���°�ڸ������������, �����̸�ݿø�/����������)
SUBSTRING('��Ʈ��', ó���ε���, �����ڸ�����) ��: SUBSTRING('12345', 2, 2) = '23'

--������������ ���ɸ鿡���� �ý��� ����͸� �� �� ����

-- ����������Ϸ� ����
DBCC DROPCLEANBUFFERS				-- �������Ϸ� ���� �෹�̼� üũ �� ĳ�� ����

-- ���SET OPTION ����
SET NOCOUNT ON					-- ��������� ������ ī��Ʈ�� ��
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED	-- ������ �б� ���� �ɼ�
ALTER DATABASE [MILES] SET  SINGLE_USER WITH ROLLBACK IMMEDIATE	-- DB ��� ����
ALTER DATABASE [MILES] SET  MULTI_USER				-- �⺻ ���

-- ���TABLE COLUMN �߰� ���� ����
ALTER TABLE [���̺��] ADD [�߰����÷���] [�Ӽ�]
-- �߰��� ���� NULLABLE�̳� DEFAULT ���� �������ִ� �͸� ������
ALTER TABLE [���̺��] ALTER COLUMN [�÷Ÿ�] [������ �Ӽ�]
EXEC SP_RENAME '���̺��.[�������÷���]', '�������̸�', 'COLUMN'
ALTER TABLE [���̺��] DROP COLUMN [�������÷���]
-- EX
ALTER TABLE [INFORMATION_DEMAND] ADD [MEM_CD] INT DEFAULT (0)
ALTER TABLE [INFORMATION_DEMAND] ADD [EVENT_NUMBER] INT

-- ��� ���� ���� ���� �� �߰� :: ������ �˾ƺ���
-- �ش� ���̺��� �������� Ȯ��
SP_HELPCONSTRAINT [���̺��]
-- MEM_WEIGHT��� MEM_SPECIALTY3�� �־����Ƿ� ������� ���� �� �߰�
ALTER TABLE MEM_INF DROP CONSTRAINT DF_MEM_INF_MEM_WEIGHT
ALTER TABLE [dbo].[MEM_INF] ADD  CONSTRAINT [DF_MEM_INF_MEM_SPECIALITY3]  DEFAULT ('$NULL') FOR [MEM_SPECIALITY3]


-- ���INDEX �߰�	:: INCLUDE OPTION�� 2000���� ���� �ȵ�,
--		:: INDEX WIZARD RECOMMEND INDEX -> MILES_LOG �� ���ص���
CREATE NONCLUSTERED INDEX IX01_MEM_INF
	ON [dbo].[MEM_INF] ([MEM_SQUAD])
	INCLUDE ([MEM_CD])

CREATE NONCLUSTERED INDEX IX02_MEM_INF
	ON [dbo].[MEM_INF] ([COMPANY_INF_CD])
	INCLUDE ([MEM_CD],[MEM_SRL_NUM],[MEM_CLASS],[MEM_NAME])

CREATE NONCLUSTERED INDEX [IX03_MEM_INF]
	ON [dbo].[MEM_INF] ([ANNU_SCHEDUL_CD])


ALTER TABLE URC
	ADD CONSTRAINT PK_URC PRIMARY KEY CLUSTERED
	(
	ANNU_SCHEDUL_CD,
	DATE_SCHEDUL_CD,
	COMPANY_INF_CD,
	OPER_PLAN_CD,
	MEM_CD
	)

SELECT * FROM MILES_LOG

--------------------------------------------------------------------
-- ���DB�α� ���
-- 2003����

SELECT * FROM ::fn_dblog(default, default)	-- LOG ���� Ȯ��
DBCC SQLPERF(LOGSPACE)		-- ��� DB �̸�, �α׻�����, ����, ���� Ȯ�� 
BACKUP LOG [MILES] WITH NO_LOG	-- �α� ���� ����� (�ɼ� NO_LOG)
SP_HELPDB [MILES]			-- ��� ���� �̸� Ȯ��
DBCC SHRINKFILE([DT_1229_log], 5, TRUNCATEONLY)	-- 5�ް����� ���
DBCC SHRINKFILE('MILES_log', 1)			-- ��������

-- 2008����
http://h9911120.blog.me/50140277008
��ó : http://memocube.blogspot.com/2011/06/mssql2008-mssql2008.html

TRUNCATE_ONLY �ɼ��� 2008 �������� ������ �ʽ��ϴ�
�α׸� ������������ ����� ���� ����� ����϶�� �ǹ����� �𸣰ڽ��ϴ�
��ư Ʈ����� �α��� �ùٸ�(?) óġ ����� �ΰ��� �ΰ� ���׿�

1. �����ͺ��̽� �������� simple
ALTER DATABASE [DB��] SET RECOVERY SIMPLE
GO

DBCC SHRINKFILE([DB��], 1)
GO

ALTER DATABASE [DB��] SET RECOVERY FULL
GO

2. Ʈ����� �α׸� ����ϰ� �α����� ũ�⸦ ���δ�.
Backup log MILES to disk='c:\log_backup01.trn'
GO

DBCC SHRINKFILE('MILES_log', 2)
GO

�̷� ������ ���ĵ� ���پ� ��� ��� �� �� �� �ݺ��ϸ� �˴ϴ�

��� 2008�α���� ����2
------------------------------------------------------------------------------
-- Otto R. Radke - http://ottoradke.com
-- Info: T-SQL script to shrink a database's transaction log. Just set the
-- database name below and run the script and it will shrink the
-- transaction log.
------------------------------------------------------------------------------
------------------------------------------------------------------------------
-- Update the line below with the name of the database who's transaction
-- log you want to shrink.
------------------------------------------------------------------------------
USE YourDatabaseName
------------------------------------------------------------------------------
-- Don't change anything below this line.
------------------------------------------------------------------------------
GO
-- Declare variables
DECLARE @SqlStatement as nvarchar(max)
DECLARE @LogFileLogicalName as sysname
-- Alter the database to simple recovery
SET @SqlStatement = 'ALTER DATABASE ' + DB_NAME() + ' SET RECOVERY SIMPLE'
EXEC ( @SqlStatement )
-- Make sure it has been altered
SELECT [name], [recovery_model_desc] FROM sys.databases WHERE [name] = DB_NAME()
-- Set the log file name variable
SELECT @LogFileLogicalName = [Name] FROM sys.database_files WHERE type = 1
-- Shrink the logfile
DBCC Shrinkfile(@LogFileLogicalName, 1)
-- Alter the database back to FULL
SET @SqlStatement = 'ALTER DATABASE ' + DB_NAME() + ' SET RECOVERY FULL'
EXEC ( @SqlStatement )
-- Make sure it has been changed back to full
SET @SqlStatement = 'SELECT [name], [recovery_model_desc] FROM ' + DB_NAME() + '.sys.databases WHERE [name] = ''' + DB_NAME() + ''''
EXEC ( @SqlStatement )
------------------------------------------------------------------------------
------------------------------------------------------------------------------
------------------------------------------------------------------------------

-- ���ݺ��� ����
DECLARE @N INT
SET @N = 0
WHILE @N < 10000
	BEGIN
		EXEC HERDIN_INC_100
		SET @N = @N + 1
	END
 
USE [MILES]
	SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
SELECT COUNT(1) FROM HERDIN

SELECT COUNT(*) FROM HERDIN

EXEC SP_WHO



-- ��ᰢ�� �Լ�����
SELECT CAST(22234.234 AS DECIMAL(7,2))