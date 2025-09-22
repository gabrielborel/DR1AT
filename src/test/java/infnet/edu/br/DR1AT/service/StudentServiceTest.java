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
                "Aluno 1",
                "123.456.789-10",
                "aluno1@email.com",
                "(11) 99999-9999",
                "Rua 1, 123"
        );

        student = new Student();
        student.setId(1L);
        student.setName("Aluno 1");
        student.setDocument("123.456.789-10");
        student.setEmail("aluno1@email.com");
        student.setPhone("(11) 99999-9999");
        student.setAddress("Rua 1, 123");

        studentResponseDTO = new StudentResponseDTO(
                1L,
                "Aluno 1",
                "123.456.789-10",
                "aluno1@email.com",
                "(11) 99999-9999",
                "Rua 1, 123",
                List.of()
        );
    }

    @Test
    void shouldCreateStudentWhenDataIsValid() {
        when(studentRepository.findByDocument(anyString())).thenReturn(Optional.empty());
        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(studentMapper.toEntity(any())).thenReturn(student);
        when(studentRepository.save(any())).thenReturn(student);
        when(studentMapper.toResponseDTO(any())).thenReturn(studentResponseDTO);

        StudentResponseDTO result = studentService.save(studentRequestDTO);

        assertNotNull(result);
        assertEquals("Aluno 1", result.name());
        assertEquals("123.456.789-10", result.document());
    }

    @Test
    void shouldReturnAllStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(student));
        when(studentMapper.toResponseDTO(any())).thenReturn(studentResponseDTO);

        List<StudentResponseDTO> result = studentService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void shouldDeleteStudentWhenStudentExists() {
        when(studentRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> studentService.deleteById(1L));
        verify(studentRepository).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenStudentDoesNotExist() {
        when(studentRepository.existsById(1L)).thenReturn(false);

        assertThrows(StudentNotFoundException.class, () -> studentService.deleteById(1L));
    }
}
