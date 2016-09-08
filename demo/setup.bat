
@echo off

set ADB="C:\Android\SDK\platform-tools\adb.exe"

set APK_DEBUG_PATH=".\app\build\outputs\apk\app-update-debug.apk"
set APK_RELEASE_PATH=".\app\build\outputs\apk\app-update-release.apk"

if "%1"=="" goto setupDebug
if "%1"=="0" goto setupDebug

if "%1"=="1" goto setupRelease

:setupDebug
@echo 0
%ADB% install -r %APK_DEBUG_PATH%
goto end

:setupRelease
@echo 1
%ADB% install -r %APK_RELEASE_PATH%
goto end

:end
