package com.example.test.Service;

import com.example.test.Model.Medicine;
import com.example.test.Repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MedicineDataInitializer implements CommandLineRunner {

    @Autowired
    private MedicineRepository medicineRepository;

    @Override
    public void run(String... args) throws Exception {
        // Only initialize if no medicines exist
        if (medicineRepository.count() == 0) {
            initializeMedicines();
        }
    }

    private void initializeMedicines() {
        // Cardiovascular Medicines
        createMedicine("Amlodipine", "Amlodipine Besylate", "Cardiovascular", "Tablet", "5mg", 
                      "Calcium channel blocker for hypertension", "Hypertension, Angina", 
                      "Hypersensitivity to amlodipine", "Dizziness, edema, flushing", 
                      "Take once daily with or without food", "Store at room temperature", 
                      "Generic Pharma", "BATCH001", "2025-12-31", 2.50, 1000, true);

        createMedicine("Metoprolol", "Metoprolol Succinate", "Cardiovascular", "Tablet", "50mg", 
                      "Beta-blocker for heart conditions", "Hypertension, Heart failure, Arrhythmias", 
                      "Severe heart failure, asthma", "Fatigue, dizziness, bradycardia", 
                      "Take with food to reduce side effects", "Store in cool, dry place", 
                      "CardioMed", "BATCH002", "2025-11-30", 3.20, 800, true);

        createMedicine("Lisinopril", "Lisinopril", "Cardiovascular", "Tablet", "10mg", 
                      "ACE inhibitor for blood pressure control", "Hypertension, Heart failure", 
                      "Pregnancy, bilateral renal artery stenosis", "Dry cough, dizziness, hyperkalemia", 
                      "Take once daily, preferably at same time", "Store below 25°C", 
                      "HeartCare", "BATCH003", "2025-10-15", 2.80, 1200, true);

        // Respiratory Medicines
        createMedicine("Albuterol", "Salbutamol", "Respiratory", "Inhaler", "90mcg", 
                      "Bronchodilator for asthma and COPD", "Asthma, COPD, Bronchospasm", 
                      "Hypersensitivity to salbutamol", "Tremor, nervousness, tachycardia", 
                      "Use as needed for breathing difficulties", "Store at room temperature", 
                      "RespiraMed", "BATCH004", "2025-09-20", 15.50, 500, true);

        createMedicine("Prednisone", "Prednisone", "Respiratory", "Tablet", "5mg", 
                      "Corticosteroid for inflammation", "Asthma, Allergic reactions, Inflammation", 
                      "Systemic fungal infections, live vaccines", "Weight gain, mood changes, insomnia", 
                      "Take with food to reduce stomach upset", "Store in original container", 
                      "SteroidPharma", "BATCH005", "2025-08-10", 1.20, 2000, true);

        // Gastrointestinal Medicines
        createMedicine("Omeprazole", "Omeprazole", "Gastrointestinal", "Capsule", "20mg", 
                      "Proton pump inhibitor for acid reduction", "GERD, Peptic ulcers, Acid reflux", 
                      "Hypersensitivity to omeprazole", "Headache, nausea, diarrhea", 
                      "Take 30 minutes before first meal", "Store below 25°C", 
                      "GastroMed", "BATCH006", "2025-07-25", 4.50, 1500, true);

        createMedicine("Metoclopramide", "Metoclopramide HCl", "Gastrointestinal", "Tablet", "10mg", 
                      "Prokinetic agent for nausea and vomiting", "Nausea, Vomiting, Gastroparesis", 
                      "Gastrointestinal obstruction, pheochromocytoma", "Drowsiness, restlessness, tardive dyskinesia", 
                      "Take 30 minutes before meals", "Store in cool, dry place", 
                      "DigestMed", "BATCH007", "2025-06-30", 1.80, 800, true);

        // Neurological Medicines
        createMedicine("Gabapentin", "Gabapentin", "Neurological", "Capsule", "300mg", 
                      "Anticonvulsant and neuropathic pain medication", "Neuropathic pain, Epilepsy, Neuralgia", 
                      "Hypersensitivity to gabapentin", "Dizziness, drowsiness, peripheral edema", 
                      "Take with or without food", "Store at room temperature", 
                      "NeuroPharma", "BATCH008", "2025-05-15", 5.20, 600, true);

        createMedicine("Diazepam", "Diazepam", "Neurological", "Tablet", "5mg", 
                      "Benzodiazepine for anxiety and muscle spasms", "Anxiety, Muscle spasms, Seizures", 
                      "Severe respiratory insufficiency, sleep apnea", "Drowsiness, confusion, dependence", 
                      "Take as directed by physician", "Store in secure location", 
                      "NeuroMed", "BATCH009", "2025-04-20", 2.10, 400, true);

        // Endocrine Medicines
        createMedicine("Metformin", "Metformin HCl", "Endocrine", "Tablet", "500mg", 
                      "Biguanide antidiabetic medication", "Type 2 Diabetes", 
                      "Severe renal impairment, metabolic acidosis", "Nausea, diarrhea, metallic taste", 
                      "Take with meals to reduce GI upset", "Store below 30°C", 
                      "DiabMed", "BATCH010", "2025-03-10", 1.50, 2000, true);

        createMedicine("Levothyroxine", "Levothyroxine Sodium", "Endocrine", "Tablet", "50mcg", 
                      "Thyroid hormone replacement", "Hypothyroidism, Thyroid cancer", 
                      "Uncorrected adrenal insufficiency", "Palpitations, weight loss, insomnia", 
                      "Take on empty stomach, 30 minutes before food", "Store in original container", 
                      "ThyroMed", "BATCH011", "2025-02-28", 3.40, 800, true);

        // Pain Management
        createMedicine("Ibuprofen", "Ibuprofen", "Pain Management", "Tablet", "400mg", 
                      "NSAID for pain and inflammation", "Pain, Inflammation, Fever", 
                      "Active peptic ulcer, severe heart failure", "Stomach upset, dizziness, rash", 
                      "Take with food or milk", "Store at room temperature", 
                      "PainRelief", "BATCH012", "2025-01-15", 0.80, 3000, false);

        createMedicine("Tramadol", "Tramadol HCl", "Pain Management", "Tablet", "50mg", 
                      "Opioid analgesic for moderate to severe pain", "Moderate to severe pain", 
                      "Acute intoxication with alcohol, opioids", "Nausea, dizziness, constipation", 
                      "Take as directed, do not crush or chew", "Store securely", 
                      "PainMed", "BATCH013", "2025-12-01", 6.50, 500, true);

        // Antibiotics
        createMedicine("Amoxicillin", "Amoxicillin", "Antibiotic", "Capsule", "500mg", 
                      "Penicillin antibiotic for bacterial infections", "Bacterial infections, Respiratory infections", 
                      "Penicillin allergy", "Diarrhea, nausea, rash", 
                      "Take every 8 hours, complete full course", "Store below 25°C", 
                      "AntibioMed", "BATCH014", "2025-11-15", 3.20, 1000, true);

        createMedicine("Azithromycin", "Azithromycin", "Antibiotic", "Tablet", "250mg", 
                      "Macrolide antibiotic for bacterial infections", "Bacterial infections, STIs, Respiratory infections", 
                      "Hypersensitivity to macrolides", "Nausea, diarrhea, abdominal pain", 
                      "Take once daily, with or without food", "Store at room temperature", 
                      "AntibioMed", "BATCH015", "2025-10-30", 8.50, 600, true);

        System.out.println("Medicine database initialized with sample data.");
    }

    private void createMedicine(String medicineName, String genericName, String category, 
                              String dosageForm, String strength, String description, 
                              String indications, String contraindications, String sideEffects, 
                              String dosageInstructions, String storageConditions, 
                              String manufacturer, String batchNumber, String expiryDate, 
                              Double unitPrice, Integer stockQuantity, Boolean isPrescriptionRequired) {
        
        Medicine medicine = new Medicine();
        medicine.setMedicineName(medicineName);
        medicine.setGenericName(genericName);
        medicine.setCategory(category);
        medicine.setDosageForm(dosageForm);
        medicine.setStrength(strength);
        medicine.setDescription(description);
        medicine.setIndications(indications);
        medicine.setContraindications(contraindications);
        medicine.setSideEffects(sideEffects);
        medicine.setDosageInstructions(dosageInstructions);
        medicine.setStorageConditions(storageConditions);
        medicine.setManufacturer(manufacturer);
        medicine.setBatchNumber(batchNumber);
        medicine.setExpiryDate(expiryDate);
        medicine.setUnitPrice(unitPrice);
        medicine.setStockQuantity(stockQuantity);
        medicine.setIsPrescriptionRequired(isPrescriptionRequired);
        medicine.setIsActive(true);
        
        medicineRepository.save(medicine);
    }
}


