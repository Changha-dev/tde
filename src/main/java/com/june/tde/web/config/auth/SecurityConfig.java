package com.june.tde.web.config.auth;


import com.june.tde.web.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() //2
                .and()
                .authorizeHttpRequests() //3
                .requestMatchers("/","/css/**","/images/**","/js/**","/h2-console/**").permitAll()
                .requestMatchers("/api/v1/**").hasRole(Role.USER.name()) //4
                .anyRequest().authenticated()//5
                .and()
                .logout()
                .logoutSuccessUrl("/")//6
                .and()
                .oauth2Login()//7
                .userInfoEndpoint()//8
                .userService(customOAuth2UserService);//9

        return http.build();
    }

}
