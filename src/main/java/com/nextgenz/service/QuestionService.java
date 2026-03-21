package com.nextgenz.service;

import com.nextgenz.entity.Question;
import com.nextgenz.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getQuestionsForExam(String classLevel, String subject, int count) {
        List<Question> questions = questionRepository.findByClassLevelAndSubject(classLevel, subject);
        Collections.shuffle(questions);
        return questions.size() > count ? questions.subList(0, count) : questions;
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
