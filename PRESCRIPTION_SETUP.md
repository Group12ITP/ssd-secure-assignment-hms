# Prescription Management System Setup

## 🏥 Overview
This system provides a complete prescription management solution for healthcare applications, allowing doctors to issue prescriptions, patients to view them, and pharmacists to manage orders.

## 🚀 Quick Start

### 1. Database Setup
The system will automatically create the required tables when you start the application. The following tables will be created:
- `medicines` - Medicine database with 15+ sample medicines
- `prescriptions` - Prescription records
- `doctor_appointment` - Updated with patientId field

### 2. Sample Data
The system includes 15+ sample medicines across different categories:
- **Cardiovascular**: Amlodipine, Metoprolol, Lisinopril
- **Respiratory**: Albuterol, Prednisone
- **Gastrointestinal**: Omeprazole, Metoclopramide
- **Neurological**: Gabapentin, Diazepam
- **Endocrine**: Metformin, Levothyroxine
- **Pain Management**: Ibuprofen, Tramadol
- **Antibiotic**: Amoxicillin, Azithromycin

### 3. Testing the System

#### Test Medicine Data
Visit: `http://localhost:8080/medicine/test`
This will show how many medicines are loaded in the database.

#### View Medicine List
Visit: `http://localhost:8080/medicine/list`
This will display all medicines in a table format.

#### Test Categories
Visit: `http://localhost:8080/medicine/categories`
This will return all available medicine categories.

## 🔧 Key Features

### Doctor Dashboard
- View today's appointments
- Complete appointments after patient check
- Create prescriptions with medicine selection
- Professional prescription creation form

### Patient Dashboard
- View all prescriptions
- Print prescriptions
- Professional card-based layout

### Pharmacist Dashboard
- View scheduled orders (prescriptions)
- Process orders (change status)
- Mark orders as complete
- Handle urgent orders

## 📋 API Endpoints

### Medicine Management
- `GET /medicine/list` - View all medicines
- `GET /medicine/categories` - Get all categories
- `GET /medicine/by-category/{category}` - Get medicines by category
- `GET /medicine/test` - Test medicine data

### Prescription Management
- `GET /prescription/create/{appointmentId}` - Create prescription form
- `POST /prescription/create` - Create new prescription
- `GET /prescription/patient/{patientId}` - Get patient prescriptions
- `GET /prescription/pharmacist/orders` - Get pharmacist orders
- `POST /prescription/update-status/{prescriptionId}` - Update prescription status

### Doctor Management
- `POST /doctor/appointment/{appointmentId}/complete` - Complete appointment
- `GET /doctor/appointment/{appointmentId}/prescription` - Create prescription for appointment

## 🎨 UI Features

### Professional Styling
- Modern Bootstrap 5 design
- Responsive layout for all devices
- Professional color scheme
- Interactive elements with hover effects
- Print-friendly styles

### User Experience
- Intuitive navigation
- Real-time medicine selection
- Status tracking
- Professional forms
- Mobile-responsive design

## 🗄️ Database Schema

### Medicines Table
```sql
CREATE TABLE medicines (
    medicine_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    medicine_name NVARCHAR(255) NOT NULL,
    generic_name NVARCHAR(255) NOT NULL,
    category NVARCHAR(100) NOT NULL,
    dosage_form NVARCHAR(100) NOT NULL,
    strength NVARCHAR(50) NOT NULL,
    description NVARCHAR(1000),
    indications NVARCHAR(1000),
    contraindications NVARCHAR(1000),
    side_effects NVARCHAR(1000),
    dosage_instructions NVARCHAR(1000),
    storage_conditions NVARCHAR(255),
    manufacturer NVARCHAR(255),
    batch_number NVARCHAR(100),
    expiry_date NVARCHAR(50),
    unit_price DECIMAL(10,2),
    stock_quantity INT,
    is_prescription_required BIT DEFAULT 1,
    is_active BIT DEFAULT 1
);
```

### Prescriptions Table
```sql
CREATE TABLE prescriptions (
    prescription_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    appointment_id BIGINT NOT NULL,
    diagnosis NVARCHAR(1000) NOT NULL,
    symptoms NVARCHAR(1000),
    notes NVARCHAR(1000),
    follow_up_date DATETIME2,
    prescription_date DATETIME2 NOT NULL DEFAULT GETDATE(),
    status NVARCHAR(50) NOT NULL DEFAULT 'Active',
    total_amount DECIMAL(10,2),
    is_urgent BIT DEFAULT 0,
    medicine_details NVARCHAR(2000)
);
```

## 🔄 Workflow

1. **Doctor Workflow**:
   - Login to doctor dashboard
   - View today's appointments
   - Complete appointment after patient check
   - Create prescription with medicine selection
   - Prescription is automatically sent to pharmacist

2. **Patient Workflow**:
   - Login to patient dashboard
   - View prescriptions section
   - See detailed medicine information
   - Print prescriptions for pharmacy visits

3. **Pharmacist Workflow**:
   - Login to pharmacist dashboard
   - View scheduled orders
   - Process orders (change status)
   - Mark orders as complete

## 🚨 Troubleshooting

### Database Connection Issues
- Ensure SQL Server is running
- Check connection string in `application.properties`
- Verify database credentials

### Medicine Data Not Loading
- Check if `DatabaseInitializer` is running
- Visit `/medicine/test` to verify data
- Check application logs for errors

### Compilation Errors
- Ensure all dependencies are installed
- Run `mvn clean compile` to rebuild
- Check for missing imports

## 📞 Support

If you encounter any issues:
1. Check the application logs
2. Verify database connectivity
3. Test individual endpoints
4. Ensure all required tables are created

The system is now ready for use with a complete prescription management workflow!


