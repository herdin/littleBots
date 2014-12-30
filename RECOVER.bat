@echo off

echo backup file move
MOVE D:\BDDATA\BACKUP\*.* D:\BDDATA\SYNC\
echo operinfo file moved
MOVE D:\BDDATA\OPERINFO\*.* D:\BDDATA\SYNC\

echo -
pause