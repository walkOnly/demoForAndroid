
@echo off

set APK_DEBUG_PATH=".\app\build\outputs\apk\app-debug.apk"
set APK_RELEASE_PATH=".\app\build\outputs\apk\app-release.apk"

if exist %APK_DEBUG_PATH% (del %APK_DEBUG_PATH%)
if exist %APK_RELEASE_PATH% (del %APK_RELEASE_PATH%)

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
