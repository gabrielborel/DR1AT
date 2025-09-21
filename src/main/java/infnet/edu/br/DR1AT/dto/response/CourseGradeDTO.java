package infnet.edu.br.DR1AT.dto.response;

import java.math.BigDecimal;

public record CourseGradeDTO(
        Long courseId,
        String courseName,
        String courseCode,
        BigDecimal grade,
        boolean approved
) {}
