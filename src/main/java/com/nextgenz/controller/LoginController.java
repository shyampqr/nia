package com.nextgenz.controller;

import com.nextgenz.service.OtpService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private OtpService otpService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String sendOtp(@RequestParam("phone") String phone, HttpSession session, Model model) {
        String otp = otpService.generateOtp(phone);
        // In a real application, send this OTP via SMS. Here we just print it.
        System.out.println("OTP for " + phone + " is: " + otp);

        session.setAttribute("phoneNumber", phone);
        return "redirect:/otp-verify";
    }

    @GetMapping("/otp-verify")
    public String otpVerifyPage() {
        return "otp-verify";
    }

    @PostMapping("/otp-verify")
    public String verifyOtp(@RequestParam("otp") String otp, HttpSession session, Model model) {
        String phone = (String) session.getAttribute("phoneNumber");
        if (phone != null && otpService.validateOtp(phone, otp)) {
            session.setAttribute("authenticatedUser", phone);
            return "redirect:/chat";
        } else {
            model.addAttribute("error", "Invalid OTP or Session Expired");
            return "otp-verify";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
