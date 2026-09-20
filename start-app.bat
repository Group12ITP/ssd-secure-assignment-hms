@echo off
echo Starting Prescription Management System...
echo.

REM Prefer Java 17 or 21 (Required by Spring Boot 3.x)
if exist "C:\Program Files\Java\jdk-17" (
    set "JAVA_HOME=C:\Program Files\Java\jdk-17"
    set "PATH=C:\Program Files\Java\jdk-17\bin;%PATH%"
) else if exist "%USERPROFILE%\.jdks\ms-21.0.8" (
    set "JAVA_HOME=%USERPROFILE%\.jdks\ms-21.0.8"
    set "PATH=%USERPROFILE%\.jdks\ms-21.0.8\bin;%PATH%"
) else if exist "C:\Program Files\Java\jdk-21" (
    set "JAVA_HOME=C:\Program Files\Java\jdk-21"
    set "PATH=C:\Program Files\Java\jdk-21\bin;%PATH%"
) else if exist "C:\Program Files\Java\jdk-22" (
    set "JAVA_HOME=C:\Program Files\Java\jdk-22"
    set "PATH=C:\Program Files\Java\jdk-22\bin;%PATH%"
)

REM Check if Java is available
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

echo Using Java:
java -version
echo.

REM Run the application
echo Running application with Maven wrapper...
call .\mvnw.cmd spring-boot:run

if %errorlevel% neq 0 (
    echo.
    echo ERROR: Failed to start application
    echo.
    echo Troubleshooting steps:
    echo 1. Make sure Java 17+ is installed (Spring Boot 3 requires Java 17 minimum)
    echo 2. Check database connection settings in application.properties
    echo 3. Verify SQL Server is running
    echo.
    pause
)
