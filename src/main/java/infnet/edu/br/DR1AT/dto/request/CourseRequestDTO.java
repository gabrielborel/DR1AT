package infnet.edu.br.DR1AT.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CourseRequestDTO(
        @NotBlank(message = "Course name is required")
        String name,

        @NotBlank(message = "Course code is required")
        String code
) {}
