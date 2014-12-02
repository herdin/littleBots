/*
   dbcc showcontig('FORMATE_INF','PK__FORMATE_INF__39788055')
*/
use MILES
go

    SET NOCOUNT ON;
    DECLARE @objectid int;
    DECLARE @indexid int;
    DECLARE @partitioncount bigint;
    DECLARE @schemaname sysname;
    DECLARE @objectname sysname;
    DECLARE @indexname sysname;
    DECLARE @partitionnum bigint;
    DECLARE @partitions bigint;
    DECLARE @frag float;
    DECLARE @command varchar(8000);
    -- ensure the temporary table does not exist
    IF EXISTS (SELECT name FROM sys.objects WHERE name = 'work_to_do')
        DROP TABLE work_to_do;
    -- conditionally select from the function, converting object and index IDs to names.

    declare @DB_ID int;
    set @DB_ID = db_id();

    SELECT
        aa.[object_id] AS objectid,
        aa.index_id AS indexid,
    --    bb.[name] as strIndexName,
    --    bb.[type_desc] as strIndexType,
        aa.partition_number AS partitionnum,
        aa.avg_fragmentation_in_percent AS frag
    INTO work_to_do
    FROM sys.dm_db_index_physical_stats (@DB_ID, NULL, NULL , NULL, 'LIMITED') aa
    --left join sys.indexes bb On aa.[object_id] = bb.[object_id] and aa.[index_id] = bb.[index_id]
    WHERE index_id > 0
    --AND avg_fragmentation_in_percent > 10.0
    order by objectid, indexid;

    -- Declare the cursor for the list of partitions to be processed.
    DECLARE partitions CURSOR FOR SELECT * FROM work_to_do;

    OPEN partitions;

    FETCH NEXT
       FROM partitions
       INTO @objectid, @indexid, @partitionnum, @frag;

    DECLARE @intCnt INT;
    SET @intCnt = 0

    WHILE @@FETCH_STATUS = 0
    BEGIN;
        SET @intCnt = @intCnt + 1;
        PRINT '--' + convert(varchar(3), @intCnt)

        SELECT @objectname = o.name, @schemaname = s.name
        FROM sys.objects AS o
        JOIN sys.schemas as s ON s.schema_id = o.schema_id
        WHERE o.object_id = @objectid;

        SELECT @indexname = name
        FROM sys.indexes
        WHERE  object_id = @objectid AND index_id = @indexid;

        SELECT @partitioncount = count (*)
        FROM sys.partitions
        WHERE object_id = @objectid AND index_id = @indexid;

            SELECT @command = 'ALTER INDEX [' + @indexname +'] ON [' + @schemaname + '].[' + @objectname + '] REBUILD';
            IF @partitioncount > 1
            begin
                SELECT @command = @command + ' PARTITION=' + CONVERT (varchar(5), @partitionnum);
                set @command = @command + ' with (maxdop=6)'
            end
            else
            begin
                --set @command = @command + ' with (maxdop=6, PAD_INDEX = on, fillfactor=95, '
                set @command = @command + ' with (maxdop=6); '
            end

            set @command = @command + char(10) + 'go' + char(10)
            --EXEC (@command);
            PRINT @command


        PRINT '--FRAGMENTATION : ' + CAST(@frag AS VARCHAR(10)) + '%' + CHAR(10);
        PRINT 'print ''' + convert(varchar(3), @intCnt) + ' : '+ @schemaname + '.' + @indexname + ' ¿Ï·á !!!!''' + char(10) + char(10) + char(10)
        PRINT  char(10) + 'go' + char(10);

        FETCH NEXT FROM partitions INTO @objectid, @indexid, @partitionnum, @frag;
    END;
    -- Close and deallocate the cursor.
    CLOSE partitions;
    DEALLOCATE partitions;

    -- drop the temporary table
    IF EXISTS (SELECT name FROM sys.objects WHERE name = 'work_to_do')
        DROP TABLE work_to_do

