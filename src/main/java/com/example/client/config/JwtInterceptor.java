package com.example.client.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final String SECRET_KEY = "GtrJ72mdK3saP9qX48zLA8nQr!Tz7WkXp9BmUz!M";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String token = request.getParameter("token");

            if (token == null) {
                String authHeader = request.getHeader("Authorization");
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7);
                }
            }

            if (token == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT no proporcionado");
                return false;
            }

            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes()) // ¡IMPORTANTE! debe ser misma clave usada en jwt.is
                    .parseClaimsJws(token)
                    .getBody();

            Object edadObj = claims.get("edad");

            if (edadObj == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El token no contiene la edad");
                return false;
            }

            int edad = (edadObj instanceof Integer)
                    ? (Integer) edadObj
                    : Integer.parseInt(edadObj.toString());

            if (edad < 18) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado: menor de edad");
                return false;
            }

            // Si quieres guardar el nombre para usar en el controlador:
            request.setAttribute("nombreUsuario", claims.get("nombre"));

            return true;

        } catch (SignatureException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Firma de token inválida");
            return false;
        } catch (Exception e) {
            e.printStackTrace(); // importante para debug
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar el token");
            return false;
        }
    }
}