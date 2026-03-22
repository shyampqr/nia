package com.nextgenz.controller;

import com.nextgenz.entity.Question;
import com.nextgenz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ExamApiController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/questions")
    public List<Map<String, Object>> getQuestions(@RequestParam String classLevel, @RequestParam String subject) {
        List<Question> questions = questionService.getQuestionsForExam(classLevel, subject, 25);
        List<Map<String, Object>> response = new ArrayList<>();

        for (Question q : questions) {
            Map<String, Object> map = new HashMap<>();
            map.put("q", q.getQuestionText());
            map.put("opts", new String[]{q.getOptionA(), q.getOptionB(), q.getOptionC(), q.getOptionD()});
            try {
                map.put("ans", Integer.parseInt(q.getCorrectOption())); // Expecting index 0-3
            } catch (NumberFormatException e) {
                map.put("ans", 0); // Default or handle "A", "B"... logic if needed
            }
            map.put("t", q.getTopic());
            response.add(map);
        }
        return response;
    }
}
