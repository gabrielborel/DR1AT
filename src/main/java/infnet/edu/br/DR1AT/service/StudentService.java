package infnet.edu.br.DR1AT.service;

import infnet.edu.br.DR1AT.dto.request.StudentRequestDTO;
import infnet.edu.br.DR1AT.dto.response.StudentResponseDTO;
import infnet.edu.br.DR1AT.exception.CourseNotFoundException;
import infnet.edu.br.DR1AT.exception.DuplicateStudentException;
import infnet.edu.br.DR1AT.exception.StudentNotFoundException;
import infnet.edu.br.DR1AT.mapper.StudentMapper;
import infnet.edu.br.DR1AT.model.Course;
import infnet.edu.br.DR1AT.model.Student;
import infnet.edu.br.DR1AT.repository.CourseRepository;
import infnet.edu.br.DR1AT.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentMapper studentMapper;

    public StudentResponseDTO save(StudentRequestDTO requestDTO) {
        validateUniqueConstraints(requestDTO);

        Student student = studentMapper.toEntity(requestDTO);
        Student savedStudent = studentRepository.save(student);

        return studentMapper.toResponseDTO(savedStudent);
    }

    public List<StudentResponseDTO> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void enrollInCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        if (!student.getCourses().contains(course)) {
            student.getCourses().add(course);
            studentRepository.save(student);
        }
    }

    public void deleteById(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }

    private void validateUniqueConstraints(StudentRequestDTO requestDTO) {
        if (studentRepository.findByDocument(requestDTO.document()).isPresent()) {
            throw DuplicateStudentException.forDocument(requestDTO.document());
        }

        if (studentRepository.findByEmail(requestDTO.email()).isPresent()) {
            throw DuplicateStudentException.forEmail(requestDTO.email());
        }
    }
}
