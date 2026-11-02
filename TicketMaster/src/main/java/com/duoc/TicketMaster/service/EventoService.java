package com.duoc.TicketMaster.service;

import com.duoc.TicketMaster.model.Evento;
import com.duoc.TicketMaster.model.NuevoEventoSolicitud;
import com.duoc.TicketMaster.repository.EventoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public List<Evento> listarEventos() {
        return eventoRepository.findAll();
    }

    public Evento crearEvento(NuevoEventoSolicitud solicitud) {
        Evento evento = new Evento(
                solicitud.nombre(),
                solicitud.fecha(),
                solicitud.precioEntrada());
        return eventoRepository.save(evento);
    }

    public Evento actualizarEvento(Long id, NuevoEventoSolicitud solicitud) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Evento no encontrado"));
        evento.actualizar(
                solicitud.nombre(),
                solicitud.fecha(),
                solicitud.precioEntrada());
        return eventoRepository.save(evento);
    }

    public void eliminarEvento(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Evento no encontrado"));
        eventoRepository.delete(evento);
    }
}
