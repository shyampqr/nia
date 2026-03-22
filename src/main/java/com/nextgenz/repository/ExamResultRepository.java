package com.nextgenz.repository;

import com.nextgenz.model.ExamResult;
import com.nextgenz.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

    // ── Student ──────────────────────────────────────────────
    List<ExamResult> findByStudentOrderBySubmittedAtDesc(Student student);
    long             countByStudent(Student student);

    // ── Admin filters ────────────────────────────────────────
    List<ExamResult> findAllByOrderBySubmittedAtDesc();
    List<ExamResult> findByClassLevelOrderBySubmittedAtDesc(Integer classLevel);
    List<ExamResult> findBySubjectOrderBySubmittedAtDesc(String subject);
    List<ExamResult> findByClassLevelAndSubjectOrderBySubmittedAtDesc(
            Integer classLevel, String subject);

    @Query("SELECT e FROM ExamResult e WHERE LOWER(e.rollNumber) LIKE LOWER(CONCAT('%',:q,'%')) ORDER BY e.submittedAt DESC")
    List<ExamResult> searchByRollNumber(@Param("q") String q);

    @Query("SELECT e FROM ExamResult e WHERE LOWER(e.studentName) LIKE LOWER(CONCAT('%',:q,'%')) ORDER BY e.submittedAt DESC")
    List<ExamResult> searchByStudentName(@Param("q") String q);

    // ── Stats ────────────────────────────────────────────────
    @Query("SELECT AVG(e.scorePercent) FROM ExamResult e")
    Double overallAvgScore();

    @Query("SELECT e.grade, COUNT(e) FROM ExamResult e GROUP BY e.grade ORDER BY e.grade")
    List<Object[]> countByGrade();

    @Query("SELECT e.subject, AVG(e.scorePercent) FROM ExamResult e GROUP BY e.subject ORDER BY e.subject")
    List<Object[]> avgScorePerSubject();

    @Query("SELECT e.classLevel, COUNT(e) FROM ExamResult e GROUP BY e.classLevel ORDER BY e.classLevel")
    List<Object[]> countPerClass();

    List<ExamResult> findTop10ByOrderBySubmittedAtDesc();

    @Query("SELECT e FROM ExamResult e ORDER BY e.scorePercent DESC, e.submittedAt DESC")
    List<ExamResult> findTopScorers();
}
