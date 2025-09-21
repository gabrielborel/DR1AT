package infnet.edu.br.DR1AT.mapper;

import infnet.edu.br.DR1AT.dto.request.GradeRequestDTO;
import infnet.edu.br.DR1AT.dto.response.GradeResponseDTO;
import infnet.edu.br.DR1AT.model.Course;
import infnet.edu.br.DR1AT.model.Grade;
import infnet.edu.br.DR1AT.model.Student;
import org.springframework.stereotype.Component;

@Component
public class GradeMapper {

    public Grade toEntity(GradeRequestDTO dto, Student student, Course course) {
        if (dto == null || student == null || course == null) {
            return null;
        }

        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setCourse(course);
        grade.setGrade(dto.grade());

        return grade;
    }

    public GradeResponseDTO toResponseDTO(Grade entity) {
        if (entity == null) {
            return null;
        }

        return new GradeResponseDTO(
                entity.getId(),
                entity.getStudent().getId(),
                entity.getStudent().getName(),
                entity.getCourse().getId(),
                entity.getCourse().getName(),
                entity.getCourse().getCode(),
                entity.getGrade(),
                entity.isApproved()
        );
    }
}
