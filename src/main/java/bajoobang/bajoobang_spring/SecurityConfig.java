package bajoobang.bajoobang_spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .logout(logout ->
                        logout.logoutSuccessUrl("/"))
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().authenticated())
                .rememberMe(rememberMe ->
                        rememberMe.rememberMeParameter("remember-me"));

        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        return http.build();
    }
}