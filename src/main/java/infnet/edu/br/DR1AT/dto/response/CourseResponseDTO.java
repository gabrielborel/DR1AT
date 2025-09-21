package infnet.edu.br.DR1AT.dto.response;

import java.util.List;

public record CourseResponseDTO(
        Long id,
        String name,
        String code,
        List<StudentGradeDTO> students
) {}
