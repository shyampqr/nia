package com.nextgenz.service;

import com.nextgenz.entity.Question;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {

    private final List<Question> allQuestions = new ArrayList<>();

    public ExamService() {
        // Sample Math Questions
        allQuestions.add(new Question(1, "Math", "What is 2 + 2?", "3", "4", "5", "6", "B"));
        allQuestions.add(new Question(2, "Math", "What is the square root of 16?", "2", "4", "6", "8", "B"));
        allQuestions.add(new Question(3, "Math", "What is 10 * 5?", "45", "50", "55", "60", "B"));
        allQuestions.add(new Question(4, "Math", "What is the value of Pi (to 2 decimal places)?", "3.12", "3.14", "3.16", "3.18", "B"));
        allQuestions.add(new Question(5, "Math", "What is 18 / 3?", "5", "6", "7", "8", "B"));

        // Sample Science Questions
        allQuestions.add(new Question(6, "Science", "What is the chemical symbol for water?", "H2O", "O2", "CO2", "NaCl", "A"));
        allQuestions.add(new Question(7, "Science", "Which planet is known as the Red Planet?", "Earth", "Mars", "Jupiter", "Venus", "B"));
        allQuestions.add(new Question(8, "Science", "What is the powerhouse of the cell?", "Nucleus", "Ribosome", "Mitochondrion", "Chloroplast", "C"));
        allQuestions.add(new Question(9, "Science", "What force pulls objects towards the center of the Earth?", "Magnetism", "Friction", "Gravity", "Tension", "C"));
        allQuestions.add(new Question(10, "Science", "What is the hardest natural substance on Earth?", "Gold", "Iron", "Diamond", "Quartz", "C"));
    }

    public List<Question> getQuestionsForExam(String subject, int count) {
        List<Question> filteredQuestions = allQuestions.stream()
                .filter(q -> q.getSubject().equalsIgnoreCase(subject))
                .collect(Collectors.toList());
        
        Collections.shuffle(filteredQuestions);
        
        return filteredQuestions.stream().limit(count).collect(Collectors.toList());
    }

    public int calculateScore(List<Question> questions) {
        int score = 0;
        for (Question question : questions) {
            if (question.getSelectedOption() != null && question.getSelectedOption().equals(question.getCorrectOption())) {
                score++;
            }
        }
        return score;
    }
}
