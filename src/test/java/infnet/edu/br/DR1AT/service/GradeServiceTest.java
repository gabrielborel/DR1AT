package infnet.edu.br.DR1AT.service;

import infnet.edu.br.DR1AT.dto.request.GradeRequestDTO;
import infnet.edu.br.DR1AT.dto.response.GradeResponseDTO;
import infnet.edu.br.DR1AT.exception.CourseNotFoundException;
import infnet.edu.br.DR1AT.exception.DuplicateGradeException;
import infnet.edu.br.DR1AT.exception.GradeNotFoundException;
import infnet.edu.br.DR1AT.exception.StudentNotFoundException;
import infnet.edu.br.DR1AT.mapper.GradeMapper;
import infnet.edu.br.DR1AT.model.Course;
import infnet.edu.br.DR1AT.model.Grade;
import infnet.edu.br.DR1AT.model.Student;
import infnet.edu.br.DR1AT.repository.CourseRepository;
import infnet.edu.br.DR1AT.repository.GradeRepository;
import infnet.edu.br.DR1AT.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GradeServiceTest {

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private GradeMapper gradeMapper;

    @InjectMocks
    private GradeService gradeService;

    private GradeRequestDTO gradeRequestDTO;
    private Student student;
    private Course course;
    private Grade grade;
    private GradeResponseDTO gradeResponseDTO;

    @BeforeEach
    void setUp() {
        gradeRequestDTO = new GradeRequestDTO(
                1L,
                1L,
                new BigDecimal("8.5")
        );

        student = new Student();
        student.setId(1L);
        student.setName("Aluno 1");

        course = new Course();
        course.setId(1L);
        course.setName("Curso 1");
        course.setCode("CURSO01");

        grade = new Grade();
        grade.setId(1L);
        grade.setStudent(student);
        grade.setCourse(course);
        grade.setGrade(new BigDecimal("8.5"));

        gradeResponseDTO = new GradeResponseDTO(
                1L, 1L, "Aluno 1", 1L, "Curso 1", "CURSO01",
                new BigDecimal("8.5"), true
        );
    }

    @Test
    void shouldCreateGradeWhenDataIsValid() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(gradeRepository.existsByStudentIdAndCourseId(1L, 1L)).thenReturn(false);
        when(gradeMapper.toEntity(any(), any(), any())).thenReturn(grade);
        when(gradeRepository.save(any())).thenReturn(grade);
        when(gradeMapper.toResponseDTO(any())).thenReturn(gradeResponseDTO);

        GradeResponseDTO result = gradeService.assignGrade(gradeRequestDTO);

        assertNotNull(result);
        assertEquals(new BigDecimal("8.5"), result.grade());
        assertTrue(result.approved());
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> gradeService.assignGrade(gradeRequestDTO));
    }

    @Test
    void shouldThrowExceptionWhenCourseNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> gradeService.assignGrade(gradeRequestDTO));
    }

    @Test
    void shouldThrowExceptionWhenGradeAlreadyExists() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(gradeRepository.existsByStudentIdAndCourseId(1L, 1L)).thenReturn(true);

        assertThrows(DuplicateGradeException.class, () -> gradeService.assignGrade(gradeRequestDTO));
    }

    @Test
    void shouldReturnApprovedStudentsWhenCourseExists() {
        when(courseRepository.existsById(1L)).thenReturn(true);
        when(gradeRepository.findApprovedStudentsByCourse(1L)).thenReturn(List.of(grade));
        when(gradeMapper.toResponseDTO(any())).thenReturn(gradeResponseDTO);

        List<GradeResponseDTO> result = gradeService.findApprovedStudentsByCourse(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).approved());
    }

    @Test
    void shouldThrowExceptionWhenCourseNotFoundForApproved() {
        when(courseRepository.existsById(1L)).thenReturn(false);

        assertThrows(CourseNotFoundException.class, () -> gradeService.findApprovedStudentsByCourse(1L));
        verify(gradeRepository, never()).findApprovedStudentsByCourse(any());
    }

    @Test
    void shouldReturnFailedStudentsWhenCourseExists() {
        Grade failedGrade = new Grade();
        failedGrade.setId(2L);
        failedGrade.setStudent(student);
        failedGrade.setCourse(course);
        failedGrade.setGrade(new BigDecimal("5.0"));

        GradeResponseDTO failedGradeResponse = new GradeResponseDTO(
                2L, 1L, "Aluno 1", 1L, "Curso 1", "CURSO01",
                new BigDecimal("5.0"), false
        );

        List<Grade> failedGrades = List.of(failedGrade);
        when(courseRepository.existsById(1L)).thenReturn(true);
        when(gradeRepository.findFailedStudentsByCourse(1L)).thenReturn(failedGrades);
        when(gradeMapper.toResponseDTO(failedGrade)).thenReturn(failedGradeResponse);

        List<GradeResponseDTO> result = gradeService.findFailedStudentsByCourse(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(failedGradeResponse.id(), result.get(0).id());
        assertFalse(result.get(0).approved());
        verify(gradeRepository).findFailedStudentsByCourse(1L);
    }

    @Test
    void shouldThrowExceptionWhenCourseNotFoundForFailed() {
        when(courseRepository.existsById(1L)).thenReturn(false);

        assertThrows(CourseNotFoundException.class, () -> gradeService.findFailedStudentsByCourse(1L));
        verify(gradeRepository, never()).findFailedStudentsByCourse(any());
    }

    @Test
    void shouldDeleteGradeWhenGradeExists() {
        when(gradeRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> gradeService.deleteById(1L));
        verify(gradeRepository).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenGradeDoesNotExist() {
        when(gradeRepository.existsById(1L)).thenReturn(false);

        assertThrows(GradeNotFoundException.class, () -> gradeService.deleteById(1L));
    }
}
