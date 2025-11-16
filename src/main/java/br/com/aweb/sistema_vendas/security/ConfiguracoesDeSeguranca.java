package br.com.aweb.sistema_vendas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracoesDeSeguranca {

    @Bean
    public SecurityFilterChain filtrosSeguranca(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/login", "/css/**", "/js/**").permitAll();
                    req.requestMatchers("/clientes", "/clientes/novo", "/clientes/editar/**", "/clientes/deletar/**").hasRole("ADMIN");
                    req.requestMatchers("/produtos/novo", "/produtos/editar/**", "/produtos/deletar/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.GET, "/produtos").authenticated();
                    req.requestMatchers("/").authenticated();
                    req.anyRequest().authenticated(); 
                })
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe.key("lembrarDeMim"))
                .build();
    }

    @Bean
    public PasswordEncoder codificadorSenha() {
        return new BCryptPasswordEncoder();
    }
}