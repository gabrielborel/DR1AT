package infnet.edu.br.DR1AT.service;

import infnet.edu.br.DR1AT.dto.request.CourseRequestDTO;
import infnet.edu.br.DR1AT.dto.response.CourseResponseDTO;
import infnet.edu.br.DR1AT.exception.DuplicateCourseException;
import infnet.edu.br.DR1AT.mapper.CourseMapper;
import infnet.edu.br.DR1AT.model.Course;
import infnet.edu.br.DR1AT.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    public CourseResponseDTO save(CourseRequestDTO requestDTO) {
        validateUniqueCode(requestDTO);

        Course course = courseMapper.toEntity(requestDTO);
        Course savedCourse = courseRepository.save(course);

        return courseMapper.toResponseDTO(savedCourse);
    }

    public List<CourseResponseDTO> findAll() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new infnet.edu.br.DR1AT.exception.CourseNotFoundException(id);
        }
        courseRepository.deleteById(id);
    }

    private void validateUniqueCode(CourseRequestDTO requestDTO) {
        Optional<Course> existingByCode = courseRepository.findByCode(requestDTO.code());
        if (existingByCode.isPresent()) {
            throw DuplicateCourseException.forCode(requestDTO.code());
        }
    }
}
