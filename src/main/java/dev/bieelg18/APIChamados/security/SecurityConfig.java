package dev.bieelg18.APIChamados.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final NaoAutenticadoHandler naoAutenticadoHandler;
    private final SemPermissaoHandler semPermissaoHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception{
        return configuration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationProvider authenticationProvider
    ) throws Exception{
        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authenticationProvider(authenticationProvider)

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(naoAutenticadoHandler)
                        .accessDeniedHandler(semPermissaoHandler)
                )

                .authorizeHttpRequests(auth -> auth
                        //Rota de cadastro e login, qualquer um pode acessar
                        .requestMatchers(HttpMethod.POST, "/usuarios", "/auth/login")
                        .permitAll()

                        //Rota da documentação, qualquer um pode acessar
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
                        .permitAll()

                        //Usuário autenticado pode editar o próprio cadastro
                        .requestMatchers(HttpMethod.PATCH, "/usuarios/me")
                        .authenticated()

                        //Rotas de usuário que são exclusivas de suporte
                        .requestMatchers(HttpMethod.GET, "/usuarios", "/usuarios/buscar")
                        .hasRole("SUPORTE")

                        .requestMatchers(HttpMethod.PATCH, "/usuarios/permissao/**")
                        .hasRole("SUPORTE")

                        .requestMatchers(HttpMethod.DELETE, "/usuarios/**")
                        .hasRole("SUPORTE")

                        //Rotas de chamados disponiveis para todos que estão autenticados
                        .requestMatchers(HttpMethod.POST, "/chamados")
                        .authenticated()

                        .requestMatchers(HttpMethod.GET, "/chamados/meusChamados")
                        .authenticated()

                        //Todo o restante fica restrito a nivel suporte
                        .requestMatchers("/chamados/**")
                        .hasRole("SUPORTE")


                ) .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

}
