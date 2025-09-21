package infnet.edu.br.DR1AT.service;

import infnet.edu.br.DR1AT.dto.request.LoginRequestDTO;
import infnet.edu.br.DR1AT.dto.response.LoginResponseDTO;
import infnet.edu.br.DR1AT.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final String FIXED_USERNAME = "professor";
    private static final String FIXED_PASSWORD = "professor123";

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        if (!FIXED_USERNAME.equals(loginRequest.username()) ||
            !FIXED_PASSWORD.equals(loginRequest.password())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        String token = jwtTokenUtil.generateToken(loginRequest.username());

        return new LoginResponseDTO(token, "Bearer", loginRequest.username());
    }
}
