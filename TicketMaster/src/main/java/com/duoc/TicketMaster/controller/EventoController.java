package com.duoc.TicketMaster.controller;

import com.duoc.TicketMaster.model.Evento;
import com.duoc.TicketMaster.model.NuevoEventoSolicitud;
import com.duoc.TicketMaster.service.EventoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public List<Evento> listarEventos() {
        return eventoService.listarEventos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Evento crearEvento(@Valid @RequestBody NuevoEventoSolicitud solicitud) {
        return eventoService.crearEvento(solicitud);
    }

    @PutMapping("/{id}")
    public Evento actualizarEvento(
            @PathVariable Long id,
            @Valid @RequestBody NuevoEventoSolicitud solicitud) {
        return eventoService.actualizarEvento(id, solicitud);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarEvento(@PathVariable Long id) {
        eventoService.eliminarEvento(id);
    }
}
