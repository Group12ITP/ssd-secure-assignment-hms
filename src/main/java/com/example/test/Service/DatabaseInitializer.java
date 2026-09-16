package com.example.test.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        // Check if medicines table exists and has data
        try {
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM medicines", Integer.class);
            if (count == null || count == 0) {
                System.out.println("Initializing medicine database...");
                initializeMedicineData();
            } else {
                System.out.println("Medicine database already initialized with " + count + " records.");
            }
        } catch (Exception e) {
            System.out.println("Medicine table not found, will be created by Hibernate.");
        }
    }

    private void initializeMedicineData() {
        // Insert sample medicine data directly
        String[] medicines = {
            "INSERT INTO medicines (medicine_name, generic_name, category, dosage_form, strength, description, indications, contraindications, side_effects, dosage_instructions, storage_conditions, manufacturer, batch_number, expiry_date, unit_price, stock_quantity, is_prescription_required, is_active) VALUES ('Amlodipine', 'Amlodipine Besylate', 'Cardiovascular', 'Tablet', '5mg', 'Calcium channel blocker for hypertension', 'Hypertension, Angina', 'Hypersensitivity to amlodipine', 'Dizziness, edema, flushing', 'Take once daily with or without food', 'Store at room temperature', 'Generic Pharma', 'BATCH001', '2025-12-31', 2.5, 1000, 1, 1)",
            "INSERT INTO medicines (medicine_name, generic_name, category, dosage_form, strength, description, indications, contraindications, side_effects, dosage_instructions, storage_conditions, manufacturer, batch_number, expiry_date, unit_price, stock_quantity, is_prescription_required, is_active) VALUES ('Metoprolol', 'Metoprolol Succinate', 'Cardiovascular', 'Tablet', '50mg', 'Beta-blocker for heart conditions', 'Hypertension, Heart failure, Arrhythmias', 'Severe heart failure, asthma', 'Fatigue, dizziness, bradycardia', 'Take with food to reduce side effects', 'Store in cool, dry place', 'CardioMed', 'BATCH002', '2025-11-30', 3.2, 800, 1, 1)",
            "INSERT INTO medicines (medicine_name, generic_name, category, dosage_form, strength, description, indications, contraindications, side_effects, dosage_instructions, storage_conditions, manufacturer, batch_number, expiry_date, unit_price, stock_quantity, is_prescription_required, is_active) VALUES ('Lisinopril', 'Lisinopril', 'Cardiovascular', 'Tablet', '10mg', 'ACE inhibitor for blood pressure control', 'Hypertension, Heart failure', 'Pregnancy, bilateral renal artery stenosis', 'Dry cough, dizziness, hyperkalemia', 'Take once daily, preferably at same time', 'Store below 25°C', 'HeartCare', 'BATCH003', '2025-10-15', 2.8, 1200, 1, 1)",
            "INSERT INTO medicines (medicine_name, generic_name, category, dosage_form, strength, description, indications, contraindications, side_effects, dosage_instructions, storage_conditions, manufacturer, batch_number, expiry_date, unit_price, stock_quantity, is_prescription_required, is_active) VALUES ('Albuterol', 'Salbutamol', 'Respiratory', 'Inhaler', '90mcg', 'Bronchodilator for asthma and COPD', 'Asthma, COPD, Bronchospasm', 'Hypersensitivity to salbutamol', 'Tremor, nervousness, tachycardia', 'Use as needed for breathing difficulties', 'Store at room temperature', 'RespiraMed', 'BATCH004', '2025-09-20', 15.5, 500, 1, 1)",
            "INSERT INTO medicines (medicine_name, generic_name, category, dosage_form, strength, description, indications, contraindications, side_effects, dosage_instructions, storage_conditions, manufacturer, batch_number, expiry_date, unit_price, stock_quantity, is_prescription_required, is_active) VALUES ('Prednisone', 'Prednisone', 'Respiratory', 'Tablet', '5mg', 'Corticosteroid for inflammation', 'Asthma, Allergic reactions, Inflammation', 'Systemic fungal infections, live vaccines', 'Weight gain, mood changes, insomnia', 'Take with food to reduce stomach upset', 'Store in original container', 'SteroidPharma', 'BATCH005', '2025-08-10', 1.2, 2000, 1, 1)",
            "INSERT INTO medicines (medicine_name, generic_name, category, dosage_form, strength, description, indications, contraindications, side_effects, dosage_instructions, storage_conditions, manufacturer, batch_number, expiry_date, unit_price, stock_quantity, is_prescription_required, is_active) VALUES ('Omeprazole', 'Omeprazole', 'Gastrointestinal', 'Capsule', '20mg', 'Proton pump inhibitor for acid reduction', 'GERD, Peptic ulcers, Acid reflux', 'Hypersensitivity to omeprazole', 'Headache, nausea, diarrhea', 'Take 30 minutes before first meal', 'Store below 25°C', 'GastroMed', 'BATCH006', '2025-07-25', 4.5, 1500, 1, 1)",
            "INSERT INTO medicines (medicine_name, generic_name, category, dosage_form, strength, description, indications, contraindications, side_effects, dosage_instructions, storage_conditions, manufacturer, batch_number, expiry_date, unit_price, stock_quantity, is_prescription_required, is_active) VALUES ('Metoclopramide', 'Metoclopramide HCl', 'Gastrointestinal', 'Tablet', '10mg', 'Prokinetic agent for nausea and vomiting', 'Nausea, Vomiting, Gastroparesis', 'Gastrointestinal obstruction, pheochromocytoma', 'Drowsiness, restlessness, tardive dyskinesia', 'Take 30 minutes before meals', 'Store in cool, dry place', 'DigestMed', 'BATCH007', '2025-06-30', 1.8, 800, 1, 1)",
            "INSERT INTO medicines (medicine_name, generic_name, category, dosage_form, strength, description, indications, contraindications, side_effects, dosage_instructions, storage_conditions, manufacturer, batch_number, expiry_date, unit_price, stock_quantity, is_prescription_required, is_active) VALUES ('Gabapentin', 'Gabapentin', 'Neurological', 'Capsule', '300mg', 'Anticonvulsant and neuropathic pain medication', 'Neuropathic pain, Epilepsy, Neuralgia', 'Hypersensitivity to gabapentin', 'Dizziness, drowsiness, peripheral edema', 'Take with or without food', 'Store at room temperature', 'NeuroPharma', 'BATCH008', '2025-05-15', 5.2, 600, 1, 1)",
            "INSERT INTO medicines (medicine_name, generic_name, category, dosage_form, strength, description, indications, contraindications, side_effects, dosage_instructions, storage_conditions, manufacturer, batch_number, expiry_date, unit_price, stock_quantity, is_prescription_required, is_active) VALUES ('Diazepam', 'Diazepam', 'Neurological', 'Tablet', '5mg', 'Benzodiazepine for anxiety and muscle spasms', 'Anxiety, Muscle spasms, Seizures', 'Severe respiratory insufficiency, sleep apnea', 'Drowsiness, confusion, dependence', 'Take as directed by physician', 'Store in secure location', 'NeuroMed', 'BATCH009', '2025-04-20', 2.1, 400, 1, 1)",
            "INSERT INTO medicines (medicine_name, generic_name, category, dosage_form, strength, description, indications, contraindications, side_effects, dosage_instructions, storage_conditions, manufacturer, batch_number, expiry_date, unit_price, stock_quantity, is_prescription_required, is_active) VALUES ('Metformin', 'Metformin HCl', 'Endocrine', 'Tablet', '500mg', 'Biguanide antidiabetic medication', 'Type 2 Diabetes', 'Severe renal impairment, metabolic acidosis', 'Nausea, diarrhea, metallic taste', 'Take with meals to reduce GI upset', 'Store below 30°C', 'DiabMed', 'BATCH010', '2025-03-10', 1.5, 2000, 1, 1)",
            "INSERT INTO medicines (medicine_name, generic_name, category, dosage_form, strength, description, indications, contraindications, side_effects, dosage_instructions, storage_conditions, manufacturer, batch_number, expiry_date, unit_price, stock_quantity, is_prescription_required, is_active) VALUES ('Levothyroxine', 'Levothyroxine Sodium', 'Endocrine', 'Tablet', '50mcg', 'Thyroid hormone replacement', 'Hypothyroidism, Thyroid cancer', 'Uncorrected adrenal insufficiency', 'Palpitations, weight loss, insomnia', 'Take on empty stomach, 30 minutes before food', 'Store in original container', 'ThyroMed', 'BATCH011', '2025-02-28', 3.4, 800, 1, 1)",
            "INSERT INTO medicines (medicine_name, generic_name, category, dosage_form, strength, description, indications, contraindications, side_effects, dosage_instructions, storage_conditions, manufacturer, batch_number, expiry_date, unit_price, stock_quantity, is_prescription_required, is_active) VALUES ('Ibuprofen', 'Ibuprofen', 'Pain Management', 'Tablet', '400mg', 'NSAID for pain and inflammation', 'Pain, Inflammation, Fever', 'Active peptic ulcer, severe heart failure', 'Stomach upset, dizziness, rash', 'Take with food or milk', 'Store at room temperature', 'PainRelief', 'BATCH012', '2025-01-15', 0.8, 3000, 0, 1)",
            "INSERT INTO medicines (medicine_name, generic_name, category, dosage_form, strength, description, indications, contraindications, side_effects, dosage_instructions, storage_conditions, manufacturer, batch_number, expiry_date, unit_price, stock_quantity, is_prescription_required, is_active) VALUES ('Tramadol', 'Tramadol HCl', 'Pain Management', 'Tablet', '50mg', 'Opioid analgesic for moderate to severe pain', 'Moderate to severe pain', 'Acute intoxication with alcohol, opioids', 'Nausea, dizziness, constipation', 'Take as directed, do not crush or chew', 'Store securely', 'PainMed', 'BATCH013', '2025-12-01', 6.5, 500, 1, 1)",
            "INSERT INTO medicines (medicine_name, generic_name, category, dosage_form, strength, description, indications, contraindications, side_effects, dosage_instructions, storage_conditions, manufacturer, batch_number, expiry_date, unit_price, stock_quantity, is_prescription_required, is_active) VALUES ('Amoxicillin', 'Amoxicillin', 'Antibiotic', 'Capsule', '500mg', 'Penicillin antibiotic for bacterial infections', 'Bacterial infections, Respiratory infections', 'Penicillin allergy', 'Diarrhea, nausea, rash', 'Take every 8 hours, complete full course', 'Store below 25°C', 'AntibioMed', 'BATCH014', '2025-11-15', 3.2, 1000, 1, 1)",
            "INSERT INTO medicines (medicine_name, generic_name, category, dosage_form, strength, description, indications, contraindications, side_effects, dosage_instructions, storage_conditions, manufacturer, batch_number, expiry_date, unit_price, stock_quantity, is_prescription_required, is_active) VALUES ('Azithromycin', 'Azithromycin', 'Antibiotic', 'Tablet', '250mg', 'Macrolide antibiotic for bacterial infections', 'Bacterial infections, STIs, Respiratory infections', 'Hypersensitivity to macrolides', 'Nausea, diarrhea, abdominal pain', 'Take once daily, with or without food', 'Store at room temperature', 'AntibioMed', 'BATCH015', '2025-10-30', 8.5, 600, 1, 1)"
        };

        for (String medicine : medicines) {
            try {
                jdbcTemplate.execute(medicine);
            } catch (Exception e) {
                System.err.println("Error inserting medicine: " + e.getMessage());
            }
        }
        
        System.out.println("Medicine database initialized successfully!");
    }
}
