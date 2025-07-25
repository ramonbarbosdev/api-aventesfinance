package com.api_aventesfinance.security;

import java.io.IOException;
import java.security.Principal;
import java.security.SignatureException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.api_aventesfinance.ApplicationContextLoad;
import com.api_aventesfinance.model.Usuario;
import com.api_aventesfinance.repository.UsuarioRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JWTTokenAutenticacaoService {

    private static final long EXPIRATION_TIME = 172800000; // 2 dias
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";

    @Value("${server.servlet.context-path}")
    private String CHAVE_COOKIE;

    private static final String SECRET_KEY_BASE64 = "HaqrDaAaICtFZNXjm5Q3dPNgAZX+bnf6efMy2HuIO1Iq928rcmtTltoAFhsROHxNwtcHjB6FWudgjqxBMXAP8w==";

    public static SecretKeySpec createSecretKey() {
        String cleanedKey = SECRET_KEY_BASE64.replaceAll("\\s", "");
        byte[] decodedKey = java.util.Base64.getDecoder().decode(cleanedKey);
        return new SecretKeySpec(decodedKey, "HmacSHA512");
    }

    public String addAuthentication(HttpServletResponse response, String username) throws Exception {
        SecretKeySpec secretKey = createSecretKey();

        // Recupere o objeto do usuário
        Usuario usuario = ApplicationContextLoad.getApplicationContext()
                .getBean(UsuarioRepository.class)
                .findUserByLogin(username);

        String jwt = Jwts.builder()
                .setSubject(username)
                .claim("id_usuario", usuario.getId())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        String token = TOKEN_PREFIX + jwt;
        response.addHeader(HEADER_STRING, token);

        ApplicationContextLoad.getApplicationContext()
                .getBean(UsuarioRepository.class)
                .atualizarTokenUser(jwt, username);

        inserirJwtCookie(jwt, response);
        liberacaoCors(response);

        // response.setContentType("application/json");
        // response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
        return token;
    }

    public Long decodeUserId(String token) {
        SecretKeySpec secretKey = createSecretKey();

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id_usuario", Long.class);
    }

    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        SecretKeySpec secretKey = createSecretKey();
        String token = request.getHeader(HEADER_STRING);

        String cookieToken = obterTokenCookie(request);

        if (cookieToken != null && !cookieToken.isEmpty()) {
            token = TOKEN_PREFIX + cookieToken;
        } else {
            token = request.getHeader(HEADER_STRING);
        }

        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            String jwt = token.replace(TOKEN_PREFIX, "").trim();

            try {
                String user = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody()
                        .getSubject();

                if (user != null) {
                    Usuario usuario = ApplicationContextLoad.getApplicationContext()
                            .getBean(UsuarioRepository.class)
                            .findUserByLogin(user);

                    if (usuario != null && jwt.equalsIgnoreCase(usuario.getToken())) {
                        return new UsernamePasswordAuthenticationToken(
                                usuario.getLogin(),
                                usuario.getSenha(),
                                usuario.getAuthorities());
                    }
                }

            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                try {
                    response.getWriter().write("{\"error\": \"Token expirado.\"}");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (MalformedJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                try {
                    response.getWriter().write("{\"error\": \"Token malformado.\"}");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                try {
                    response.getWriter().write("{\"error\": \"Erro na autenticação: " + e.getMessage() + "\"}");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }

        liberacaoCors(response);
        return null;
    }

    private void inserirJwtCookie(String jwt, HttpServletResponse response) {
        StringBuilder cookieValue = new StringBuilder();
        cookieValue.append("access_token=").append(jwt)
                .append("; Path=").append(CHAVE_COOKIE)
                .append("; HttpOnly")
                .append("; Secure") // ⚠️ obrigatório em produção (HTTPS)
                .append("; SameSite=None") // ⚠️ obrigatório para cross-origin
                .append("; Max-Age=3600"); // 1 hora

        response.addHeader("Set-Cookie", cookieValue.toString());
    }

    public String obterTokenCookie(HttpServletRequest request) {

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("access_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void liberacaoCors(HttpServletResponse response) {
        if (response.getHeader("Access-Control-Allow-Origin") == null) {
            response.addHeader("Access-Control-Allow-Origin", "*");
        }

        if (response.getHeader("Access-Control-Allow-Headers") == null) {
            response.addHeader("Access-Control-Allow-Headers", "*");
        }

        if (response.getHeader("Access-Control-Request-Headers") == null) {
            response.addHeader("Access-Control-Request-Headers", "*");
        }

        if (response.getHeader("Access-Control-Allow-Methods") == null) {
            response.addHeader("Access-Control-Allow-Methods", "*");
        }
    }
}
