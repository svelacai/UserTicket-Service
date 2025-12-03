package com.project.userticket.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.userticket.dto.TicketRequestDTO;
import com.project.userticket.dto.TicketResponseDTO;
import com.project.userticket.entity.Ticket;
import com.project.userticket.entity.Ticket.TicketStatus;
import com.project.userticket.entity.Usuario;
import com.project.userticket.exception.ResourceNotFoundException;
import com.project.userticket.repository.TicketRepository;
import com.project.userticket.repository.UsuarioRepository;

@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional
	@CacheEvict(value = "ticketsByUser", key = "#request.usuarioId")
	public TicketResponseDTO crearTicket(TicketRequestDTO request) {
		Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

		Ticket ticket = new Ticket();
		ticket.setDescripcion(request.getDescripcion());
		ticket.setUsuario(usuario);
		ticket.setStatus(request.getStatus() != null ? request.getStatus() : TicketStatus.ABIERTO);

		return toDTO(ticketRepository.save(ticket));
	}

	@Transactional
	@CacheEvict(value = "ticketsByUser", key = "#result.usuarioId")
	public TicketResponseDTO actualizarTicket(UUID id, TicketRequestDTO request) {
		Ticket ticket = ticketRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));

		ticket.setDescripcion(request.getDescripcion());
		if (request.getStatus() != null) {
			ticket.setStatus(request.getStatus());
		}

		return toDTO(ticketRepository.save(ticket));
	}

	@Transactional
	@CacheEvict(value = "ticketsByUser", allEntries = true)
	public void eliminarTicket(UUID id) {
		if (!ticketRepository.existsById(id)) {
			throw new ResourceNotFoundException("Ticket no encontrado");
		}
		ticketRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public TicketResponseDTO obtenerPorId(UUID id) {
		return ticketRepository.findById(id).map(this::toDTO)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
	}

	@Transactional(readOnly = true)
	public Page<TicketResponseDTO> obtenerTickets(TicketStatus status, UUID usuarioId, Pageable pageable) {
		Page<Ticket> tickets;

		if (status != null && usuarioId != null) {
			tickets = ticketRepository.findByStatusAndUsuarioId(status, usuarioId, pageable);
		} else if (status != null) {
			tickets = ticketRepository.findByStatus(status, pageable);
		} else if (usuarioId != null) {
			tickets = obtenerTicketsPorUsuario(usuarioId, pageable);
		} else {
			tickets = ticketRepository.findAll(pageable);
		}

		return tickets.map(this::toDTO);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "ticketsByUser", key = "#usuarioId")
	public Page<Ticket> obtenerTicketsPorUsuario(UUID usuarioId, Pageable pageable) {
		return ticketRepository.findByUsuarioId(usuarioId, pageable);
	}

	private TicketResponseDTO toDTO(Ticket ticket) {
		TicketResponseDTO dto = new TicketResponseDTO();
		dto.setId(ticket.getId());
		dto.setDescripcion(ticket.getDescripcion());
		dto.setUsuarioId(ticket.getUsuario().getId());
		dto.setUsuarioNombre(ticket.getUsuario().getNombres() + " " + ticket.getUsuario().getApellidos());
		dto.setStatus(ticket.getStatus());
		dto.setFechaCreacion(ticket.getFechaCreacion());
		dto.setFechaActualizacion(ticket.getFechaActualizacion());
		return dto;
	}
}
