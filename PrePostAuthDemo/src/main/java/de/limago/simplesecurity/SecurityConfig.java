package de.limago.simplesecurity;


import de.limago.simplesecurity.security.model.User;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity( prePostEnabled = true,securedEnabled = true, jsr250Enabled = true)
//@RequiredArgsConstructor
public class SecurityConfig   {

	//private final UserRepository userRepository;


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
	public UserDetailsService users() {
		UserDetails user = User.builder()
				.username("user")
				.password("$2a$10$OiXOEDV4bV7gxTPNlg3pZOq/H8jmlnCS7DdDcVXSDdvfax/7pWbVa")
				.rolle("USER")
				.build();
		UserDetails admin = User.builder()
				.username("fritz")
				.password("$2a$10$OiXOEDV4bV7gxTPNlg3pZOq/H8jmlnCS7DdDcVXSDdvfax/7pWbVa")
				.rolle("GUEST")
				.build();
		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}






}