package DClab.CRUD_Learn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // 로그인, 회원가입 페이지는 누구나 접근 가능
                        .requestMatchers("/login-page", "/join-page", "/join", "/login", "/error").permitAll()
                        // 메인페이지(/)와 다른 모든 페이지는 인증 필요
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login-page")           // 커스텀 로그인 페이지
                        .loginProcessingUrl("/login")       // 로그인 처리 URL
                        .defaultSuccessUrl("/", true)       // 로그인 성공 시 메인페이지로 이동
                        .failureUrl("/login-page?error=true") // 로그인 실패 시
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")               // 로그아웃 URL
                        .logoutSuccessUrl("/login-page")    // 로그아웃 성공 시 로그인 페이지로
                        .invalidateHttpSession(true)       // 세션 무효화
                        .deleteCookies("JSESSIONID")        // 쿠키 삭제
                        .permitAll()
                );
        return http.build();
    }
}
