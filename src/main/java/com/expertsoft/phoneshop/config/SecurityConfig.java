package com.expertsoft.phoneshop.config;

import com.expertsoft.phoneshop.persistence.model.AppProperties;
import com.expertsoft.phoneshop.persistence.model.PhoneshopUser;
import com.expertsoft.phoneshop.persistence.model.Role;
import com.expertsoft.phoneshop.persistence.model.RoleType;
import com.expertsoft.phoneshop.persistence.repository.PhoneshopUserRepository;
import com.expertsoft.phoneshop.service.PhoneshopOAuth2UserService;
import com.expertsoft.phoneshop.service.PhoneshopUserDetailsService;
import jakarta.servlet.DispatcherType;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final PhoneshopOAuth2UserService phoneshopOAuth2UserService;
    private final AppProperties appProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authReqCustomize -> authReqCustomize
                        .requestMatchers(mvc.pattern("/actuator/health"),
                                mvc.pattern("/"),
                                mvc.pattern("/phones**"),
                                mvc.pattern("/phones/searchByForm/**"),
                                mvc.pattern("/error**"),
                                mvc.pattern("/css/phoneshop.css"),
                                mvc.pattern("/webjars/bootstrap/css/bootstrap.min.css"),
                                mvc.pattern("/webjars/**"),
                                mvc.pattern("/login"),
                                mvc.pattern("/logout"),
                                mvc.pattern("/user"),
                                mvc.pattern("/oauth2/**"),
                                mvc.pattern("/auth/**"),
                                mvc.pattern("/login/signin")
                        ).permitAll()
                        .requestMatchers(
                                mvc.pattern("/admin")
                        ).hasRole("ADMIN")
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                        .requestMatchers(mvc.pattern("/phones/{id}")).authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(loginConfigurer -> loginConfigurer
                        .loginPage("/login")
                        .loginProcessingUrl("/signin")
                        .permitAll()
                )
                .logout(logoutCustomize -> logoutCustomize
                        .permitAll()
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(false)
                        .logoutSuccessUrl("/phones")
                        .logoutSuccessHandler(((request, response, authentication) -> response.sendRedirect("/")))
                )
                .oauth2Login(oauth2Customizer -> oauth2Customizer
                        .loginPage("/login").permitAll()
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(phoneshopOAuth2UserService)
                        )
                );

        return http.build();
    }

    @Scope("prototype")
    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PhoneshopUserDetailsService phoneshopUserDetailsService(PhoneshopUserRepository phoneshopUserRepository) {
        var adminPhoneshopUser = getSuperAdminPhoneshopUserBasedOnConfigPropertiesSuperAdmin(appProperties.getSuperAdmin());
        phoneshopUserRepository.save(adminPhoneshopUser);
        return new PhoneshopUserDetailsService(phoneshopUserRepository);
    }

    private PhoneshopUser getSuperAdminPhoneshopUserBasedOnConfigPropertiesSuperAdmin(AppProperties.SuperAdmin superAdmin) {
        PhoneshopUser adminPhoneshopUser = new PhoneshopUser();
        adminPhoneshopUser.setName(superAdmin.getName());
        adminPhoneshopUser.setEmail(superAdmin.getEmail());
        adminPhoneshopUser.setPassword(this.passwordEncoder().encode(superAdmin.getPassword()));
        adminPhoneshopUser.setCompany(superAdmin.getCompany());
        adminPhoneshopUser.setLocation(superAdmin.getLocation());
        Role adminRole = new Role();
        adminRole.setRoleType(RoleType.ADMIN);
        adminPhoneshopUser.setRole(adminRole);
        adminPhoneshopUser.setCreatedAt(LocalDateTime.now());
        adminPhoneshopUser.setUpdatedAt(LocalDateTime.now());
        return adminPhoneshopUser;
    }

}
