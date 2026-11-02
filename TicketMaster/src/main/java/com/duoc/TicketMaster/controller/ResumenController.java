package com.duoc.TicketMaster.controller;

import com.duoc.TicketMaster.service.S3Service;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

/**
 * Controlador REST para gestionar los archivos de resúmenes de compras en AWS S3.
 * Cumple con los requerimientos de subir, descargar, modificar y borrar por ID de compra.
 */
@RestController
@RequestMapping("/api/resumenes")
public class ResumenController {

    private final S3Service s3Service;

    public ResumenController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    /**
     * 1. CREAR / SUBIR RESUMEN (POST)
     * Guarda el archivo en una carpeta dentro del bucket cuyo nombre es el ID de la compra.
     * Ruta en S3: resumenes/{compraId}/detalle_compra.txt
     */
    @PostMapping("/{compraId}")
    public ResponseEntity<String> subirResumen(@PathVariable Long compraId, @RequestBody String contenido) {
        s3Service.subirResumen(compraId, contenido);
        return ResponseEntity.ok("Resumen de la compra " + compraId + " creado y subido a S3 con éxito.");
    }

    /**
     * 2. DESCARGAR RESUMEN (GET)
     * Permite descargar el archivo de texto directamente desde el bucket S3.
     */
    @GetMapping("/{compraId}/descargar")
    public ResponseEntity<InputStreamResource> descargarResumen(@PathVariable Long compraId) {
        InputStream inputStream = s3Service.descargarResumen(compraId);
        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=detalle_compra_" + compraId + ".txt")
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }

    /**
     * 3. MODIFICAR / ACTUALIZAR RESUMEN (PUT)
     * Permite sobreescribir el archivo de resumen en caso de error o actualización.
     */
    @PutMapping("/{compraId}")
    public ResponseEntity<String> modificarResumen(@PathVariable Long compraId, @RequestBody String nuevoContenido) {
        s3Service.modificarResumen(compraId, nuevoContenido);
        return ResponseEntity.ok("Resumen de la compra " + compraId + " actualizado con éxito en S3.");
    }

    /**
     * 4. BORRAR RESUMEN (DELETE)
     * Elimina el archivo de resumen específico del bucket S3.
     */
    @DeleteMapping("/{compraId}")
    public ResponseEntity<String> borrarResumen(@PathVariable Long compraId) {
        s3Service.borrarResumen(compraId);
        return ResponseEntity.ok("Resumen de la compra " + compraId + " eliminado con éxito de S3.");
    }
}