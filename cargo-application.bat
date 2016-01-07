@echo off
cd cargo-common\config-server
start run
echo Config Server�� �����ɶ����� ��ٸ��ϴ�. �Ϸ�Ǹ� �ƹ�Ű�� �Է��ϼ���.
pause > nul



echo Eureka Server�� �����մϴ�.
@echo off
cd ..\eureka-server
start run

echo Zuul Server�� �����մϴ�.
@echo off
cd ..\zuul-server
start run

echo Boot Admin�� �����մϴ�.
@echo off
cd ..\spring-boot-admin
start run

echo ���������� �����մϴ�.
@echo off
cd ..\..\auth-server
start run




echo Microservice-1] cargo-tracker Service�� �����մϴ�.
@echo off
cd ..\cargo-tracker
start run

echo Microservice-2] path-finder Service�� �����մϴ�.
@echo off
cd ..\cargo-pathfinder
start run

echo Microservice-3] booking Service�� �����մϴ�.
@echo off
cd ..\cargo-booking
start run

echo Microservice-4] user Service�� �����մϴ�.
@echo off
cd ..\cargo-user
start run

echo Microservice-5] aggregation Service�� �����մϴ�.
@echo off
cd ..\cargo-aggregation
start run

echo Microservice-6] ui-web Service�� �����մϴ�.
@echo off
cd ..\cargo-ui\cargo-ui-web
start run

echo Microservice-7] ui-device Service�� �����մϴ�.
@echo off
cd ..\cargo-ui-device
start run



echo ��� ���񽺰� ���� �� �Դϴ�. ���� â�� �������� �ƹ�Ű�� �Է��ϼ���.
pause > nul