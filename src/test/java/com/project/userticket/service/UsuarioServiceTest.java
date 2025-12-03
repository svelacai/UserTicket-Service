package com.project.userticket.service;

import com.project.userticket.dto.UsuarioRequestDTO;
import com.project.userticket.dto.UsuarioResponseDTO;
import com.project.userticket.entity.Usuario;
import com.project.userticket.exception.ResourceNotFoundException;
import com.project.userticket.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

	@Mock
	private UsuarioRepository usuarioRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UsuarioService usuarioService;

	@Test
	void crearUsuario_DeberiaCrearUsuarioExitosamente() {
		UsuarioRequestDTO request = new UsuarioRequestDTO();
		request.setNombres("Juan");
		request.setApellidos("Pérez");
		request.setUsername("jperez");
		request.setPassword("password123");

		Usuario usuario = new Usuario();
		usuario.setId(UUID.randomUUID());
		usuario.setNombres(request.getNombres());
		usuario.setApellidos(request.getApellidos());
		usuario.setUsername(request.getUsername());

		when(usuarioRepository.existsByUsername(request.getUsername())).thenReturn(false);
		when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
		when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

		UsuarioResponseDTO response = usuarioService.crearUsuario(request);

		assertNotNull(response);
		assertEquals("Juan", response.getNombres());
		verify(usuarioRepository).save(any(Usuario.class));
	}

	@Test
	void crearUsuario_DeberiaLanzarExcepcionCuandoUsernameExiste() {
		UsuarioRequestDTO request = new UsuarioRequestDTO();
		request.setUsername("existente");

		when(usuarioRepository.existsByUsername(request.getUsername())).thenReturn(true);

		assertThrows(IllegalArgumentException.class, () -> usuarioService.crearUsuario(request));
	}

	@Test
	void obtenerPorId_DeberiaRetornarUsuario() {
		UUID id = UUID.randomUUID();
		Usuario usuario = new Usuario();
		usuario.setId(id);
		usuario.setNombres("Juan");

		when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

		UsuarioResponseDTO response = usuarioService.obtenerPorId(id);

		assertNotNull(response);
		assertEquals(id, response.getId());
	}

	@Test
	void obtenerPorId_DeberiaLanzarExcepcionCuandoNoExiste() {
		UUID id = UUID.randomUUID();
		when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> usuarioService.obtenerPorId(id));
	}
}
