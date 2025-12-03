package com.project.userticket.service;

import com.project.userticket.dto.TicketRequestDTO;
import com.project.userticket.dto.TicketResponseDTO;
import com.project.userticket.entity.Ticket;
import com.project.userticket.entity.Ticket.TicketStatus;
import com.project.userticket.entity.Usuario;
import com.project.userticket.exception.ResourceNotFoundException;
import com.project.userticket.repository.TicketRepository;
import com.project.userticket.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

	@Mock
	private TicketRepository ticketRepository;

	@Mock
	private UsuarioRepository usuarioRepository;

	@InjectMocks
	private TicketService ticketService;

	@Test
	void crearTicket_DeberiaCrearTicketExitosamente() {
		UUID usuarioId = UUID.randomUUID();
		TicketRequestDTO request = new TicketRequestDTO();
		request.setDescripcion("Problema con el sistema");
		request.setUsuarioId(usuarioId);

		Usuario usuario = new Usuario();
		usuario.setId(usuarioId);
		usuario.setNombres("Juan");
		usuario.setApellidos("Pérez");

		Ticket ticket = new Ticket();
		ticket.setId(UUID.randomUUID());
		ticket.setDescripcion(request.getDescripcion());
		ticket.setUsuario(usuario);
		ticket.setStatus(TicketStatus.ABIERTO);

		when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
		when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

		TicketResponseDTO response = ticketService.crearTicket(request);

		assertNotNull(response);
		assertEquals("Problema con el sistema", response.getDescripcion());
		assertEquals(TicketStatus.ABIERTO, response.getStatus());
		verify(ticketRepository).save(any(Ticket.class));
	}

	@Test
	void crearTicket_DeberiaLanzarExcepcionCuandoUsuarioNoExiste() {
		UUID usuarioId = UUID.randomUUID();
		TicketRequestDTO request = new TicketRequestDTO();
		request.setUsuarioId(usuarioId);

		when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> ticketService.crearTicket(request));
	}

	@Test
	void eliminarTicket_DeberiaEliminarExitosamente() {
		UUID id = UUID.randomUUID();
		when(ticketRepository.existsById(id)).thenReturn(true);

		ticketService.eliminarTicket(id);

		verify(ticketRepository).deleteById(id);
	}

	@Test
	void eliminarTicket_DeberiaLanzarExcepcionCuandoNoExiste() {
		UUID id = UUID.randomUUID();
		when(ticketRepository.existsById(id)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> ticketService.eliminarTicket(id));
	}
}
