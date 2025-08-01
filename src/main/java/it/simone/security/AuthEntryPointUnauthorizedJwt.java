package it.simone.security;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/* AuthEntryPointUnauthorizedJwt
Gestisce lo scenario di richiesta non autorizzata
*/
@Component
public class AuthEntryPointUnauthorizedJwt implements AuthenticationEntryPoint {

	//private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointUnauthorizedJwt.class);
	
	// Modifica le intestazioni su ServletResponse come necessario
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		System.out.println("Unauthorized error: {}" + authException.getMessage());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
	}
}
