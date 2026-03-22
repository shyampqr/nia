package com.nextgenz.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ExamSubmitRequest {

    @Getter
    @NotBlank(message = "Student name is required")
    @Size(max = 150)
    private String studentName;

    @Size(max = 50)
    private String rollNumber;

    @Getter
    @NotNull @Min(2) @Max(10)
    private Integer classLevel;

    @Getter
    @NotBlank
    private String subject;

    @Getter
    @NotNull @Min(1) @Max(50)   private Integer totalQuestions;
    @Getter
    @NotNull @Min(0)             private Integer correctAnswers;
    @Getter
    @NotNull @Min(0)             private Integer wrongAnswers;
    @Getter
    @NotNull @Min(0)             private Integer skippedAnswers;
    @Getter
    @NotNull @Min(0) @Max(100)  private Integer scorePercent;
    @Getter
    @NotBlank                    private String  grade;
    @Getter
    @NotNull @Min(0)             private Integer timeTakenSeconds;

    public String  getRollNumber()                 { return rollNumber == null ? "" : rollNumber; }

}
