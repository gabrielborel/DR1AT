package infnet.edu.br.DR1AT.dto.response;

import java.util.List;

public record StudentResponseDTO(
        Long id,
        String name,
        String document,
        String email,
        String phone,
        String address,
        List<CourseGradeDTO> coursesWithGrades
) {}
