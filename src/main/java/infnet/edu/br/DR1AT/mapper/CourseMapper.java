package infnet.edu.br.DR1AT.mapper;

import infnet.edu.br.DR1AT.dto.request.CourseRequestDTO;
import infnet.edu.br.DR1AT.dto.response.CourseResponseDTO;
import infnet.edu.br.DR1AT.dto.response.StudentGradeDTO;
import infnet.edu.br.DR1AT.model.Course;
import infnet.edu.br.DR1AT.model.Grade;
import infnet.edu.br.DR1AT.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    @Autowired
    private GradeRepository gradeRepository;

    public Course toEntity(CourseRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Course course = new Course();
        course.setName(dto.name());
        course.setCode(dto.code());

        return course;
    }

    public CourseResponseDTO toResponseDTO(Course entity) {
        if (entity == null) {
            return null;
        }

        List<Grade> courseGrades = gradeRepository.findByCourseId(entity.getId());
        List<StudentGradeDTO> studentsWithGrades = courseGrades.stream()
                .map(grade -> new StudentGradeDTO(
                        grade.getStudent().getId(),
                        grade.getStudent().getName(),
                        grade.getStudent().getDocument(),
                        grade.getStudent().getEmail(),
                        grade.getGrade(),
                        grade.isApproved()
                ))
                .collect(Collectors.toList());

        return new CourseResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getCode(),
                studentsWithGrades
        );
    }
}
