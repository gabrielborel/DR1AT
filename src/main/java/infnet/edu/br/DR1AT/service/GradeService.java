package infnet.edu.br.DR1AT.service;

import infnet.edu.br.DR1AT.dto.request.GradeRequestDTO;
import infnet.edu.br.DR1AT.dto.response.GradeResponseDTO;
import infnet.edu.br.DR1AT.exception.*;
import infnet.edu.br.DR1AT.mapper.GradeMapper;
import infnet.edu.br.DR1AT.model.Course;
import infnet.edu.br.DR1AT.model.Grade;
import infnet.edu.br.DR1AT.model.Student;
import infnet.edu.br.DR1AT.repository.CourseRepository;
import infnet.edu.br.DR1AT.repository.GradeRepository;
import infnet.edu.br.DR1AT.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private GradeMapper gradeMapper;

    public GradeResponseDTO assignGrade(GradeRequestDTO requestDTO) {
        Student student = studentRepository.findById(requestDTO.studentId())
                .orElseThrow(() -> new StudentNotFoundException(requestDTO.studentId()));

        Course course = courseRepository.findById(requestDTO.courseId())
                .orElseThrow(() -> new CourseNotFoundException(requestDTO.courseId()));

        if (gradeRepository.existsByStudentIdAndCourseId(requestDTO.studentId(), requestDTO.courseId())) {
            throw DuplicateGradeException.forStudentAndCourse(requestDTO.studentId(), requestDTO.courseId());
        }

        Grade grade = gradeMapper.toEntity(requestDTO, student, course);
        Grade savedGrade = gradeRepository.save(grade);

        return gradeMapper.toResponseDTO(savedGrade);
    }

    public List<GradeResponseDTO> findApprovedStudentsByCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new CourseNotFoundException(courseId);
        }

        return gradeRepository.findApprovedStudentsByCourse(courseId)
                .stream()
                .map(gradeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<GradeResponseDTO> findFailedStudentsByCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new CourseNotFoundException(courseId);
        }

        return gradeRepository.findFailedStudentsByCourse(courseId)
                .stream()
                .map(gradeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        if (!gradeRepository.existsById(id)) {
            throw new GradeNotFoundException(id);
        }
        gradeRepository.deleteById(id);
    }
}
