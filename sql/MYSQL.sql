# F9			  : EXECUTE ALL
# SHIFT + F9 : EXECUTE SELECTION

#DUAL
SELECT 1 FROM DUAL;

#SYSDATE FORMATER
SELECT DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s') FROM DUAL;

#MAKE BACK UP TABLE
CREATE TABLE BACKUP_SOURCE_TABLE SELECT * FROM BACKUP_TARGET_TABLE;

#SEQ IMPLEMENT #PRE MADE SEQ_TEST TABLE
DESC SEQ_TEST;
INSERT INTO SEQ_TEST VALUES();
SELECT MAX(SEQ_NUM) FROM SEQ_TEST;

SELECT * FROM SEQ_TEST;
DELETE FROM SEQ_TEST;

ALTER TABLE SEQ_TEST AUTO_INCREMENT = 1;


