package de.limago.simplesecurity;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.access.intercept.RunAsManager;
import org.springframework.security.access.intercept.RunAsManagerImpl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity( securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig   {

	private final UserRepository userRepository;


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(requests -> requests
						.requestMatchers("/", "/home").permitAll()
						.requestMatchers("/unsecure/*").permitAll()
						.requestMatchers("/secure/*").hasAnyRole("USER", "GUEST")
						.anyRequest().authenticated()
				)
				.formLogin(form -> form
						.loginPage("/login")
						.permitAll()
				)
				.logout(LogoutConfigurer::permitAll);



		return http.build();
	}


		@Bean
		public UserDetailsService userDetailsService() {
			return username -> userRepository.findById(username)
					.orElseThrow(() -> new UsernameNotFoundException(username));
		}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}



		@Bean
		public RunAsManager runAsManager() {
			final RunAsManagerImpl runAsManager = new RunAsManagerImpl();
			runAsManager.setKey("MyRunAsKey");
			return runAsManager;
		}



		/*
			Ein AuthenticationProvider in Spring Boot ist verantwortlich für die Überprüfung von Anmeldeinformationen und die Bereitstellung einer Authentifizierung für eine Anwendung. Wenn ein Benutzer sich anmeldet, nimmt der AuthenticationManager die Anmeldeinformationen entgegen und ruft verschiedene AuthenticationProvider auf, um die Authentizität der bereitgestellten Anmeldeinformationen zu überprüfen.

Jeder AuthenticationProvider kann verschiedene Arten der Authentifizierung unterstützen, wie zum Beispiel die Überprüfung von Benutzername und Passwort gegen eine Datenbank, die Verwendung von Tokens oder die Integration mit externen Diensten wie LDAP oder OAuth.

Im Grunde genommen implementiert ein AuthenticationProvider die authenticate()-Methode, um zu überprüfen, ob die bereitgestellten Anmeldeinformationen gültig sind. Wenn die Überprüfung erfolgreich ist, wird ein Authentication-Objekt zurückgegeben, das die Authentifizierungsinformationen des Benutzers enthält.


		@Bean
		public AuthenticationProvider runAsAuthenticationProvider() {
			final RunAsImplAuthenticationProvider authProvider = new RunAsImplAuthenticationProvider();

			authProvider.setKey("MyRunAsKey");
			return authProvider;
		}

	 */


}