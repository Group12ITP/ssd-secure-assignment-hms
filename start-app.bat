@echo off
echo Starting Prescription Management System...
echo.

REM Check if Java is available
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 11 or higher
    pause
    exit /b 1
)

REM Try to run the application
echo Running application with Maven wrapper...
.\mvnw.cmd spring-boot:run

if %errorlevel% neq 0 (
    echo.
    echo ERROR: Failed to start application
    echo.
    echo Troubleshooting steps:
    echo 1. Make sure Java 11+ is installed
    echo 2. Check database connection settings
    echo 3. Verify all dependencies are downloaded
    echo.
    pause
)


