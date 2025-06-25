package com.WishApp.WishApp.services.uploads;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service implements IS3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    private String extractKeyFromUrl(String fileUrl) {
        // Extrae la parte de la key después del dominio de S3
        String prefix = String.format("https://%s.s3.", bucketName);
        if (fileUrl.startsWith(prefix)) {
            return fileUrl.substring(prefix.length()).split("/", 2)[1];
        }
        return fileUrl;
    }

    @Override
    public String uploadFile(MultipartFile file, String keyName) throws IOException {
        // Sube el archivo a S3 y retorna su URL pública
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(keyName)
                        .build(),
                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
        );

        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName, "us-east-1", keyName);  // URL pública del archivo

    }

    @Override
    public void deleteFile(String fileUrl) {
        try {
            // Extraer la key (ruta) del archivo desde la URL
            String key = extractKeyFromUrl(fileUrl);

            // Eliminar el objeto de S3
            s3Client.deleteObject(
                    DeleteObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .build()
            );
        } catch (Exception e) {
            // Puedes lanzar una excepción personalizada o simplemente loggear el error
            throw new RuntimeException("Error al eliminar archivo de S3: " + fileUrl, e);
        }


    }
}
