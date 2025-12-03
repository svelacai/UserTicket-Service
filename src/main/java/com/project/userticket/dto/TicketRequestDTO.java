package com.project.userticket.dto;

import com.project.userticket.entity.Ticket.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public class TicketRequestDTO {
	@NotBlank(message = "Descripción es obligatoria")
	@Size(max = 500, message = "Descripción no puede exceder 500 caracteres")
	private String descripcion;

	@NotNull(message = "Usuario ID es obligatorio")
	private UUID usuarioId;

	private TicketStatus status;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public UUID getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(UUID usuarioId) {
		this.usuarioId = usuarioId;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}
}
