package infnet.edu.br.DR1AT.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

public record StudentRequestDTO(
        @NotBlank(message = "Name is required")
        String name,

        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11}", message = "Document must have a valid CPF format")
        @NotBlank(message = "Document is required")
        String document,

        @Email(message = "Email must have a valid format")
        @NotBlank(message = "Email is required")
        String email,

        @Pattern(regexp = "\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}", message = "Phone must have a valid format")
        @NotBlank(message = "Phone is required")
        String phone,

        @NotBlank(message = "Address is required")
        String address
) {}
