package com.duoc.TicketMaster.model;

import java.math.BigDecimal;

public record CompraRespuesta(
        String evento,
        int cantidadEntradas,
        BigDecimal precioUnitario,
        BigDecimal precioTotal) {
}
