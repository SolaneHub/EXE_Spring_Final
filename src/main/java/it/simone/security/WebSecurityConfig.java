package it.simone.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import it.simone.exespringfinal.service.UserDetailsServiceImpl;

/* Spring Security
La sicurezza delle applicazioni si riduce a due problemi più o meno indipendenti: 
- l'autenticazione (chi sei?)
- l'autorizzazione (cosa puoi fare?)
Spring Security si concentra sulla fornitura di autenticazione e autorizzazione alle applicazioni Java.
Caratteristiche
- Supporto completo ed estensibile sia per l'autenticazione che per l'autorizzazione
- Protezione contro attacchi come session fixation, clickjacking, cross site request forgery, ecc.
- Integrazione API servlet
- Integrazione con Spring Web MVC
*/

/* WebSecurityConfig 
Tramite @Configuration e @EnableWebSecurity, abilita le funzioni di sicurezza e ne configura i parametri
- Istruisce il sistema sul service (nel nostro caso userDetailsService) da impiegare per manipolare i dati dello user 
- Registra il filter (nel nostro caso AuthTokenFilter) che ha il compito di estrarre e processare il token JWT dalle request
- Definisce l'algoritmo di encoding delle password
- Configura la sicurezza HTTP
*/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointUnauthorizedJwt unauthorizedHandler;

	// Bean della classe AuthTokenFilter
	@Bean
	AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	// Definisce il metodo di encoding delle password
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * AuthenticationManager L'interfaccia principale della strategia per
	 * l'autenticazione in Spring Un AuthenticationManager può fare una delle 3 cose
	 * nel suo metodo authenticate(): - Restituire un'autenticazione (normalmente
	 * con authenticated=true) se può verificare che l'input rappresenti un'entità
	 * valida - Generare un'eccezione AuthenticationException se ritiene che l'input
	 * rappresenti un'entità non valida - Restituire null se non può decidere
	 */
	// Espone come Bean l'AuthenticationManager in modo che sia disponibile come
	// autowired nel controller REST
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		/*
		 * AuthenticationManagerBuilder Spring Security fornisce alcuni helper di
		 * configurazione per ottenere rapidamente le funzioni comuni di gestione
		 * dell'autenticazione configurate nell'applicazione. L'helper più comunemente
		 * utilizzato è AuthenticationManagerBuilder, utile per impostare i dettagli
		 * utente
		 */
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		return authenticationManagerBuilder.build();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
		// Definisce i criteri di protezione HTTP
		return http
				// Esempio abilitazione e disabilitazione filtri predefiniti per CORS E CSRF
				.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())
				/*
				 * Imposta la policy delle Session: - ALWAYS: crea sempre una HttpSession -
				 * IF_REQUIRED: crea una HttpSession solo se necessario - NEVER: non crea una
				 * HttpSession, ma può utilizzarne una se esiste già - STATELESS: non crea una
				 * HttpSession e non la utilizzerà per ottenere SecurityContext
				 */
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// Definisce la classe che gestisce gli accessi non autorizzati
				.exceptionHandling(eh -> eh.authenticationEntryPoint(unauthorizedHandler))
				// Imposta i permessi sugli endpoints
				.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/api/signup/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/login/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/public/**").permitAll()
						/*
						 * Alternativa a @PreAuthorize .requestMatchers(HttpMethod.GET,
						 * "/api/admin").hasRole("ADMIN") .requestMatchers(HttpMethod.GET,
						 * "/api/user").hasAnyRole("ADMIN","USER")
						 */
						// Definisce che ogni request deve essere autenticata
						.anyRequest().authenticated())
				.authenticationManager(authenticationManager)
				/*
				 * addFilterBefore(): consente di aggiungere un filtro prima di una classe
				 * filtro del framework In questo caso aggiunge il filtro di autenticazione JWT
				 * prima del filtro UsernamePasswordAuthenticationFilter
				 */
				.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class).build();
	}

}