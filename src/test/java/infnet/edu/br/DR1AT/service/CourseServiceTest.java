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
                "Java Programming",
                "JAVA101"
        );

        course = new Course();
        course.setId(1L);
        course.setName("Java Programming");
        course.setCode("JAVA101");

        courseResponseDTO = new CourseResponseDTO(
                1L,
                "Java Programming",
                "JAVA101",
                List.of()
        );
    }

    @Test
    void save_ShouldCreateCourse_WhenValidData() {
        // Given
        when(courseRepository.findByCode(anyString())).thenReturn(Optional.empty());
        when(courseMapper.toEntity(any())).thenReturn(course);
        when(courseRepository.save(any())).thenReturn(course);
        when(courseMapper.toResponseDTO(any())).thenReturn(courseResponseDTO);

        // When
        CourseResponseDTO result = courseService.save(courseRequestDTO);

        // Then
        assertNotNull(result);
        assertEquals("Java Programming", result.name());
        assertEquals("JAVA101", result.code());
    }

    @Test
    void save_ShouldThrowException_WhenCodeAlreadyExists() {
        // Given
        when(courseRepository.findByCode(courseRequestDTO.code())).thenReturn(Optional.of(course));

        // When & Then
        assertThrows(DuplicateCourseException.class, () -> courseService.save(courseRequestDTO));
        verify(courseRepository, never()).save(any());
    }

    @Test
    void findAll_ShouldReturnListOfCourses() {
        // Given
        when(courseRepository.findAll()).thenReturn(List.of(course));
        when(courseMapper.toResponseDTO(any())).thenReturn(courseResponseDTO);

        // When
        List<CourseResponseDTO> result = courseService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void deleteById_ShouldDeleteCourse_WhenCourseExists() {
        // Given
        when(courseRepository.existsById(1L)).thenReturn(true);

        // When & Then
        assertDoesNotThrow(() -> courseService.deleteById(1L));
        verify(courseRepository).deleteById(1L);
    }

    @Test
    void deleteById_ShouldThrowException_WhenCourseNotExists() {
        // Given
        when(courseRepository.existsById(1L)).thenReturn(false);

        // When & Then
        assertThrows(CourseNotFoundException.class, () -> courseService.deleteById(1L));
    }
}
