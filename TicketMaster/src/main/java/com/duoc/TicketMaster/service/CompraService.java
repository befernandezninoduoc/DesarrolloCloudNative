package com.duoc.TicketMaster.service;

import com.duoc.TicketMaster.model.Compra;
import com.duoc.TicketMaster.model.CompraRespuesta;
import com.duoc.TicketMaster.model.CompraSolicitud;
import com.duoc.TicketMaster.model.Evento;
import com.duoc.TicketMaster.repository.CompraRepository;
import com.duoc.TicketMaster.repository.EventoRepository;
import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final EventoRepository eventoRepository;

    public CompraService(CompraRepository compraRepository, EventoRepository eventoRepository) {
        this.compraRepository = compraRepository;
        this.eventoRepository = eventoRepository;
    }

    @Transactional
    public CompraRespuesta comprarEntradas(CompraSolicitud solicitud) {
        Evento evento = eventoRepository.findById(solicitud.eventoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Evento no encontrado"));
        BigDecimal precioUnitario = evento.getPrecioEntrada();
        BigDecimal precioTotal = precioUnitario.multiply(BigDecimal.valueOf(solicitud.cantidad()));

        compraRepository.save(new Compra(
                evento,
                solicitud.cantidad(),
                precioUnitario,
                precioTotal));

        return new CompraRespuesta(
                evento.getNombre(),
                solicitud.cantidad(),
                precioUnitario,
                precioTotal);
    }
}
