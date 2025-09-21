package infnet.edu.br.DR1AT.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record GradeRequestDTO(
        @NotNull(message = "Student ID is required")
        Long studentId,

        @NotNull(message = "Course ID is required")
        Long courseId,

        @NotNull(message = "Grade is required")
        @DecimalMin(value = "0.0", message = "Grade must be at least 0.0")
        @DecimalMax(value = "10.0", message = "Grade must be at most 10.0")
        BigDecimal grade
) {}
