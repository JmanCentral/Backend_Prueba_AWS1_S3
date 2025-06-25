package com.WishApp.WishApp.services.uploads;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IS3Service {

    String uploadFile(MultipartFile file, String keyName) throws IOException;

    void deleteFile(String fileUrl);

}
