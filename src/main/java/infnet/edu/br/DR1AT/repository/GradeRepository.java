package infnet.edu.br.DR1AT.repository;

import infnet.edu.br.DR1AT.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    @Query("SELECT g FROM Grade g WHERE g.course.id = :courseId AND g.grade >= 7.0")
    List<Grade> findApprovedStudentsByCourse(@Param("courseId") Long courseId);

    @Query("SELECT g FROM Grade g WHERE g.course.id = :courseId AND g.grade < 7.0")
    List<Grade> findFailedStudentsByCourse(@Param("courseId") Long courseId);

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    @Query("SELECT g FROM Grade g WHERE g.student.id = :studentId")
    List<Grade> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT g FROM Grade g WHERE g.course.id = :courseId")
    List<Grade> findByCourseId(@Param("courseId") Long courseId);
}
