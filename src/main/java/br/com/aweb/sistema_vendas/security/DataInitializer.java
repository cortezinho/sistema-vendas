package br.com.aweb.sistema_vendas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.aweb.sistema_vendas.model.Usuario;
import br.com.aweb.sistema_vendas.model.UsuarioRole;
import br.com.aweb.sistema_vendas.repository.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        
        // --- Cria o usuário ADMIN ---
        if (usuarioRepository.findByEmailIgnoreCase("admin@email.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setEmail("admin@email.com");
            admin.setSenha(passwordEncoder.encode("admin")); 
            admin.setRole(UsuarioRole.ADMIN);
            
            usuarioRepository.save(admin);
        }

        // --- ADICIONADO: Cria o usuário PADRAO ---
        if (usuarioRepository.findByEmailIgnoreCase("user@email.com").isEmpty()) {
            Usuario user = new Usuario();
            user.setNome("Usuário Padrão");
            user.setEmail("user@email.com");
            user.setSenha(passwordEncoder.encode("user")); // Senha é "user"
            user.setRole(UsuarioRole.PADRAO);
            
            usuarioRepository.save(user);
        }
    }
}