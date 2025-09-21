package infnet.edu.br.DR1AT.dto.response;

import java.math.BigDecimal;

public record StudentGradeDTO(
        Long studentId,
        String studentName,
        String studentDocument,
        String studentEmail,
        BigDecimal grade,
        boolean approved
) {}
