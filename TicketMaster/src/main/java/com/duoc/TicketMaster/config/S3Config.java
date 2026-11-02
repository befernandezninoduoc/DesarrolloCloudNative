package com.duoc.TicketMaster.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de AWS S3 para instanciar manualmente el cliente AmazonS3
 * utilizando credenciales temporales del entorno de laboratorio de AWS.
 */
@Configuration
public class S3Config {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.credentials.session-token}")
    private String sessionToken;

    @Value("${cloud.aws.region.static}")
    private String region;

    /**
     * Crea, configura y expone el bean de AmazonS3 para inyectarlo en los servicios.
     */
    @Bean
    public AmazonS3 amazonS3() {
        BasicSessionCredentials credentials = new BasicSessionCredentials(accessKey, secretKey, sessionToken);
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}