package com.project.userticket.integration;

import com.project.userticket.entity.Usuario;
import com.project.userticket.repository.TicketRepository;
import com.project.userticket.repository.UsuarioRepository;
import com.project.userticket.service.TicketService;
import com.project.userticket.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TicketIntegrationTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        ticketRepository.deleteAll();
        usuarioRepository.deleteAll();
        
        usuario = new Usuario();
        usuario.setNombres("Test");
        usuario.setApellidos("User");
        usuario.setUsername("testuser");
        usuario.setPassword(passwordEncoder.encode("password"));
        usuario = usuarioRepository.save(usuario);
    }

    @Test
    void contextLoads() {
        assertNotNull(usuarioService);
        assertNotNull(ticketService);
        assertNotNull(usuarioRepository);
        assertNotNull(ticketRepository);
    }

    @Test
    void usuarioRepository_DeberiaGuardarYRecuperar() {
        assertNotNull(usuario.getId());
        assertEquals("Test", usuario.getNombres());
        assertEquals("testuser", usuario.getUsername());
        
        Usuario encontrado = usuarioRepository.findByUsername("testuser").orElse(null);
        assertNotNull(encontrado);
        assertEquals(usuario.getId(), encontrado.getId());
    }
}
