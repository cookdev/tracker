@echo off
cd cargo-common\ConfigServer
start run
echo Config Server가 구동될때까지 기다립니다. 완료되면 아무키나 입력하세요.
pause > nul



echo Eureka Server를 구동합니다.
@echo off
cd ..\EurekaServer
start run

echo Zuul Server를 구동합니다.
@echo off
cd ..\ZuulServer
start run

echo Boot Admin을 구동합니다.
@echo off
cd ..\spring-boot-admin
start run

echo 인증서버를 구동합니다.
@echo off
cd ..\..\auth-server
start run




echo Microservice-1] cargo-tracker Service를 구동합니다.
@echo off
cd ..\cargo-tracker
start run

echo Microservice-2] path-finder Service를 구동합니다.
@echo off
cd ..\cargo-tracker-pathfinder
start run

echo Microservice-3] booking Service를 구동합니다.
@echo off
cd ..\cargo-booking
start run

echo Microservice-4] user Service를 구동합니다.
@echo off
cd ..\cargo-user
start run

echo Microservice-5] aggregation Service를 구동합니다.
@echo off
cd ..\cargo-aggregation
start run

echo Microservice-6] ui-web Service를 구동합니다.
@echo off
cd ..\cargo-ui\cargo-ui-web
start run

echo Microservice-7] ui-device Service를 구동합니다.
@echo off
cd ..\cargo-ui-device
start run



echo 모든 서비스가 구동 중 입니다. 현재 창을 닫으려면 아무키나 입력하세요.
pause > nul