package infnet.edu.br.DR1AT.controller;

import infnet.edu.br.DR1AT.dto.request.LoginRequestDTO;
import infnet.edu.br.DR1AT.dto.response.LoginResponseDTO;
import infnet.edu.br.DR1AT.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO response = authService.login(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
