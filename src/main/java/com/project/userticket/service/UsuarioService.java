package com.project.userticket.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.userticket.dto.UsuarioRequestDTO;
import com.project.userticket.dto.UsuarioResponseDTO;
import com.project.userticket.entity.Usuario;
import com.project.userticket.exception.ResourceNotFoundException;
import com.project.userticket.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO request) {
		if (usuarioRepository.existsByUsername(request.getUsername())) {
			throw new IllegalArgumentException("El username ya existe");
		}

		Usuario usuario = new Usuario();
		usuario.setNombres(request.getNombres());
		usuario.setApellidos(request.getApellidos());
		usuario.setUsername(request.getUsername());
		usuario.setPassword(passwordEncoder.encode(request.getPassword()));

		return toDTO(usuarioRepository.save(usuario));
	}

	@Transactional
	public UsuarioResponseDTO actualizarUsuario(UUID id, UsuarioRequestDTO request) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

		if (!usuario.getUsername().equals(request.getUsername())
				&& usuarioRepository.existsByUsername(request.getUsername())) {
			throw new IllegalArgumentException("El username ya existe");
		}

		usuario.setNombres(request.getNombres());
		usuario.setApellidos(request.getApellidos());
		usuario.setUsername(request.getUsername());
		if (request.getPassword() != null && !request.getPassword().isEmpty()) {
			usuario.setPassword(passwordEncoder.encode(request.getPassword()));
		}

		return toDTO(usuarioRepository.save(usuario));
	}

	@Transactional(readOnly = true)
	public List<UsuarioResponseDTO> obtenerTodos() {
		return usuarioRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public UsuarioResponseDTO obtenerPorId(UUID id) {
		return usuarioRepository.findById(id).map(this::toDTO)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
	}

	private UsuarioResponseDTO toDTO(Usuario usuario) {
		UsuarioResponseDTO dto = new UsuarioResponseDTO();
		dto.setId(usuario.getId());
		dto.setNombres(usuario.getNombres());
		dto.setApellidos(usuario.getApellidos());
		dto.setUsername(usuario.getUsername());
		dto.setFechaCreacion(usuario.getFechaCreacion());
		dto.setFechaActualizacion(usuario.getFechaActualizacion());
		return dto;
	}
}
