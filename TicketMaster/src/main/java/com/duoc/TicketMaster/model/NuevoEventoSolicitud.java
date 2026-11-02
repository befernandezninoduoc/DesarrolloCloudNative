package com.duoc.TicketMaster.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record NuevoEventoSolicitud(
        @NotBlank String nombre,
        @NotNull LocalDateTime fecha,
        @NotNull @DecimalMin("0.01") BigDecimal precioEntrada) {
}
