package com.duoc.TicketMaster.controller;

import com.duoc.TicketMaster.model.CompraRespuesta;
import com.duoc.TicketMaster.model.CompraSolicitud;
import com.duoc.TicketMaster.service.CompraService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompraRespuesta comprarEntradas(@Valid @RequestBody CompraSolicitud solicitud) {
        return compraService.comprarEntradas(solicitud);
    }
}
