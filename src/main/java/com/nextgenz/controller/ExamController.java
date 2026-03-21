package com.nextgenz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExamController {

    @GetMapping("/exam")
    public String examPage() {
        return "exam-form";
    }
}
