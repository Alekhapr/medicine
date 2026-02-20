package com.medicinestore.inventory.controller;

import com.medicinestore.inventory.entity.Medicine;
import com.medicinestore.inventory.service.MedicineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class MedicineViewController {

    private final MedicineService medicineService;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("stats", medicineService.getDashboardStats());
        model.addAttribute("expiredMedicines", medicineService.getExpiredMedicines());
        model.addAttribute("lowStockMedicines", medicineService.getLowStockMedicines());
        return "dashboard";
    }

    @GetMapping("/medicines")
    public String medicineList(@RequestParam(defaultValue = "") String search,
                               @RequestParam(defaultValue = "all") String filter,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               Model model) {
        Page<Medicine> medicinePage = medicineService.getMedicines(search, PageRequest.of(page, size, Sort.by("name")));
        model.addAttribute("medicines", medicinePage.getContent());
        model.addAttribute("medicinePage", medicinePage);
        model.addAttribute("search", search);
        model.addAttribute("filter", filter);
        return "medicine-list";
    }

    @GetMapping("/medicines/new")
    public String newMedicineForm(Model model) {
        model.addAttribute("medicine", new Medicine());
        return "medicine-form";
    }

    @GetMapping("/medicines/{id}/edit")
    public String editMedicineForm(@PathVariable Long id, Model model) {
        model.addAttribute("medicine", medicineService.getMedicine(id));
        return "medicine-form";
    }

    @PostMapping("/medicines")
    public String saveMedicine(@Valid @ModelAttribute("medicine") Medicine medicine,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "medicine-form";
        }

        if (medicine.getId() == null) {
            medicineService.addMedicine(medicine);
            redirectAttributes.addFlashAttribute("successMessage", "Medicine added successfully.");
        } else {
            medicineService.updateMedicine(medicine.getId(), medicine);
            redirectAttributes.addFlashAttribute("successMessage", "Medicine updated successfully.");
        }
        return "redirect:/medicines";
    }
}
