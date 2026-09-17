package com.example.test.Controller;

import com.example.test.Model.Medicine;
import com.example.test.Service.MedicineService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/medicine")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    private boolean isPharmacist(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("pharmacistUsername") != null;
    }

    @GetMapping("/list")
    public String listMedicines(HttpServletRequest request, Model model) {
        if (!isPharmacist(request)) {
            return "redirect:/pharmacist/login";
        }
        List<Medicine> medicines = medicineService.getAllMedicines();
        model.addAttribute("medicines", medicines);
        return "medicine/list";
    }

    @GetMapping("/add")
    public String addMedicineForm(HttpServletRequest request, Model model) {
        if (!isPharmacist(request)) {
            return "redirect:/pharmacist/login";
        }
        model.addAttribute("medicine", new Medicine());
        return "medicine/add";
    }

    @PostMapping("/add")
    public String saveMedicine(@Valid @ModelAttribute("medicine") Medicine medicine,
                               BindingResult bindingResult,
                               HttpServletRequest request,
                               Model model) {
        if (!isPharmacist(request)) {
            return "redirect:/pharmacist/login";
        }
        if (bindingResult.hasErrors()) {
            return "medicine/add";
        }
        medicineService.saveMedicine(medicine);
        return "redirect:/medicine/list";
    }

    // JSON for inventory widgets or client-side filtering
    @GetMapping("/api/all")
    @ResponseBody
    public List<Medicine> listMedicinesJson(HttpServletRequest request) {
        if (!isPharmacist(request)) {
            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        }
        return medicineService.getAllMedicines();
    }

    @GetMapping("/categories")
    @ResponseBody
    public List<String> getCategories(HttpServletRequest request) {
        if (!isPharmacist(request)) {
            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        }
        return medicineService.getAllCategories();
    }

    @GetMapping("/by-category/{category}")
    @ResponseBody
    public List<Medicine> getMedicinesByCategory(@PathVariable String category, HttpServletRequest request) {
        if (!isPharmacist(request)) {
            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        }
        return medicineService.getMedicinesByCategory(category);
    }
}


