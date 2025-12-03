package com.project.userticket.repository;

import com.project.userticket.entity.Ticket;
import com.project.userticket.entity.Ticket.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
	Page<Ticket> findByStatus(TicketStatus status, Pageable pageable);

	Page<Ticket> findByUsuarioId(UUID usuarioId, Pageable pageable);

	Page<Ticket> findByStatusAndUsuarioId(TicketStatus status, UUID usuarioId, Pageable pageable);

	@Query("SELECT t FROM Ticket t JOIN FETCH t.usuario WHERE t.usuario.id = :usuarioId")
	Page<Ticket> findByUsuarioIdWithUsuario(UUID usuarioId, Pageable pageable);
}
