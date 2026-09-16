package com.example.test.Controller;

import com.example.test.Model.Medicine;
import com.example.test.Service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/medicine")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping("/list")
    public String listMedicines(Model model) {
        List<Medicine> medicines = medicineService.getAllMedicines();
        model.addAttribute("medicines", medicines);
        return "medicine/list";
    }

    @GetMapping("/add")
    public String addMedicineForm(Model model) {
        model.addAttribute("medicine", new Medicine());
        return "medicine/add";
    }

    @PostMapping("/add")
    public String saveMedicine(@ModelAttribute Medicine medicine, Model model) {
        medicineService.saveMedicine(medicine);
        return "redirect:/medicine/list";
    }

    // JSON for inventory widgets or client-side filtering
    @GetMapping("/api/all")
    @ResponseBody
    public List<Medicine> listMedicinesJson() {
        return medicineService.getAllMedicines();
    }

    @GetMapping("/categories")
    @ResponseBody
    public List<String> getCategories() {
        return medicineService.getAllCategories();
    }

    @GetMapping("/by-category/{category}")
    @ResponseBody
    public List<Medicine> getMedicinesByCategory(@PathVariable String category) {
        return medicineService.getMedicinesByCategory(category);
    }

    @GetMapping("/test")
    @ResponseBody
    public String testMedicineData() {
        List<Medicine> medicines = medicineService.getAllMedicines();
        return "Found " + medicines.size() + " medicines in database";
    }
}


