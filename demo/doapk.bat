
@echo off

if "%1"=="" goto assembleDebug
if "%1"=="0" goto assembleDebug

if "%1"=="1" goto assembleRelease

:assembleDebug
@echo 0
call gradlew assembleDebug
goto end

:assembleRelease
@echo 1
call gradlew assembleRelease
goto end

:end
