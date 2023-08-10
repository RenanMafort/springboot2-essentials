package academy.renansereia.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Log4j2
@RequiredArgsConstructor
public class SecurityConfig{


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            http.csrf(AbstractHttpConfigurer::disable)
//                csrf(crf -> crf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())).formLogin(f -> f.loginPage("/login"))
//                .authorizeHttpRequests((auth) -> auth.requestMatchers("/animes/admin/**").hasRole("ADMIN"))
//                .authorizeHttpRequests((auth) -> auth.requestMatchers("/animes/**").hasRole("USER"))
                    .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService  userDetailsService() throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
       log.info("Password enconder - {}", passwordEncoder.encode("renan1010"));

       UserDetails admin = User.withUsername("adminrenan")
               .password(passwordEncoder.encode("1010"))
               .roles("ADMIN","USER")
               .build();

        UserDetails user = User.withUsername("renan")
                .password(passwordEncoder.encode("1010"))
                .roles("USER")
                .build();


        return new InMemoryUserDetailsManager(admin,user);

    }



}


