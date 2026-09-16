# Java Version Issue - Quick Fix Guide

## 🚨 **Problem: "release version 17 not supported"**

### **Root Cause:**
Your system doesn't have Java 17 installed, but the project was configured for Java 17.

### **✅ Solution Applied:**
I've updated the project to use Java 11 instead, which is more commonly available.

## 🔧 **Quick Fixes:**

### **Option 1: Use Updated Project (Recommended)**
The project is now configured for Java 11. Try running:
```bash
.\mvnw.cmd spring-boot:run
```

### **Option 2: Install Java 17**
If you prefer Java 17:
1. Download Java 17 from Oracle or OpenJDK
2. Install and set JAVA_HOME
3. Revert pom.xml to Java 17

### **Option 3: Use Batch File**
I've created a `start-app.bat` file:
```bash
start-app.bat
```

## 🎯 **Testing Steps:**

### **1. Check Java Version**
```bash
java -version
```
Should show Java 11 or higher.

### **2. Run Application**
```bash
.\mvnw.cmd spring-boot:run
```

### **3. Test System**
Once running, visit:
- `http://localhost:8080/test-page`
- `http://localhost:8080/create-sample-appointments`
- `http://localhost:8080/prescription/create/1`

## 📋 **Common Java Issues:**

### **"Java not found"**
- Install Java 11+ from Oracle or OpenJDK
- Set JAVA_HOME environment variable
- Add Java to PATH

### **"Maven not found"**
- Use the Maven wrapper: `.\mvnw.cmd`
- Or install Maven separately

### **"Database connection failed"**
- Check SQL Server is running
- Verify connection settings in `application.properties`

## 🛠️ **Manual Java Installation:**

### **Windows:**
1. Download Java 11 from https://adoptium.net/
2. Install with default settings
3. Set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-11.x.x
4. Add %JAVA_HOME%\bin to PATH

### **Alternative: Use IDE**
- Open project in IntelliJ IDEA or Eclipse
- Let IDE handle Java version
- Run from IDE instead of command line

## 🎯 **Quick Test Commands:**

```bash
# Check Java
java -version

# Check Maven wrapper
.\mvnw.cmd --version

# Run application
.\mvnw.cmd spring-boot:run

# Test endpoints (after app starts)
curl http://localhost:8080/test
```

## 📞 **If Still Having Issues:**

1. **Check Java Installation:**
   ```bash
   where java
   echo %JAVA_HOME%
   ```

2. **Try Different Java Version:**
   - Update pom.xml to Java 8: `<java.version>8</java.version>`
   - Or Java 11: `<java.version>11</java.version>`

3. **Use IDE:**
   - Open project in IntelliJ IDEA
   - Right-click TestApplication.java
   - Select "Run TestApplication"

The application should now work with Java 11! 🚀


