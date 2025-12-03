package com.project.userticket.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.userticket.dto.TicketRequestDTO;
import com.project.userticket.dto.TicketResponseDTO;
import com.project.userticket.entity.Ticket.TicketStatus;
import com.project.userticket.service.TicketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/tickets")
@Tag(name = "Tickets", description = "Gestión de tickets")
public class TicketController {

	private static final Logger log = LoggerFactory.getLogger(TicketController.class);

	@Autowired
	private TicketService ticketService;

	@PostMapping
	@Operation(summary = "Crear Ticket", description = "POST /api/tickets - Crear un nuevo ticket")
	public ResponseEntity<TicketResponseDTO> crear(@Valid @RequestBody TicketRequestDTO request) {
		log.info("POST /api/tickets - Crear ticket para usuario: {}", request.getUsuarioId());
		return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.crearTicket(request));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Actualizar Ticket", description = "PUT /api/tickets/{id} - Editar un ticket existente")
	public ResponseEntity<TicketResponseDTO> actualizar(@PathVariable UUID id,
			@Valid @RequestBody TicketRequestDTO request) {
		log.info("PUT /api/tickets/{} - Actualizar ticket", id);
		return ResponseEntity.ok(ticketService.actualizarTicket(id, request));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar Ticket", description = "DELETE /api/tickets/{id} - Eliminar un ticket por ID")
	public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
		log.info("DELETE /api/tickets/{} - Eliminar ticket", id);
		ticketService.eliminarTicket(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener Ticket por ID", description = "GET /api/tickets/{id} - Obtener un ticket específico por ID")
	public ResponseEntity<TicketResponseDTO> obtenerPorId(@PathVariable UUID id) {
		log.info("GET /api/tickets/{} - Obtener ticket por ID", id);
		return ResponseEntity.ok(ticketService.obtenerPorId(id));
	}

	@GetMapping
	@Operation(summary = "Listar Tickets", description = "GET /api/tickets - Obtener lista paginada de tickets con filtros opcionales")
	public ResponseEntity<Page<TicketResponseDTO>> obtenerTodos(@RequestParam(required = false) TicketStatus status,
			@RequestParam(required = false) UUID usuarioId,
			@PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.DESC) Pageable pageable) {
		log.info("GET /api/tickets - Listar tickets con filtros: status={}, usuarioId={}", status, usuarioId);
		return ResponseEntity.ok(ticketService.obtenerTickets(status, usuarioId, pageable));
	}
}
