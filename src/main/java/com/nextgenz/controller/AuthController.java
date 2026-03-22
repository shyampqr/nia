package com.nextgenz.controller;

import com.nextgenz.model.Student;
import com.nextgenz.repository.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final StudentRepository studentRepo;
    private final PasswordEncoder   encoder;

    public AuthController(StudentRepository studentRepo,
                          PasswordEncoder encoder) {
        this.studentRepo = studentRepo;
        this.encoder     = encoder;
    }

    /** Root → redirect to exam portal */
    @GetMapping("/")
    public String root() {
        return "redirect:/exam";
    }

    /** Login page */
    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout,
                            Model model) {
        if (error  != null) model.addAttribute("errorMsg",  "Invalid username or password.");
        if (logout != null) model.addAttribute("logoutMsg", "You have been logged out.");
        return "auth/login";
    }

    /** Registration page */
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("student", new Student());
        return "auth/register";
    }

    /** Handle registration */
    @PostMapping("/register")
    public String register(@ModelAttribute Student student,
                           RedirectAttributes redirectAttrs) {
        if (studentRepo.existsByUsername(student.getUsername())) {
            redirectAttrs.addFlashAttribute("errorMsg",
                "Username already taken. Please choose another.");
            return "redirect:/register";
        }
        student.setPassword(encoder.encode(student.getPassword()));
        student.setRole("ROLE_STUDENT");
        student.setEnabled(true);
        studentRepo.save(student);
        redirectAttrs.addFlashAttribute("successMsg",
            "Account created! Please log in.");
        return "redirect:/login";
    }
}
