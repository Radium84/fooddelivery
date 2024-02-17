package jwt;


import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.servlet.http.HttpServletRequest;
import ru.edu.sberbank.jwt.SecurityUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecurityUtilTest {

    @Test
    void convertToAuthority_PrependRolePrefix_WhenAbsent() {
        String role = "USER";
        SimpleGrantedAuthority authority = SecurityUtil.convertToAuthority(role);

        assertEquals("ROLE_USER", authority.getAuthority());
    }

    @Test
    void convertToAuthority_KeepRolePrefix_WhenPresent() {
        String roleWithPrefix = "ROLE_ADMIN";
        SimpleGrantedAuthority authority = SecurityUtil.convertToAuthority(roleWithPrefix);

        assertEquals("ROLE_ADMIN", authority.getAuthority());
    }

    @Test
    void extractAuthTokenFromRequest_ReturnToken_WhenBearerTokenPresent() {
        // Создаем mock объекта HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        // Имитируем поведение, когда в заголовке есть Bearer токен
        when(request.getHeader(SecurityUtil.AUTH_HEADER)).thenReturn("Bearer abcdef123456");

        String token = SecurityUtil.extractAuthTokenFromRequest(request);

        assertEquals("abcdef123456", token);
    }

    @Test
    void extractAuthTokenFromRequest_ReturnNull_WhenBearerTokenAbsent() {
        // Создаем mock объекта HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        // Имитируем поведение, когда заголовок отсутствует
        when(request.getHeader(SecurityUtil.AUTH_HEADER)).thenReturn(null);

        String token = SecurityUtil.extractAuthTokenFromRequest(request);

        assertNull(token);
    }

    @Test
    void extractAuthTokenFromRequest_ReturnNull_WhenTokenDoesNotStartWithBearer() {
        // Создаем mock объекта HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        // Имитируем поведение, когда токен не начинается с "Bearer "
        when(request.getHeader(SecurityUtil.AUTH_HEADER)).thenReturn("SomeOther abcdef123456");

        String token = SecurityUtil.extractAuthTokenFromRequest(request);

        assertNull(token);
    }
}
