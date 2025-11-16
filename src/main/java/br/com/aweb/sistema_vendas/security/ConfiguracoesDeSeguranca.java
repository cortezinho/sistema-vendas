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
                    // Libera acesso à tela de login e aos arquivos estáticos (CSS, JS)
                    req.requestMatchers("/login", "/css/**", "/js/**").permitAll();
                    
                    // Permite que usuários ADMIN vejam e modifiquem (GET, POST)
                    req.requestMatchers("/clientes/novo", "/clientes/editar/**", "/clientes/deletar/**").hasRole("ADMIN");
                    req.requestMatchers("/produtos/novo", "/produtos/editar/**", "/produtos/deletar/**").hasRole("ADMIN");
                    
                    // Permite que usuários autenticados (ADMIN ou PADRAO) vejam as listas (apenas GET)
                    req.requestMatchers(HttpMethod.GET, "/clientes", "/produtos").authenticated();

                    // Regra para a página inicial
                    req.requestMatchers("/").authenticated();
                    
                    // Qualquer outra requisição não mapeada exige autenticação
                    req.anyRequest().authenticated(); 
                })
                .formLogin(form -> form
                        .loginPage("/login") // Define a página de login customizada
                        .defaultSuccessUrl("/") // Redireciona para a home após o login
                        .permitAll() // Permite acesso total à página de login
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout") // URL ao deslogar
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe.key("lembrarDeMim")) // Ativa o "Lembrar de mim"
                .build();
    }

    @Bean
    public PasswordEncoder codificadorSenha() {
        return new BCryptPasswordEncoder();
    }
}