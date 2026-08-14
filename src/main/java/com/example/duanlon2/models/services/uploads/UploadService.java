package com.example.duanlon2.models.services.uploads;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UploadService {
    private final Cloudinary cloudinary;


    public String upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            String originalFilename = file.getOriginalFilename();

            if(originalFilename != null && originalFilename.contains(".")) {
                originalFilename = originalFilename.substring(0,originalFilename.lastIndexOf("."));
            }

            Map uploadParams = ObjectUtils.asMap(
                    "resource_type", "auto",
                    "public_id",originalFilename
            );

            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),uploadParams);
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
