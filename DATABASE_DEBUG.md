# Database Insertion Issue - Troubleshooting Guide

## 🚨 **Problem: Prescription details not inserting to database**

### **Root Causes & Solutions:**

## 🔧 **Step-by-Step Debugging:**

### **1. Test Database Connection**
```
http://localhost:8080/test-db
```
**Expected**: "Database connection test - OK. Prescription count: X"
**If Error**: Database server not running or connection issues

### **2. Test Prescription Save**
```
http://localhost:8080/test-prescription-save
```
**Expected**: "Test prescription saved successfully! ID: X"
**If Error**: Table creation or permission issues

### **3. Check Console Logs**
Look for these debug messages when creating prescriptions:
```
=== PRESCRIPTION CREATION DEBUG ===
=== PRESCRIPTION SERVICE DEBUG ===
=== PRESCRIPTION MEDICINE SERVICE DEBUG ===
```

## 🛠️ **Common Issues & Fixes:**

### **Issue 1: Database Tables Not Created**
**Symptoms**: 
- "Table 'prescriptions' doesn't exist"
- "Table 'prescription_medicines' doesn't exist"

**Solution**:
1. Check `application.properties`:
   ```properties
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.generate-ddl=true
   ```

2. Run SQL script manually:
   ```sql
   -- Run create-tables.sql in SQL Server Management Studio
   ```

### **Issue 2: Database Connection Failed**
**Symptoms**:
- "Connection refused"
- "Login failed"

**Solution**:
1. Check SQL Server is running
2. Verify connection settings in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=DDD
   spring.datasource.username=app_user
   spring.datasource.password=123
   ```

### **Issue 3: Medicine Data Missing**
**Symptoms**:
- "Medicine not found with ID: X"
- Empty medicine list

**Solution**:
1. Run medicine data initialization:
   ```
   http://localhost:8080/medicine/test
   ```

2. Check if `MedicineDataInitializer` is running

### **Issue 4: Form Data Not Submitted**
**Symptoms**:
- "No medicines provided for prescription"
- Empty medicine IDs array

**Solution**:
1. Check JavaScript in `create-prescription.html`
2. Verify form submission includes medicine data
3. Check browser console for JavaScript errors

## 🎯 **Debugging Steps:**

### **Step 1: Enable SQL Logging**
Add to `application.properties`:
```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### **Step 2: Check Database Tables**
```sql
-- Check if tables exist
SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES 
WHERE TABLE_NAME IN ('prescriptions', 'prescription_medicines', 'medicines')

-- Check prescription data
SELECT * FROM prescriptions

-- Check prescription medicines data
SELECT * FROM prescription_medicines
```

### **Step 3: Test Individual Components**
1. **Test Prescription Save**: `http://localhost:8080/test-prescription-save`
2. **Test Medicine Data**: `http://localhost:8080/medicine/test`
3. **Test Sample Appointments**: `http://localhost:8080/create-sample-appointments`

## 🔍 **Advanced Debugging:**

### **Check Hibernate DDL**
Look for these messages in console:
```
Hibernate: create table prescriptions ...
Hibernate: create table prescription_medicines ...
Hibernate: insert into prescriptions ...
```

### **Check Transaction Management**
Add `@Transactional` to service methods if missing:
```java
@Transactional
public Prescription createPrescription(Prescription prescription) {
    // ...
}
```

### **Check Entity Annotations**
Verify `@Entity` and `@Table` annotations are correct:
```java
@Entity
@Table(name = "prescriptions")
public class Prescription {
    // ...
}
```

## 📋 **Quick Fix Checklist:**

- [ ] Database server running
- [ ] Connection settings correct
- [ ] Tables created (`spring.jpa.hibernate.ddl-auto=update`)
- [ ] Medicine data loaded
- [ ] Sample appointments created
- [ ] Form data being submitted
- [ ] No JavaScript errors
- [ ] Console logs showing debug messages

## 🚀 **Test Workflow:**

1. **Start Application**: `.\mvnw.cmd spring-boot:run`
2. **Test Database**: `http://localhost:8080/test-db`
3. **Test Prescription Save**: `http://localhost:8080/test-prescription-save`
4. **Create Sample Data**: `http://localhost:8080/create-sample-appointments`
5. **Test Prescription Creation**: `http://localhost:8080/prescription/create/1`
6. **Check Console Logs**: Look for debug messages
7. **Verify Database**: Check tables have data

## 📞 **If Still Having Issues:**

1. **Check Console Output**: Look for error messages
2. **Check Database Logs**: SQL Server error logs
3. **Test with Simple Data**: Use test endpoints first
4. **Verify Permissions**: Database user has INSERT permissions
5. **Check Network**: Database server accessible

The debug logging will show exactly where the insertion is failing! 🔍


