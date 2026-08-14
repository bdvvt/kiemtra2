package com.example.duanlon2.validations.handle;


import com.example.duanlon2.validations.annotations.FileExtension;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class FileExtensionValidator implements ConstraintValidator<FileExtension, MultipartFile> {
    private String[] allowedExtensions;

    @Override
    public void initialize(FileExtension constraintAnnotation) {
        allowedExtensions = constraintAnnotation.allowedExtensions();
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty()){
            return true;
        }

        String fileName = value.getOriginalFilename();
        if(fileName == null){
            return false;
        }

        String fileNameLowerCase = fileName.toLowerCase();
        return Arrays.stream(allowedExtensions)
                .anyMatch(fileNameLowerCase::endsWith);
    }


}
