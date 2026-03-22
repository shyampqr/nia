package com.nextgenz.controller;

import com.nextgenz.dto.ExamSubmitRequest;
import com.nextgenz.model.ExamResult;
import com.nextgenz.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    // ── Student Routes ───────────────────────────────────────

    @GetMapping("/exam")
    public String examPortal(Principal principal, Model model) {
        model.addAttribute("username",    principal.getName());
        model.addAttribute("myExamCount", examService.getMyExamCount());
        return "exam/exam-portal";
    }
    @GetMapping("/exam-form")
    public String examPage() {
        return "exam-form";
    }

    @PostMapping("/exam/submit")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> submitExam(
            @Valid @RequestBody ExamSubmitRequest req,
            BindingResult br) {
        if (br.hasErrors()) {
            String msg = br.getFieldErrors().stream()
                    .map(e -> e.getField() + ": " + e.getDefaultMessage())
                    .findFirst().orElse("Validation failed");
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", msg));
        }
        try {
            ExamResult saved = examService.saveResult(req);
            return ResponseEntity.ok(Map.of(
                    "success",  true,
                    "resultId", saved.getId(),
                    "redirect", "/exam/result/" + saved.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/exam/result/{id}")
    public String viewResult(@PathVariable Long id, Model model) {
        Optional<ExamResult> opt = examService.getResultById(id);
        if (opt.isEmpty()) return "redirect:/exam";
        model.addAttribute("result", opt.get());
        return "exam/exam-result";
    }

    @GetMapping("/exam/my-results")
    public String myResults(Model model) {
        List<ExamResult> results = examService.getMyResults();
        double avg = results.stream()
                .mapToInt(ExamResult::getScorePercent)
                .average().orElse(0.0);
        model.addAttribute("results",   results);
        model.addAttribute("examCount", results.size());
        model.addAttribute("avgScore",  Math.round(avg * 10.0) / 10.0);
        return "exam/my-results";
    }

    // ── Admin Routes ─────────────────────────────────────────

    @GetMapping("/admin/exam-results")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard(
            @RequestParam(required = false) Integer classLevel,
            @RequestParam(required = false) String  subject,
            @RequestParam(required = false) String  rollNumber,
            @RequestParam(required = false) String  studentName,
            Model model) {

        List<ExamResult>    results = examService.getAllResults(
                                        classLevel, subject, rollNumber, studentName);
        Map<String, Object> stats   = examService.getDashboardStats();

        model.addAttribute("results",           results);
        model.addAttribute("totalExams",        stats.get("totalExams"));
        model.addAttribute("overallAvg",        stats.get("overallAvg"));
        model.addAttribute("gradeDistribution", stats.get("gradeDistribution"));
        model.addAttribute("subjectAverages",   stats.get("subjectAverages"));
        model.addAttribute("classDistribution", stats.get("classDistribution"));
        model.addAttribute("topScorers",        stats.get("topScorers"));
        model.addAttribute("recentResults",     stats.get("recentResults"));
        model.addAttribute("filterClass",       classLevel);
        model.addAttribute("filterSubject",     subject);
        model.addAttribute("filterRoll",        rollNumber);
        model.addAttribute("filterName",        studentName);

        return "admin/exam-results";
    }

    @DeleteMapping("/admin/exam-results/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteResult(@PathVariable Long id) {
        try {
            examService.deleteResult(id);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/api/exam/results")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<List<ExamResult>> apiResults(
            @RequestParam(required = false) Integer classLevel,
            @RequestParam(required = false) String  subject) {
        return ResponseEntity.ok(
                examService.getAllResults(classLevel, subject, null, null));
    }
}
