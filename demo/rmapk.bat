
@echo off

set ADB="C:\Android\SDK\platform-tools\adb.exe"
set PACKAGE_NAME="com.hhxc.demo"

%ADB% uninstall %PACKAGE_NAME%
