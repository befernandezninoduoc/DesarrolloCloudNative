package com.duoc.TicketMaster;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.duoc.TicketMaster.repository.CompraRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class TicketMasterApplicationTests {

	@Autowired
	private MockMvc solicitudes;

	@Autowired
	private CompraRepository compraRepository;

	@Test
	void permiteActualizarYEliminarEventoPorId() throws Exception {
		MvcResult respuestaEvento = solicitudes.perform(post("/api/eventos")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
									"nombre": "Evento original",
									"fecha": "2026-12-15T20:00:00",
									"precioEntrada": 15000.00
								}
								"""))
				.andExpect(status().isCreated())
				.andReturn();

		Number eventoId = JsonPath.read(
				respuestaEvento.getResponse().getContentAsString(), "$.id");
		String rutaEvento = "/api/eventos/" + eventoId.longValue();

		solicitudes.perform(put(rutaEvento)
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
									"nombre": "Evento actualizado",
									"fecha": "2026-12-16T21:00:00",
									"precioEntrada": 20000.00
								}
								"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(eventoId.intValue()))
				.andExpect(jsonPath("$.nombre").value("Evento actualizado"))
				.andExpect(jsonPath("$.precioEntrada").value(20000.00));

		solicitudes.perform(delete(rutaEvento))
				.andExpect(status().isNoContent());

		solicitudes.perform(delete(rutaEvento))
				.andExpect(status().isNotFound());
	}

	@Test
	void permiteCrearListarEventosYComprarEntradas() throws Exception {
		MvcResult respuestaEvento = solicitudes.perform(post("/api/eventos")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
									"nombre": "Concierto",
									"fecha": "2026-12-15T20:00:00",
									"precioEntrada": 15000.00
								}
								"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nombre").value("Concierto"))
				.andReturn();

		Number eventoId = JsonPath.read(
				respuestaEvento.getResponse().getContentAsString(), "$.id");

		solicitudes.perform(get("/api/eventos"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nombre").value("Concierto"))
				.andExpect(jsonPath("$[0].precioEntrada").value(15000.00));

		solicitudes.perform(post("/api/compras")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
									"eventoId": %d,
									"cantidad": 2
								}
								""".formatted(eventoId.longValue())))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.evento").value("Concierto"))
				.andExpect(jsonPath("$.cantidadEntradas").value(2))
				.andExpect(jsonPath("$.precioUnitario").value(15000.00))
				.andExpect(jsonPath("$.precioTotal").value(30000.00));

		org.assertj.core.api.Assertions.assertThat(compraRepository.count()).isEqualTo(1);
	}
}
