package com.nextgenz.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "exam_results", indexes = {
    @Index(name = "idx_student_id", columnList = "student_id"),
    @Index(name = "idx_class",      columnList = "class_level"),
    @Index(name = "idx_subject",    columnList = "subject"),
    @Index(name = "idx_submitted",  columnList = "submitted_at")
})
public class ExamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false, length = 150)
    private String studentName;

    @Column(length = 50)
    private String rollNumber;

    @Column(nullable = false)
    private Integer classLevel;

    @Column(nullable = false, length = 50)
    private String subject;

    @Column(nullable = false)
    private Integer totalQuestions;

    @Column(nullable = false)
    private Integer correctAnswers;

    @Column(nullable = false)
    private Integer wrongAnswers;

    @Column(nullable = false)
    private Integer skippedAnswers;

    @Column(nullable = false)
    private Integer scorePercent;

    @Column(nullable = false, length = 5)
    private String grade;

    @Column(nullable = false)
    private Integer timeTakenSeconds;

    @Column(nullable = false)
    private LocalDateTime submittedAt;

    // ── Constructors ─────────────────────────────────────────
    public ExamResult() {}

    public ExamResult(Student student, String studentName, String rollNumber,
                      Integer classLevel, String subject,
                      Integer totalQuestions, Integer correctAnswers,
                      Integer wrongAnswers, Integer skippedAnswers,
                      Integer scorePercent, String grade,
                      Integer timeTakenSeconds) {
        this.student          = student;
        this.studentName      = studentName;
        this.rollNumber       = rollNumber == null ? "" : rollNumber;
        this.classLevel       = classLevel;
        this.subject          = subject;
        this.totalQuestions   = totalQuestions;
        this.correctAnswers   = correctAnswers;
        this.wrongAnswers     = wrongAnswers;
        this.skippedAnswers   = skippedAnswers;
        this.scorePercent     = scorePercent;
        this.grade            = grade;
        this.timeTakenSeconds = timeTakenSeconds;
        this.submittedAt      = LocalDateTime.now();
    }

    // ── Display helpers ──────────────────────────────────────
    public String getSubjectDisplay() {
        if (subject == null) return "";
        return switch (subject.toLowerCase()) {
            case "mathematics" -> "Mathematics";
            case "science"     -> "Science";
            case "english"     -> "English";
            case "hindi"       -> "Hindi";
            case "social"      -> "Social Science";
            case "sanskrit"    -> "Sanskrit";
            case "computer"    -> "Computer Science";
            case "gk"          -> "General Knowledge";
            case "evs"         -> "EVS";
            default            -> subject;
        };
    }

    public String getSubjectIcon() {
        if (subject == null) return "📝";
        return switch (subject.toLowerCase()) {
            case "mathematics" -> "📐";
            case "science"     -> "🔬";
            case "english"     -> "📖";
            case "hindi"       -> "🇮🇳";
            case "social"      -> "🏛️";
            case "sanskrit"    -> "📜";
            case "computer"    -> "💻";
            case "gk"          -> "🌍";
            case "evs"         -> "🌿";
            default            -> "📝";
        };
    }

    public String getClassDisplay() {
        if (classLevel == null) return "";
        return switch (classLevel) {
            case 2  -> "2nd Class";
            case 3  -> "3rd Class";
            case 4  -> "4th Class";
            case 5  -> "5th Class";
            case 6  -> "6th Class";
            case 7  -> "7th Class";
            case 8  -> "8th Class";
            case 9  -> "9th Class";
            case 10 -> "10th Class";
            default -> "Class " + classLevel;
        };
    }

    public String getTimeTakenDisplay() {
        if (timeTakenSeconds == null) return "—";
        return String.format("%d min %02d sec",
                timeTakenSeconds / 60, timeTakenSeconds % 60);
    }

    public String getSubmittedAtDisplay() {
        if (submittedAt == null) return "—";
        return submittedAt.format(
                DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
    }

    // ── Getters & Setters ────────────────────────────────────
    public Long          getId()                         { return id; }
    public Student       getStudent()                    { return student; }
    public void          setStudent(Student s)           { this.student = s; }
    public String        getStudentName()                { return studentName; }
    public void          setStudentName(String v)        { this.studentName = v; }
    public String        getRollNumber()                 { return rollNumber; }
    public void          setRollNumber(String v)         { this.rollNumber = v; }
    public Integer       getClassLevel()                 { return classLevel; }
    public void          setClassLevel(Integer v)        { this.classLevel = v; }
    public String        getSubject()                    { return subject; }
    public void          setSubject(String v)            { this.subject = v; }
    public Integer       getTotalQuestions()             { return totalQuestions; }
    public void          setTotalQuestions(Integer v)    { this.totalQuestions = v; }
    public Integer       getCorrectAnswers()             { return correctAnswers; }
    public void          setCorrectAnswers(Integer v)    { this.correctAnswers = v; }
    public Integer       getWrongAnswers()               { return wrongAnswers; }
    public void          setWrongAnswers(Integer v)      { this.wrongAnswers = v; }
    public Integer       getSkippedAnswers()             { return skippedAnswers; }
    public void          setSkippedAnswers(Integer v)    { this.skippedAnswers = v; }
    public Integer       getScorePercent()               { return scorePercent; }
    public void          setScorePercent(Integer v)      { this.scorePercent = v; }
    public String        getGrade()                      { return grade; }
    public void          setGrade(String v)              { this.grade = v; }
    public Integer       getTimeTakenSeconds()           { return timeTakenSeconds; }
    public void          setTimeTakenSeconds(Integer v)  { this.timeTakenSeconds = v; }
    public LocalDateTime getSubmittedAt()                { return submittedAt; }
    public void          setSubmittedAt(LocalDateTime v) { this.submittedAt = v; }
}
