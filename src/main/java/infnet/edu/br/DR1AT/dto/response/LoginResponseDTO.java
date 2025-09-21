package infnet.edu.br.DR1AT.dto.response;

public record LoginResponseDTO(
        String token,
        String type,
        String username
) {}
