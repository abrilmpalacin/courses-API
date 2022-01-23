package com.ada.finalproject.courses.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";
	private final String SECRET = "myUltraSecretKey";
	
	@Autowired
	AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER);

        if (header == null) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = authenticate(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken authenticate(HttpServletRequest request) {
        String token = request.getHeader(HEADER).replace(PREFIX, "");
        		
        Claims claims = Jwts.parser()
        					.setSigningKey(SECRET.getBytes())
        					.parseClaimsJws(token.replaceAll("\"", ""))
        					.getBody();
        
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();		
		int length = claims.get("authorities").toString().length();
		String role = claims.get("authorities").toString().substring(1, length - 1);		
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
		authorities.add(authority);
		
		return new UsernamePasswordAuthenticationToken(claims, null, authorities);
    }
}
