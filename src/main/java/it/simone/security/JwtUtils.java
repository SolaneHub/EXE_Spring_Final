package it.simone.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import it.simone.exespringfinal.service.UserDetailsImpl;

/* Utility: definisce le funzioni di gestione dei token JWT. 
I parametri precedentemente inseriti in configurazione vengono impiegati 
per generare il token ed impostarne la data di scadenza
*/
@Component
public class JwtUtils {

	// Chiave segreta
	private static String jwtSecret;

	/*
	 * Workaround per accedere ai valori in application.properties tramite @Value
	 * (che non può funzionare con static)
	 */
	@Value("${jwt.secret}")
	public void setStaticSecret(String secret) {
		jwtSecret = secret;
	}

	public static String getStaticSecret() {
		return jwtSecret;
	}

	// Crea chiave segreta
	private static SecretKey getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// Scadenza del token
	private static final int jwtExpirationMs = 60 * 1000 * 60;

	/*
	 * Genera il token Utilizza dati dell'utente, data di scadenza, algoritmo di
	 * cifratura, chiave segreta Authentication: rappresenta il token per una
	 * richiesta di autenticazione o per un'entità autenticata una volta che la
	 * richiesta è stata elaborata dal metodo
	 * AuthenticationManager.authenticate(Authentication)
	 */
	public static String generateJwtToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		Date now = new Date();
		Date exp = new Date((now).getTime() + jwtExpirationMs);
		System.out.println(getSigningKey());
		return Jwts.builder().subject(userPrincipal.getUsername()).issuedAt(now).expiration(exp)
				.signWith(getSigningKey()).compact();
	}

	/*
	 * Valida il token
	 */
	public boolean validateJwtToken(String authToken) {
		// Analizza la stringa JWT
		try {
			// Usa la chiave di firma per verificare i dati (claims) del token
			Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(authToken);
			return true;
		} catch (SignatureException e) {
			System.out.println("Firma JWT non valida: " + e.getMessage());
		} catch (MalformedJwtException e) {
			System.out.println("Token JWT non valido: " + e.getMessage());
		} catch (ExpiredJwtException e) {
			System.out.println("Token scaduto: " + e.getMessage());
		} catch (UnsupportedJwtException e) {
			System.out.println("Token non supportato: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println("Il claims JWT è vuoto: " + e.getMessage());
		}
		return false;
	}

	// Estrae username dal Token
	public String getUserNameFromJwtToken(String authToken) {
		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(authToken).getPayload().getSubject();
	}
}
