# Prescription Management System - Troubleshooting Guide

## 🚨 **500 Internal Server Error - Common Solutions**

### **1. Database Connection Issues**
- **Check**: Database server is running
- **Check**: Connection string in `application.properties`
- **Check**: Database tables exist (run `create-tables.sql`)

### **2. Missing Dependencies**
- **Check**: All required services are properly injected
- **Check**: Repository interfaces are correctly implemented

### **3. Data Validation Issues**
- **Check**: Required fields are not null
- **Check**: Medicine data is properly loaded

## 🔧 **Quick Fixes**

### **Test Application Status**
```
http://localhost:8080/test
```

### **Test Database Connection**
```
http://localhost:8080/test-db
```

### **Test Medicine Data**
```
http://localhost:8080/medicine/test
```

## 📋 **Step-by-Step Debugging**

### **1. Check Application Logs**
Look for specific error messages in the console output.

### **2. Verify Database Tables**
Ensure these tables exist:
- `medicines`
- `prescriptions` 
- `prescription_medicines`
- `doctors`
- `patients`
- `pharmacists`
- `doctor_appointments`

### **3. Test Individual Components**
- Test medicine loading: `/medicine/test`
- Test prescription creation: `/prescription/create/1`
- Test pharmacist dashboard: `/pharmacist/dashboard?username=test`

## 🛠️ **Manual Database Setup**

If tables don't exist, run this SQL:

```sql
-- Run the create-tables.sql script
-- Then run the DatabaseInitializer to populate sample data
```

## 📞 **Common Error Messages**

### **"Medicine not found"**
- **Solution**: Ensure medicine data is loaded
- **Check**: `/medicine/test` endpoint

### **"Prescription not found"**
- **Solution**: Check prescription ID exists
- **Check**: Database has prescription records

### **"Database connection failed"**
- **Solution**: Check database server status
- **Check**: Connection properties

## 🎯 **Quick Start Commands**

1. **Start Application**: `mvn spring-boot:run`
2. **Test Homepage**: `http://localhost:8080/`
3. **Test System**: `http://localhost:8080/test`
4. **Test Medicine**: `http://localhost:8080/medicine/test`

## 📝 **System Requirements**

- Java 17+
- Spring Boot 3.x
- SQL Server Database
- Maven 3.6+

## 🔍 **Debug Mode**

Add to `application.properties`:
```properties
logging.level.com.example.test=DEBUG
spring.jpa.show-sql=true
```


