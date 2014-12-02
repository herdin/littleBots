-- ■■DBA의 정기적/일상적 업무
-- 1. 백업과 로그의 관리
-- 2. 인덱스 리빌드
-- 3. DBCC (DB 오류점검)
-- 4. 새로운 사용자 등록, 권한 부여
-- 5. UPDATE STATISTICS - 분포 페이지의 갱신
-- 6. 불필요한 로그 파일들의 삭제

-- ■■DB 정보 확인
EXEC SP_HELPDB					-- 디비 관련 정보 확인 SP
SELECT DATABASEPROPERTYEX('MILES', 'RECOVERY')	-- 해당 디비 복구 모델 확인
ALTER DATABASE [MILES] SET RECOVERY SIMPLE		-- 해당 디비 복구 모델 변경 (SIMPLE / FULL)
EXEC SP_HELPINDEX [테이블명]				-- 해당 테이블 인덱스 정보 확인
SELECT @@CONNECTIONS				-- 커넥션 수 확인?
EXEC SP_WHO						-- 커넥션 정보 확인
DBCC LOGINFO						-- 모든 로그 크기 및 정보 확인
EXEC SP_HELPCONSTRAINT [테이블명]			--해당 테이블의 제약 정보 확인, 또는 SP_HELP 도 같음

-- ■■DB 의 존재 유무에 따라 삭제하는 구문
IF EXISTS (
	SELECT name
	FROM master.dbo.sysdatabases 
	WHERE name = 'MILES'
	)
	BEGIN
	ALTER DATABASE [MILES] SET  SINGLE_USER WITH ROLLBACK IMMEDIATE
	DROP DATABASE [MILES]
	END
GO						-- 디비 삭제

  --// 로그 추가 및 [옵션]실제 최소 크기 및 최대치(MAXSIZE)를 변경한다. (FILEGROWTH : 증가치 설정)
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



-- ■■DB 테이블 컬럼 모두 보기
SELECT	TABLE_NAME, 
	COLUMN_NAME,columnproperty(OBJECT_ID(TABLE_NAME),
	COLUMN_NAME, 'IsIdentity') as IsIdentity,
	ORDINAL_POSITION, IS_NULLABLE, DATA_TYPE, 
	CHARACTER_OCTET_LENGTH, NUMERIC_PRECISION, COLLATION_NAME, 
	CHARACTER_SET_NAME 
FROM 
INFORMATION_SCHEMA.COLUMNS 
-- WHERE TABLE_NAME = '테이블명'
ORDER BY TABLE_NAME, ORDINAL_POSITION, COLUMN_NAME

-- ■■함수 관련
ROUND(숫자, 몇번째자리까지남길건지, 음수이면반올림/나머지버림)
SUBSTRING('스트링', 처음인덱스, 몇자자를건지) 예: SUBSTRING('12345', 2, 2) = '23'

--관리도구에서 성능면에가면 시스템 모니터를 할 수 있음

-- ■■프로파일러 관련
DBCC DROPCLEANBUFFERS				-- 프로파일러 쿼리 듀레이션 체크 중 캐시 삭제

-- ■■SET OPTION 관련
SET NOCOUNT ON					-- 쿼리실행시 나오는 카운트수 끔
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED	-- 락없이 읽기 가능 옵션
ALTER DATABASE [MILES] SET  SINGLE_USER WITH ROLLBACK IMMEDIATE	-- DB 모드 변경
ALTER DATABASE [MILES] SET  MULTI_USER				-- 기본 모드

-- ■■TABLE COLUMN 추가 수정 삭제
ALTER TABLE [테이블명] ADD [추가할컬럼명] [속성]
-- 추가할 때는 NULLABLE이나 DEFAULT 값이 정해져있는 것만 가능함
ALTER TABLE [테이블명] ALTER COLUMN [컬렴명] [변경할 속성]
EXEC SP_RENAME '테이블명.[수정할컬럼명]', '수정될이름', 'COLUMN'
ALTER TABLE [테이블명] DROP COLUMN [삭제할컬럼명]
-- EX
ALTER TABLE [INFORMATION_DEMAND] ADD [MEM_CD] INT DEFAULT (0)
ALTER TABLE [INFORMATION_DEMAND] ADD [EVENT_NUMBER] INT

-- ■■ 제약 조건 삭제 및 추가 :: 예제로 알아봐용
-- 해당 테이블의 제약조건 확인
SP_HELPCONSTRAINT [테이블명]
-- MEM_WEIGHT대신 MEM_SPECIALTY3을 넣었으므로 제약사항 삭제 및 추가
ALTER TABLE MEM_INF DROP CONSTRAINT DF_MEM_INF_MEM_WEIGHT
ALTER TABLE [dbo].[MEM_INF] ADD  CONSTRAINT [DF_MEM_INF_MEM_SPECIALITY3]  DEFAULT ('$NULL') FOR [MEM_SPECIALITY3]


-- ■■INDEX 추가	:: INCLUDE OPTION은 2000에는 적용 안됨,
--		:: INDEX WIZARD RECOMMEND INDEX -> MILES_LOG 은 안해도됨
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
-- ■■DB로그 축소
-- 2003버전

SELECT * FROM ::fn_dblog(default, default)	-- LOG 내용 확인
DBCC SQLPERF(LOGSPACE)		-- 모든 DB 이름, 로그사이즈, 사용률, 상태 확인 
BACKUP LOG [MILES] WITH NO_LOG	-- 로그 내용 지우기 (옵션 NO_LOG)
SP_HELPDB [MILES]			-- 대상 파일 이름 확인
DBCC SHRINKFILE([DT_1229_log], 5, TRUNCATEONLY)	-- 5메가까지 축소
DBCC SHRINKFILE('MILES_log', 1)			-- 같은내용

-- 2008버전
http://h9911120.blog.me/50140277008
출처 : http://memocube.blogspot.com/2011/06/mssql2008-mssql2008.html

TRUNCATE_ONLY 옵션은 2008 버전부턴 사용되지 않습니다
로그를 비정상적으로 지우기 보다 제대로 사용하라는 의미인지 모르겠습니다
암튼 트랜잭션 로그의 올바른(?) 처치 방법은 두가지 인거 같네요

1. 데이터베이스 복구모델을 simple
ALTER DATABASE [DB명] SET RECOVERY SIMPLE
GO

DBCC SHRINKFILE([DB명], 1)
GO

ALTER DATABASE [DB명] SET RECOVERY FULL
GO

2. 트랜잭션 로그를 백업하고 로그파일 크기를 줄인다.
Backup log MILES to disk='c:\log_backup01.trn'
GO

DBCC SHRINKFILE('MILES_log', 2)
GO

이런 과정을 거쳐도 안줄어 드는 경우 한 번 더 반복하면 됩니다

■■ 2008로그축소 버전2
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

-- ■■반복문 쓰기
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



-- ■■각종 함수관련
SELECT CAST(22234.234 AS DECIMAL(7,2))