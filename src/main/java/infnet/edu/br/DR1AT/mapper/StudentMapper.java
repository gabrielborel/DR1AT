package infnet.edu.br.DR1AT.mapper;

import infnet.edu.br.DR1AT.dto.request.StudentRequestDTO;
import infnet.edu.br.DR1AT.dto.response.CourseGradeDTO;
import infnet.edu.br.DR1AT.dto.response.StudentResponseDTO;
import infnet.edu.br.DR1AT.model.Grade;
import infnet.edu.br.DR1AT.model.Student;
import infnet.edu.br.DR1AT.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentMapper {

    @Autowired
    private GradeRepository gradeRepository;

    public Student toEntity(StudentRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Student student = new Student();
        student.setName(dto.name());
        student.setDocument(dto.document());
        student.setEmail(dto.email());
        student.setPhone(dto.phone());
        student.setAddress(dto.address());

        return student;
    }

    public StudentResponseDTO toResponseDTO(Student entity) {
        if (entity == null) {
            return null;
        }

        List<Grade> studentGrades = gradeRepository.findByStudentId(entity.getId());
        List<CourseGradeDTO> coursesWithGrades = studentGrades.stream()
                .map(grade -> new CourseGradeDTO(
                        grade.getCourse().getId(),
                        grade.getCourse().getName(),
                        grade.getCourse().getCode(),
                        grade.getGrade(),
                        grade.isApproved()
                ))
                .collect(Collectors.toList());

        return new StudentResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getDocument(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getAddress(),
                coursesWithGrades
        );
    }
}
