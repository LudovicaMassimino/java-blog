package it.ludo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import it.ludo.repository.UserRepo;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(request -> request

                        .requestMatchers("/home", "/home/**", "/resources/**", "/img/**", "/uploads/**", "/js/**",
                                "/css/**")
                        .permitAll()
                        .requestMatchers("/article/admin/**").hasRole("ADMIN")
                        .requestMatchers("/article/user/**").hasRole("USER")
                        .requestMatchers("/dashboard/admin", "/user/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/article/{id}/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login") // mostra login.html
                        .loginProcessingUrl("/authentication") // Spring gestisce l'autenticazione POST
                        .defaultSuccessUrl("/home") // rimuovi il true
                        .permitAll())

                .httpBasic(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/home")
                        .permitAll())
                // Configura la pagina di errore per accesso negato
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/403"));

        return http.build();
    }

    @Bean
    DatabaseUserDetailsService userDetailsService(UserRepo userRepo) {
        return new DatabaseUserDetailsService(userRepo);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(DatabaseUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
