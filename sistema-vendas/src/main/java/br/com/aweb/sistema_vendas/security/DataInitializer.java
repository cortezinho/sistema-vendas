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
        // Verifica se o usuário admin@email.com já existe
        if (usuarioRepository.findByEmailIgnoreCase("admin@email.com").isEmpty()) {
            
            // Se não existir, cria um novo usuário
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setEmail("admin@email.com");
            // Codifica a senha antes de salvar
            admin.setSenha(passwordEncoder.encode("admin")); 
            admin.setRole(UsuarioRole.ADMIN);
            
            usuarioRepository.save(admin);
        }
    }
}