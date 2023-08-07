package com.example.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.security.jwt.JwtAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor 
public class SecurityConfig {
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final AuthenticationProvider authenticationProvider;
//	private final LogoutHandler logoutHandler;

//	@Bean
//	public WebSecurityCustomizer webSecurityCustomizer() {
//		return (web) -> web.ignoring()
//				.requestMatchers("/static/**");
//	}

	@Bean
    public SecurityFilterChain FilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.csrf(csrf->csrf.disable())
		        .sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth->auth.requestMatchers("/inter/**").permitAll())
				.authorizeHttpRequests(auth->auth.requestMatchers("/menter/**").permitAll())
				.authorizeHttpRequests(auth->auth.requestMatchers("/demo").permitAll())
		        .authorizeHttpRequests(auth -> auth
		                .requestMatchers("/**").permitAll()
		            )
				.authorizeHttpRequests(auth->auth.anyRequest().permitAll())
	            .authenticationProvider(authenticationProvider)
	            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//	            .formLogin(withDefaults());
//				.logout().deleteCookies("remove").invalidateHttpSession(false)
//				.logoutUrl("/custom-logout").logoutSuccessUrl("/logout-success");
		return httpSecurity.build();
    }


}
