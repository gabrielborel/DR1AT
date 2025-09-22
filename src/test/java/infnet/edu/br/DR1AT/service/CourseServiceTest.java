package infnet.edu.br.DR1AT.service;

import infnet.edu.br.DR1AT.dto.request.CourseRequestDTO;
import infnet.edu.br.DR1AT.dto.response.CourseResponseDTO;
import infnet.edu.br.DR1AT.exception.CourseNotFoundException;
import infnet.edu.br.DR1AT.exception.DuplicateCourseException;
import infnet.edu.br.DR1AT.mapper.CourseMapper;
import infnet.edu.br.DR1AT.model.Course;
import infnet.edu.br.DR1AT.repository.CourseRepository;
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
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseService courseService;

    private CourseRequestDTO courseRequestDTO;
    private Course course;
    private CourseResponseDTO courseResponseDTO;

    @BeforeEach
    void setUp() {
        courseRequestDTO = new CourseRequestDTO(
                "Curso 1",
                "CURSO01"
        );

        course = new Course();
        course.setId(1L);
        course.setName("Curso 1");
        course.setCode("CURSO01");

        courseResponseDTO = new CourseResponseDTO(
                1L,
                "Curso 1",
                "CURSO01",
                List.of()
        );
    }

    @Test
    void shouldCreateCourseWhenDataIsValid() {
        when(courseRepository.findByCode(anyString())).thenReturn(Optional.empty());
        when(courseMapper.toEntity(any())).thenReturn(course);
        when(courseRepository.save(any())).thenReturn(course);
        when(courseMapper.toResponseDTO(any())).thenReturn(courseResponseDTO);

        CourseResponseDTO result = courseService.save(courseRequestDTO);

        assertNotNull(result);
        assertEquals("Curso 1", result.name());
        assertEquals("CURSO01", result.code());
    }

    @Test
    void shouldThrowExceptionWhenCodeAlreadyExists() {
        when(courseRepository.findByCode(courseRequestDTO.code())).thenReturn(Optional.of(course));

        assertThrows(DuplicateCourseException.class, () -> courseService.save(courseRequestDTO));
        verify(courseRepository, never()).save(any());
    }

    @Test
    void shouldReturnAllCourses() {
        when(courseRepository.findAll()).thenReturn(List.of(course));
        when(courseMapper.toResponseDTO(any())).thenReturn(courseResponseDTO);

        List<CourseResponseDTO> result = courseService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void shouldDeleteCourseWhenCourseExists() {
        when(courseRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> courseService.deleteById(1L));
        verify(courseRepository).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenCourseDoesNotExist() {
        when(courseRepository.existsById(1L)).thenReturn(false);

        assertThrows(CourseNotFoundException.class, () -> courseService.deleteById(1L));
    }
}
