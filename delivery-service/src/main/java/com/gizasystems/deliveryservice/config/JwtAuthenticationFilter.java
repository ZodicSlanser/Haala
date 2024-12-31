package com.gizasystems.deliveryservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  // TODO: get this from application.properties
  private String publicKey = "OmarEsawyOHMYGODThisIsTheBestDayOfMyLifeIamSoHappyHaveANiceDayDohaIsMyWifeIamSoHappy";
  
  private SecretKey secretKey;

  JwtAuthenticationFilter() {
    this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(publicKey));
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {

    // Skip JWT processing for /authenticate/** endpoints
    String requestURI = request.getRequestURI();
    if (requestURI.startsWith("/authenticate")) {
      filterChain.doFilter(request, response);
      return;
    }

    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      String jwt = authorizationHeader.substring(7);
      try {
        // TODO: change the deprecated methods

        Claims claims = Jwts.parser()
                 .setSigningKey(secretKey)
                 .build()
                 .parseClaimsJws(jwt)
                 .getBody();
        
        Long userId = claims.get("id", Long.class);
        String role = claims.get("role", String.class);
        
        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null &&
            role != null && role.equals("DELIVERY_PERSON")) {
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, null);
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      } catch (Exception e) {
        // TODO: better error handling
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"error\": \"No or Bad JWT\"}");
        return;
      }
    }
    filterChain.doFilter(request, response);
  }
}
