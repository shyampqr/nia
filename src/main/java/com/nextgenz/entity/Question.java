package com.nextgenz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject; // e.g., "math", "science"
    private String classLevel; // e.g., "5", "10"
    
    @Column(length = 1000)
    private String questionText;
    
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    
    private String correctOption; // 0, 1, 2, 3 (Index based)
    
    private String selectedOption; // Transient field for holding user selection during exam
    
    private String topic; // e.g., "Algebra"

    // Custom constructor for easier object creation (without ID and selectedOption)
    public Question(String subject, String classLevel, String questionText, String optionA, String optionB, String optionC, String optionD, String correctOption, String topic) {
        this.subject = subject;
        this.classLevel = classLevel;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = correctOption;
        this.topic = topic;
    }
}
