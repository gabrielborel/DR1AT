package infnet.edu.br.DR1AT.dto.response;

import java.math.BigDecimal;

public record GradeResponseDTO(
        Long id,
        Long studentId,
        String studentName,
        Long courseId,
        String courseName,
        String courseCode,
        BigDecimal grade,
        boolean approved
) {}
