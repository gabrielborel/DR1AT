package infnet.edu.br.DR1AT.service;

import infnet.edu.br.DR1AT.dto.request.StudentRequestDTO;
import infnet.edu.br.DR1AT.dto.response.StudentResponseDTO;
import infnet.edu.br.DR1AT.exception.StudentNotFoundException;
import infnet.edu.br.DR1AT.mapper.StudentMapper;
import infnet.edu.br.DR1AT.model.Student;
import infnet.edu.br.DR1AT.repository.CourseRepository;
import infnet.edu.br.DR1AT.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentService studentService;

    private StudentRequestDTO studentRequestDTO;
    private Student student;
    private StudentResponseDTO studentResponseDTO;

    @BeforeEach
    void setUp() {
        studentRequestDTO = new StudentRequestDTO(
                "John Doe",
                "123.456.789-10",
                "john@example.com",
                "(11) 99999-9999",
                "123 Main St"
        );

        student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setDocument("123.456.789-10");
        student.setEmail("john@example.com");
        student.setPhone("(11) 99999-9999");
        student.setAddress("123 Main St");

        studentResponseDTO = new StudentResponseDTO(
                1L,
                "John Doe",
                "123.456.789-10",
                "john@example.com",
                "(11) 99999-9999",
                "123 Main St",
                List.of()
        );
    }

    @Test
    void save_ShouldCreateStudent_WhenValidData() {
        // Given
        when(studentRepository.findByDocument(anyString())).thenReturn(Optional.empty());
        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(studentMapper.toEntity(any())).thenReturn(student);
        when(studentRepository.save(any())).thenReturn(student);
        when(studentMapper.toResponseDTO(any())).thenReturn(studentResponseDTO);

        // When
        StudentResponseDTO result = studentService.save(studentRequestDTO);

        // Then
        assertNotNull(result);
        assertEquals("John Doe", result.name());
        assertEquals("123.456.789-10", result.document());
    }

    @Test
    void findAll_ShouldReturnListOfStudents() {
        // Given
        when(studentRepository.findAll()).thenReturn(List.of(student));
        when(studentMapper.toResponseDTO(any())).thenReturn(studentResponseDTO);

        // When
        List<StudentResponseDTO> result = studentService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void deleteById_ShouldDeleteStudent_WhenStudentExists() {
        // Given
        when(studentRepository.existsById(1L)).thenReturn(true);

        // When & Then
        assertDoesNotThrow(() -> studentService.deleteById(1L));
        verify(studentRepository).deleteById(1L);
    }

    @Test
    void deleteById_ShouldThrowException_WhenStudentNotExists() {
        // Given
        when(studentRepository.existsById(1L)).thenReturn(false);

        // When & Then
        assertThrows(StudentNotFoundException.class, () -> studentService.deleteById(1L));
    }
}
