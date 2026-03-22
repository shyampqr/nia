package com.nextgenz.controller;

import com.nextgenz.entity.Question;
import com.nextgenz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/questions")
public class AdminQuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public String listQuestions(Model model) {
        model.addAttribute("questions", questionService.getAllQuestions());
        model.addAttribute("newQuestion", new Question());
        return "admin-questions";
    }

    @PostMapping("/add")
    public String addQuestion(@ModelAttribute Question question) {
        questionService.saveQuestion(question);
        return "redirect:/admin/questions";
    }

    @GetMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return "redirect:/admin/questions";
    }
}
