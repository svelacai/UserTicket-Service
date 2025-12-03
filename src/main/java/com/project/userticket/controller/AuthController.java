package com.project.userticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.userticket.dto.AuthResponseDTO;
import com.project.userticket.dto.LoginRequestDTO;
import com.project.userticket.dto.UsuarioRequestDTO;
import com.project.userticket.dto.UsuarioResponseDTO;
import com.project.userticket.service.AuthService;
import com.project.userticket.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints de autenticación y registro")
public class AuthController {

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AuthService authService;
	@Autowired
	private UsuarioService usuarioService;

	@PostMapping("/login")
	@Operation(summary = "Login", description = "POST /api/auth/login - Autenticar usuario y obtener token JWT")
	public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
		log.info("POST /api/auth/login - Usuario: {}", request.getUsername());
		return ResponseEntity.ok(authService.login(request));
	}

	@PostMapping("/register")
	@Operation(summary = "Registrar Usuario", description = "POST /api/auth/register - Crear nuevo usuario en el sistema")
	public ResponseEntity<UsuarioResponseDTO> register(@Valid @RequestBody UsuarioRequestDTO request) {
		log.info("POST /api/auth/register - Usuario: {}", request.getUsername());
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(request));
	}
}
