package com.example.conecta_hogar.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        System.out.println("\n--- [JWT FILTER INICIO] ---");
        System.out.println("Endpoint invocado: " + request.getMethod() + " " + request.getRequestURI());

        // 1. Si no viene el token o no empieza por "Bearer ", dejamos pasar la petición
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("❌ NO SE ENCONTRÓ HEADER 'Authorization' VÁLIDO (Debe empezar por 'Bearer ')");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extraemos el token quitando el prefijo "Bearer " (7 caracteres)
            jwt = authHeader.substring(7).trim();
            System.out.println("🔑 TOKEN RECIBIDO: " + jwt.substring(0, Math.min(jwt.length(), 20)) + "...");

            userEmail = jwtService.extractUsername(jwt);
            System.out.println("👤 CORREO EXTRAÍDO DEL TOKEN: " + userEmail);

            // Si el token trae un correo y el usuario no está autenticado aún en el contexto
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                System.out.println("📋 ROLES CARGADOS EN SECURITY: " + userDetails.getAuthorities());

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Registramos al usuario autenticado en el contexto de Spring Security
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("✅ ¡AUTENTICACIÓN EXITOSA EN CONTEXTO PARA " + userEmail + "!");
                } else {
                    System.out.println("❌ EL TOKEN NO ES VÁLIDO PARA ESTE USUARIO (jwtService.isTokenValid = false)");
                }
            }
        } catch (Exception e) {
            System.err.println("❌ EXCEPCIÓN AL PROCESAR TOKEN:");
            e.printStackTrace(); // Esto te dirá el error exacto (SignatureException, ExpiredJwtException, etc.)
            SecurityContextHolder.clearContext();
        }

        System.out.println("--- [JWT FILTER FIN] ---\n");
        filterChain.doFilter(request, response);
    }
}