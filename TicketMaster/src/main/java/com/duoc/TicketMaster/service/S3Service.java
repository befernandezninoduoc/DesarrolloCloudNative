package com.duoc.TicketMaster.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Servicio encargado de gestionar las operaciones CRUD sobre el bucket de AWS S3
 * para almacenar y administrar los resúmenes y detalles de las compras de tickets.
 */
@Service
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    /**
     * Sube o crea un archivo de texto con el resumen de la compra en el bucket S3.
     * Estructura de la ruta: resumenes/{compraId}/detalle_compra.txt
     */
    public void subirResumen(Long compraId, String contenidoResumen) {
        String fileName = "resumenes/" + compraId + "/detalle_compra.txt";
        byte[] bytes = contenidoResumen.getBytes(StandardCharsets.UTF_8);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(bytes.length);
        metadata.setContentType("text/plain");

        InputStream inputStream = new ByteArrayInputStream(bytes);
        amazonS3.putObject(bucketName, fileName, inputStream, metadata);
    }

    /**
     * Descarga y retorna el flujo de datos (InputStream) del resumen asociado a una compra desde S3.
     */
    public InputStream descargarResumen(Long compraId) {
        String fileName = "resumenes/" + compraId + "/detalle_compra.txt";
        S3Object s3Object = amazonS3.getObject(bucketName, fileName);
        return s3Object.getObjectContent();
    }

    /**
     * Modifica o actualiza el contenido del archivo de resumen de una compra en S3.
     */
    public void modificarResumen(Long compraId, String nuevoContenido) {
        subirResumen(compraId, nuevoContenido);
    }

    /**
     * Elimina el archivo de resumen de una compra específico del bucket S3.
     */
    public void borrarResumen(Long compraId) {
        String fileName = "resumenes/" + compraId + "/detalle_compra.txt";
        amazonS3.deleteObject(bucketName, fileName);
    }
}