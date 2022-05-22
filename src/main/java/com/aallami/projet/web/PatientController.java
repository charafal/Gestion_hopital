package com.aallami.projet.web;


import com.aallami.projet.entities.Patient;
import com.aallami.projet.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientrepository;

    @GetMapping(path = "/user/index")
    public String patients(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "keyword",defaultValue = "") String keyword)
    {
        Page<Patient> patient = patientrepository.findByNomContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listPatient",patient.getContent());
        model.addAttribute("pages", new int[patient.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "patient";
    }

    @GetMapping("/admin/delete")
    public  String delete(Long id, String keyword, int page)
    {
        patientrepository.deleteById(id);
        return  "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/")
    public  String Home()
    {
        return  "home";
    }

    @GetMapping("/user/patient")
    @ResponseBody
    public List<Patient> listpatient()
    {
        return patientrepository.findAll();
    }

    @GetMapping("/admin/formpatient")
    public String formpatient(Model model)
    {
        model.addAttribute("patient", new Patient());
        return "/formpatient";
    }
    @PostMapping("/admin/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "")  String keyword)
    {
        if(bindingResult.hasErrors()) return "formpatient";
        patientrepository.save(patient);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/editPatient")
    public String editPatient(Model model, Long id, String keyword, int page)
    {
        Patient patient = patientrepository.findById(id).orElse(null);
        if(patient == null) throw new RuntimeException("Patient introuvable");
        model.addAttribute("patient", patient);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "editPatient";
    }
}

