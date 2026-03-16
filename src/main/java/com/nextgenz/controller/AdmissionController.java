package com.nextgenz.controller;

import com.nextgenz.entity.Student;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admission")
public class AdmissionController {

    @GetMapping("/step1")
    public String step1Form(Model model) {
        if (!model.containsAttribute("student")) {
            model.addAttribute("student", new Student());
        }
        return "admission-step1";
    }

    @PostMapping("/step1")
    public String step1Submit(@ModelAttribute("student") Student student, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "admission-step1";
        }
        session.setAttribute("student", student);
        return "redirect:/admission/step2";
    }

    @GetMapping("/step2")
    public String step2Form(Model model, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return "redirect:/admission/step1"; // Redirect if no student in session
        }
        model.addAttribute("student", student);
        return "admission-step2";
    }

    @PostMapping("/step2")
    public String step2Submit(@ModelAttribute("student") Student studentForm, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return "redirect:/admission/step1";
        }
        // Assuming step2 collects more student details, e.g., parent info
        // For now, just update the existing student object with potential new fields from the form
        // student.setFatherName(studentForm.getFatherName()); // Example
        // student.setMotherName(studentForm.getMotherName()); // Example
        session.setAttribute("student", student); // Update session with potentially modified student
        return "redirect:/admission/step3";
    }

    @GetMapping("/step3")
    public String step3Form(Model model, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return "redirect:/admission/step1";
        }
        model.addAttribute("student", student);
        return "admission-step3";
    }

    @PostMapping("/step3")
    public String step3Submit(@ModelAttribute("student") Student studentForm, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return "redirect:/admission/step1";
        }
        // Update student object with details from step 3 form
        session.setAttribute("student", student);
        return "redirect:/admission/step4";
    }

    @GetMapping("/step4")
    public String step4Form(Model model, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return "redirect:/admission/step1";
        }
        model.addAttribute("student", student);
        return "admission-step4";
    }

    @PostMapping("/step4")
    public String step4Submit(@ModelAttribute("student") Student studentForm, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return "redirect:/admission/step1";
        }
        // Update student object with details from step 4 form
        session.setAttribute("student", student);
        return "redirect:/admission/step5";
    }

    @GetMapping("/step5")
    public String step5Form(Model model, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return "redirect:/admission/step1";
        }
        model.addAttribute("student", student);
        return "admission-step5";
    }

    @PostMapping("/step5")
    public String step5Submit(@ModelAttribute("student") Student studentForm, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return "redirect:/admission/step1";
        }
        // Update student object with details from step 5 form
        session.setAttribute("student", student);
        return "redirect:/admission/step6";
    }

    @GetMapping("/step6")
    public String step6Form(Model model, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return "redirect:/admission/step1";
        }
        model.addAttribute("student", student);
        return "admission-step6";
    }

    @PostMapping("/step6")
    public String step6Submit(HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return "redirect:/admission/step1";
        }
        // Save photo + payment details
        // Here you would typically save the 'student' object to the database
        System.out.println("Final Student Data for saving: " + student.getName() + ", " + student.getEmail());
        session.removeAttribute("student"); // Clear student from session after final submission
        return "redirect:/admission/confirmation";
    }

    @GetMapping("/confirmation")
    public String confirmationPage() {
        return "admission-confirmation";
    }
}
