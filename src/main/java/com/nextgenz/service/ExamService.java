package com.nextgenz.service;

import com.nextgenz.dto.ExamSubmitRequest;
import com.nextgenz.model.ExamResult;
import com.nextgenz.model.Student;
import com.nextgenz.repository.ExamResultRepository;
import com.nextgenz.repository.StudentRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ExamService {

    private final ExamResultRepository examRepo;
    private final StudentRepository    studentRepo;

    public ExamService(ExamResultRepository examRepo,
                       StudentRepository studentRepo) {
        this.examRepo    = examRepo;
        this.studentRepo = studentRepo;
    }

    // ── Current logged-in student ────────────────────────────
    private Student getCurrentStudent() {
        String username = SecurityContextHolder.getContext()
                            .getAuthentication().getName();
        return studentRepo.findByUsername(username)
                .orElseThrow(() ->
                    new UsernameNotFoundException("Student not found: " + username));
    }

    private boolean isAdmin() {
        return SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    // ── Save result ──────────────────────────────────────────
    @Transactional
    public ExamResult saveResult(ExamSubmitRequest req) {
        Student student = getCurrentStudent();
        ExamResult result = new ExamResult(
                student,
                req.getStudentName(),
                req.getRollNumber(),
                req.getClassLevel(),
                req.getSubject(),
                req.getTotalQuestions(),
                req.getCorrectAnswers(),
                req.getWrongAnswers(),
                req.getSkippedAnswers(),
                req.getScorePercent(),
                req.getGrade(),
                req.getTimeTakenSeconds()
        );
        return examRepo.save(result);
    }

    // ── Student: own results ─────────────────────────────────
    public List<ExamResult> getMyResults() {
        return examRepo.findByStudentOrderBySubmittedAtDesc(getCurrentStudent());
    }

    public long getMyExamCount() {
        return examRepo.countByStudent(getCurrentStudent());
    }

    // ── Fetch single result (owner or admin) ─────────────────
    public Optional<ExamResult> getResultById(Long id) {
        Optional<ExamResult> opt = examRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        if (isAdmin()) return opt;
        Student me = getCurrentStudent();
        if (!opt.get().getStudent().getId().equals(me.getId()))
            return Optional.empty();
        return opt;
    }

    // ── Admin: all results with filters ─────────────────────
    public List<ExamResult> getAllResults(Integer classLevel, String subject,
                                          String rollNumber, String studentName) {
        if (rollNumber != null && !rollNumber.isBlank())
            return examRepo.searchByRollNumber(rollNumber.trim());
        if (studentName != null && !studentName.isBlank())
            return examRepo.searchByStudentName(studentName.trim());
        if (classLevel != null && subject != null && !subject.isBlank())
            return examRepo.findByClassLevelAndSubjectOrderBySubmittedAtDesc(classLevel, subject);
        if (classLevel != null)
            return examRepo.findByClassLevelOrderBySubmittedAtDesc(classLevel);
        if (subject != null && !subject.isBlank())
            return examRepo.findBySubjectOrderBySubmittedAtDesc(subject);
        return examRepo.findAllByOrderBySubmittedAtDesc();
    }

    // ── Admin: dashboard stats ───────────────────────────────
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("totalExams",    examRepo.count());
        stats.put("overallAvg",    round(examRepo.overallAvgScore()));
        stats.put("recentResults", examRepo.findTop10ByOrderBySubmittedAtDesc());
        stats.put("topScorers",    examRepo.findTopScorers().stream().limit(5).toList());

        Map<String, Long> gradeMap = new LinkedHashMap<>();
        for (Object[] r : examRepo.countByGrade())
            gradeMap.put((String) r[0], (Long) r[1]);
        stats.put("gradeDistribution", gradeMap);

        Map<String, Double> subjMap = new LinkedHashMap<>();
        for (Object[] r : examRepo.avgScorePerSubject())
            subjMap.put(label((String) r[0]), round((Double) r[1]));
        stats.put("subjectAverages", subjMap);

        Map<Integer, Long> classMap = new LinkedHashMap<>();
        for (Object[] r : examRepo.countPerClass())
            classMap.put((Integer) r[0], (Long) r[1]);
        stats.put("classDistribution", classMap);

        return stats;
    }

    // ── Admin: delete ────────────────────────────────────────
    @Transactional
    public void deleteResult(Long id) { examRepo.deleteById(id); }

    // ── Helpers ──────────────────────────────────────────────
    private double round(Double v) {
        return v == null ? 0.0 : Math.round(v * 10.0) / 10.0;
    }

    private String label(String s) {
        if (s == null) return "";
        return switch (s.toLowerCase()) {
            case "mathematics" -> "Mathematics";
            case "science"     -> "Science";
            case "english"     -> "English";
            case "hindi"       -> "Hindi";
            case "social"      -> "Social Science";
            case "sanskrit"    -> "Sanskrit";
            case "computer"    -> "Computer Science";
            case "gk"          -> "GK";
            case "evs"         -> "EVS";
            default            -> s;
        };
    }
}
