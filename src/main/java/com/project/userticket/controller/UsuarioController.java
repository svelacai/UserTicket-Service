package com.project.userticket.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.userticket.dto.UsuarioRequestDTO;
import com.project.userticket.dto.UsuarioResponseDTO;
import com.project.userticket.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Gestión de usuarios")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar Usuarios", description = "GET /api/usuarios - Obtener listado de todos los usuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerTodos() {
        log.info("GET /api/usuarios - Listar todos los usuarios");
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener Usuario por ID", description = "GET /api/usuarios/{id} - Obtener usuario por ID")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(@PathVariable UUID id) {
        log.info("GET /api/usuarios/{} - Obtener usuario por ID", id);
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Usuario", description = "PUT /api/usuarios/{id} - Actualizar datos de un usuario")
    public ResponseEntity<UsuarioResponseDTO> actualizar(@PathVariable UUID id, @Valid @RequestBody UsuarioRequestDTO request) {
        log.info("PUT /api/usuarios/{} - Actualizar usuario", id);
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, request));
    }
}
