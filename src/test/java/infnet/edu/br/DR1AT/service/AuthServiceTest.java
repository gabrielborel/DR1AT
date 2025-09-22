package infnet.edu.br.DR1AT.service;

import infnet.edu.br.DR1AT.dto.request.LoginRequestDTO;
import infnet.edu.br.DR1AT.dto.response.LoginResponseDTO;
import infnet.edu.br.DR1AT.security.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldReturnTokenWhenCredentialsAreValid() {
        LoginRequestDTO req = new LoginRequestDTO("professor", "professor123");
        when(jwtTokenUtil.generateToken("professor")).thenReturn("abc.token");

        LoginResponseDTO resp = authService.login(req);

        assertNotNull(resp);
        assertEquals("abc.token", resp.token());
        assertEquals("Bearer", resp.type());
        assertEquals("professor", resp.username());
        verify(jwtTokenUtil).generateToken("professor");
    }

    @Test
    void shouldThrowExceptionWhenCredentialsAreInvalid() {
        LoginRequestDTO req = new LoginRequestDTO("Usuario 1", "senha123");

        assertThrows(BadCredentialsException.class, () -> authService.login(req));
        verify(jwtTokenUtil, never()).generateToken(any());
    }
}
