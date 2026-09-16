-- Create Doctors Table
CREATE TABLE doctors (
    doctor_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    full_name NVARCHAR(255) NOT NULL,
    nic NVARCHAR(20) NOT NULL UNIQUE,
    gender NVARCHAR(10) NOT NULL,
    date_of_birth NVARCHAR(20) NOT NULL,
    contact_number NVARCHAR(20) NOT NULL,
    email NVARCHAR(255) NOT NULL UNIQUE,
    address NVARCHAR(500),
    license_number NVARCHAR(50) NOT NULL,
    specialization NVARCHAR(100) NOT NULL,
    qualifications NVARCHAR(500),
    years_of_experience INT,
    department NVARCHAR(100),
    working_days NVARCHAR(50),
    working_hours NVARCHAR(50),
    username NVARCHAR(50) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    security_question NVARCHAR(500),
    security_answer NVARCHAR(255)
);

-- Create Patients Table
CREATE TABLE patients (
    patient_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    full_name NVARCHAR(255) NOT NULL,
    nic NVARCHAR(20) NOT NULL UNIQUE,
    gender NVARCHAR(10) NOT NULL,
    date_of_birth NVARCHAR(20) NOT NULL,
    contact_number NVARCHAR(20) NOT NULL,
    email NVARCHAR(255) NOT NULL UNIQUE,
    address NVARCHAR(500),
    blood_group NVARCHAR(10),
    emergency_contact NVARCHAR(20),
    medical_history NVARCHAR(1000),
    allergies NVARCHAR(500),
    current_medications NVARCHAR(1000),
    username NVARCHAR(50) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    security_question NVARCHAR(500),
    security_answer NVARCHAR(255)
);

-- Create Doctor Appointments Table
CREATE TABLE doctor_appointment (
    appointment_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    appointment_date DATETIME2 NOT NULL,
    appointment_time NVARCHAR(20) NOT NULL,
    status NVARCHAR(50) NOT NULL DEFAULT 'Scheduled',
    notes NVARCHAR(1000),
    created_date DATETIME2 NOT NULL DEFAULT GETDATE()
);

-- Create Medicine Table
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
    unit_price FLOAT,
    stock_quantity INT,
    is_prescription_required BIT DEFAULT 1,
    is_active BIT DEFAULT 1
);

-- Create Prescription Table
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
    total_amount FLOAT,
    is_urgent BIT DEFAULT 0,
    medicine_details NVARCHAR(2000)
);

-- Create Prescription Medicines Table
CREATE TABLE prescription_medicines (
    prescription_medicine_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    prescription_id BIGINT NOT NULL,
    medicine_id BIGINT NOT NULL,
    medicine_name NVARCHAR(255) NOT NULL,
    generic_name NVARCHAR(255) NOT NULL,
    dosage_form NVARCHAR(100) NOT NULL,
    strength NVARCHAR(50) NOT NULL,
    dosage NVARCHAR(100) NOT NULL,
    frequency NVARCHAR(100) NOT NULL,
    duration NVARCHAR(100) NOT NULL,
    instructions NVARCHAR(500),
    quantity INT,
    unit_price FLOAT,
    total_price FLOAT
);

-- Add foreign key constraints
ALTER TABLE prescriptions ADD CONSTRAINT FK_prescriptions_doctor 
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id);
ALTER TABLE prescriptions ADD CONSTRAINT FK_prescriptions_patient 
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id);
ALTER TABLE prescriptions ADD CONSTRAINT FK_prescriptions_appointment 
    FOREIGN KEY (appointment_id) REFERENCES doctor_appointment(appointment_id);

-- Add foreign key constraints for doctor_appointment table
ALTER TABLE doctor_appointment ADD CONSTRAINT FK_appointment_doctor 
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id);
ALTER TABLE doctor_appointment ADD CONSTRAINT FK_appointment_patient 
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id);

-- Create indexes for better performance
CREATE INDEX IX_medicines_category ON medicines(category);
CREATE INDEX IX_medicines_active ON medicines(is_active);
CREATE INDEX IX_medicines_prescription_required ON medicines(is_prescription_required);

CREATE INDEX IX_prescriptions_doctor ON prescriptions(doctor_id);
CREATE INDEX IX_prescriptions_patient ON prescriptions(patient_id);
CREATE INDEX IX_prescriptions_status ON prescriptions(status);
CREATE INDEX IX_prescriptions_date ON prescriptions(prescription_date);

-- Insert sample doctor data
INSERT INTO doctors (full_name, nic, gender, date_of_birth, contact_number, email, address, license_number, specialization, qualifications, years_of_experience, department, working_days, working_hours, username, password, security_question, security_answer) VALUES
('Dr. Sarah Johnson', '1234567890', 'Female', '1980-05-15', '+1-555-0101', 'sarah.johnson@hospital.com', '123 Medical Center Dr, City, State', 'MD123456', 'Cardiology', 'MD, PhD in Cardiology', 15, 'Cardiology', 'Monday-Friday', '9:00 AM - 5:00 PM', 'sarah.johnson', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'What is your mother''s maiden name?', 'Smith'),
('Dr. Michael Chen', '2345678901', 'Male', '1975-08-22', '+1-555-0102', 'michael.chen@hospital.com', '456 Health Ave, City, State', 'MD234567', 'Neurology', 'MD, PhD in Neurology', 20, 'Neurology', 'Monday-Friday', '8:00 AM - 4:00 PM', 'michael.chen', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'What city were you born in?', 'Boston'),
('Dr. Emily Rodriguez', '3456789012', 'Female', '1982-12-03', '+1-555-0103', 'emily.rodriguez@hospital.com', '789 Care St, City, State', 'MD345678', 'Pediatrics', 'MD, Board Certified Pediatrician', 12, 'Pediatrics', 'Monday-Friday', '9:00 AM - 6:00 PM', 'emily.rodriguez', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'What is your favorite color?', 'Blue'),
('Dr. James Wilson', '4567890123', 'Male', '1978-03-18', '+1-555-0104', 'james.wilson@hospital.com', '321 Wellness Blvd, City, State', 'MD456789', 'Orthopedics', 'MD, Orthopedic Surgery Specialist', 18, 'Orthopedics', 'Monday-Friday', '7:00 AM - 3:00 PM', 'james.wilson', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'What is your pet''s name?', 'Buddy'),
('Dr. Lisa Thompson', '5678901234', 'Female', '1985-07-25', '+1-555-0105', 'lisa.thompson@hospital.com', '654 Healing Way, City, State', 'MD567890', 'Dermatology', 'MD, Dermatology Specialist', 10, 'Dermatology', 'Monday-Friday', '10:00 AM - 6:00 PM', 'lisa.thompson', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'What is your favorite food?', 'Pizza');

-- Insert sample patient data
INSERT INTO patients (full_name, nic, gender, date_of_birth, contact_number, email, address, blood_group, emergency_contact, medical_history, allergies, current_medications, username, password, security_question, security_answer) VALUES
('John Smith', '9876543210', 'Male', '1990-01-15', '+1-555-0201', 'john.smith@email.com', '123 Main St, City, State', 'O+', '+1-555-0202', 'No significant medical history', 'None known', 'None', 'john.smith', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'What is your mother''s maiden name?', 'Johnson'),
('Jane Doe', '8765432109', 'Female', '1985-06-20', '+1-555-0203', 'jane.doe@email.com', '456 Oak Ave, City, State', 'A+', '+1-555-0204', 'Hypertension, Diabetes Type 2', 'Penicillin', 'Metformin, Lisinopril', 'jane.doe', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'What city were you born in?', 'New York');

-- Insert sample appointment data
INSERT INTO doctor_appointment (doctor_id, patient_id, appointment_date, appointment_time, status, notes, created_date) VALUES
(1, 1, '2025-01-15 10:30:00', '10:30 AM', 'Completed', 'Regular checkup', '2025-01-10 14:30:00'),
(2, 1, '2025-01-20 14:00:00', '2:00 PM', 'Scheduled', 'Follow-up appointment', '2025-01-12 09:15:00'),
(1, 2, '2025-01-18 11:00:00', '11:00 AM', 'Completed', 'Blood pressure check', '2025-01-11 16:45:00');

-- Insert sample prescription data
INSERT INTO prescriptions (doctor_id, patient_id, appointment_id, diagnosis, symptoms, notes, follow_up_date, prescription_date, status, total_amount, is_urgent, medicine_details) VALUES
(1, 1, 1, 'Hypertension', 'High blood pressure readings, occasional headaches', 'Continue monitoring blood pressure daily. Follow up in 2 weeks.', '2025-01-29 10:30:00', '2025-01-15 10:45:00', 'Active', 25.50, 0, '{"medicines": [{"name": "Amlodipine", "dosage": "5mg", "frequency": "Once daily"}]}'),
(2, 1, 2, 'Migraine', 'Severe headaches, sensitivity to light', 'Avoid triggers, take medication as prescribed', '2025-02-05 14:00:00', '2025-01-20 14:15:00', 'Active', 18.75, 0, '{"medicines": [{"name": "Gabapentin", "dosage": "300mg", "frequency": "Twice daily"}]}'),
(1, 2, 3, 'Type 2 Diabetes', 'Elevated blood sugar levels', 'Monitor blood sugar regularly, maintain diet', '2025-01-25 11:00:00', '2025-01-18 11:20:00', 'Completed', 32.00, 0, '{"medicines": [{"name": "Metformin", "dosage": "500mg", "frequency": "Twice daily"}]}');

-- Insert sample prescription medicines data
INSERT INTO prescription_medicines (prescription_id, medicine_id, medicine_name, generic_name, dosage_form, strength, dosage, frequency, duration, instructions, quantity, unit_price, total_price) VALUES
(1, 1, 'Amlodipine', 'Amlodipine Besylate', 'Tablet', '5mg', '1 tablet', 'Once daily', '14 days', 'Take with or without food', 14, 2.50, 35.00),
(2, 8, 'Gabapentin', 'Gabapentin', 'Capsule', '300mg', '1 capsule', 'Twice daily', '10 days', 'Take with or without food', 20, 0.75, 15.00),
(3, 10, 'Metformin', 'Metformin HCl', 'Tablet', '500mg', '1 tablet', 'Twice daily', '14 days', 'Take with meals to reduce GI upset', 28, 1.50, 42.00);

-- Insert sample medicine data
INSERT INTO medicines (medicine_name, generic_name, category, dosage_form, strength, description, indications, contraindications, side_effects, dosage_instructions, storage_conditions, manufacturer, batch_number, expiry_date, unit_price, stock_quantity, is_prescription_required, is_active) VALUES
('Amlodipine', 'Amlodipine Besylate', 'Cardiovascular', 'Tablet', '5mg', 'Calcium channel blocker for hypertension', 'Hypertension, Angina', 'Hypersensitivity to amlodipine', 'Dizziness, edema, flushing', 'Take once daily with or without food', 'Store at room temperature', 'Generic Pharma', 'BATCH001', '2025-12-31', 2.50, 1000, 1, 1),
('Metoprolol', 'Metoprolol Succinate', 'Cardiovascular', 'Tablet', '50mg', 'Beta-blocker for heart conditions', 'Hypertension, Heart failure, Arrhythmias', 'Severe heart failure, asthma', 'Fatigue, dizziness, bradycardia', 'Take with food to reduce side effects', 'Store in cool, dry place', 'CardioMed', 'BATCH002', '2025-11-30', 3.20, 800, 1, 1),
('Lisinopril', 'Lisinopril', 'Cardiovascular', 'Tablet', '10mg', 'ACE inhibitor for blood pressure control', 'Hypertension, Heart failure', 'Pregnancy, bilateral renal artery stenosis', 'Dry cough, dizziness, hyperkalemia', 'Take once daily, preferably at same time', 'Store below 25°C', 'HeartCare', 'BATCH003', '2025-10-15', 2.80, 1200, 1, 1),
('Albuterol', 'Salbutamol', 'Respiratory', 'Inhaler', '90mcg', 'Bronchodilator for asthma and COPD', 'Asthma, COPD, Bronchospasm', 'Hypersensitivity to salbutamol', 'Tremor, nervousness, tachycardia', 'Use as needed for breathing difficulties', 'Store at room temperature', 'RespiraMed', 'BATCH004', '2025-09-20', 15.50, 500, 1, 1),
('Prednisone', 'Prednisone', 'Respiratory', 'Tablet', '5mg', 'Corticosteroid for inflammation', 'Asthma, Allergic reactions, Inflammation', 'Systemic fungal infections, live vaccines', 'Weight gain, mood changes, insomnia', 'Take with food to reduce stomach upset', 'Store in original container', 'SteroidPharma', 'BATCH005', '2025-08-10', 1.20, 2000, 1, 1),
('Omeprazole', 'Omeprazole', 'Gastrointestinal', 'Capsule', '20mg', 'Proton pump inhibitor for acid reduction', 'GERD, Peptic ulcers, Acid reflux', 'Hypersensitivity to omeprazole', 'Headache, nausea, diarrhea', 'Take 30 minutes before first meal', 'Store below 25°C', 'GastroMed', 'BATCH006', '2025-07-25', 4.50, 1500, 1, 1),
('Metoclopramide', 'Metoclopramide HCl', 'Gastrointestinal', 'Tablet', '10mg', 'Prokinetic agent for nausea and vomiting', 'Nausea, Vomiting, Gastroparesis', 'Gastrointestinal obstruction, pheochromocytoma', 'Drowsiness, restlessness, tardive dyskinesia', 'Take 30 minutes before meals', 'Store in cool, dry place', 'DigestMed', 'BATCH007', '2025-06-30', 1.80, 800, 1, 1),
('Gabapentin', 'Gabapentin', 'Neurological', 'Capsule', '300mg', 'Anticonvulsant and neuropathic pain medication', 'Neuropathic pain, Epilepsy, Neuralgia', 'Hypersensitivity to gabapentin', 'Dizziness, drowsiness, peripheral edema', 'Take with or without food', 'Store at room temperature', 'NeuroPharma', 'BATCH008', '2025-05-15', 5.20, 600, 1, 1),
('Diazepam', 'Diazepam', 'Neurological', 'Tablet', '5mg', 'Benzodiazepine for anxiety and muscle spasms', 'Anxiety, Muscle spasms, Seizures', 'Severe respiratory insufficiency, sleep apnea', 'Drowsiness, confusion, dependence', 'Take as directed by physician', 'Store in secure location', 'NeuroMed', 'BATCH009', '2025-04-20', 2.10, 400, 1, 1),
('Metformin', 'Metformin HCl', 'Endocrine', 'Tablet', '500mg', 'Biguanide antidiabetic medication', 'Type 2 Diabetes', 'Severe renal impairment, metabolic acidosis', 'Nausea, diarrhea, metallic taste', 'Take with meals to reduce GI upset', 'Store below 30°C', 'DiabMed', 'BATCH010', '2025-03-10', 1.50, 2000, 1, 1),
('Levothyroxine', 'Levothyroxine Sodium', 'Endocrine', 'Tablet', '50mcg', 'Thyroid hormone replacement', 'Hypothyroidism, Thyroid cancer', 'Uncorrected adrenal insufficiency', 'Palpitations, weight loss, insomnia', 'Take on empty stomach, 30 minutes before food', 'Store in original container', 'ThyroMed', 'BATCH011', '2025-02-28', 3.40, 800, 1, 1),
('Ibuprofen', 'Ibuprofen', 'Pain Management', 'Tablet', '400mg', 'NSAID for pain and inflammation', 'Pain, Inflammation, Fever', 'Active peptic ulcer, severe heart failure', 'Stomach upset, dizziness, rash', 'Take with food or milk', 'Store at room temperature', 'PainRelief', 'BATCH012', '2025-01-15', 0.80, 3000, 0, 1),
('Tramadol', 'Tramadol HCl', 'Pain Management', 'Tablet', '50mg', 'Opioid analgesic for moderate to severe pain', 'Moderate to severe pain', 'Acute intoxication with alcohol, opioids', 'Nausea, dizziness, constipation', 'Take as directed, do not crush or chew', 'Store securely', 'PainMed', 'BATCH013', '2025-12-01', 6.50, 500, 1, 1),
('Amoxicillin', 'Amoxicillin', 'Antibiotic', 'Capsule', '500mg', 'Penicillin antibiotic for bacterial infections', 'Bacterial infections, Respiratory infections', 'Penicillin allergy', 'Diarrhea, nausea, rash', 'Take every 8 hours, complete full course', 'Store below 25°C', 'AntibioMed', 'BATCH014', '2025-11-15', 3.20, 1000, 1, 1),
('Azithromycin', 'Azithromycin', 'Antibiotic', 'Tablet', '250mg', 'Macrolide antibiotic for bacterial infections', 'Bacterial infections, STIs, Respiratory infections', 'Hypersensitivity to macrolides', 'Nausea, diarrhea, abdominal pain', 'Take once daily, with or without food', 'Store at room temperature', 'AntibioMed', 'BATCH015', '2025-10-30', 8.50, 600, 1, 1);
