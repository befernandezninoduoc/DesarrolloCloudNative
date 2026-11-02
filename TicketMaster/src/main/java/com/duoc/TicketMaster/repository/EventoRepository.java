package com.duoc.TicketMaster.repository;

import com.duoc.TicketMaster.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}
