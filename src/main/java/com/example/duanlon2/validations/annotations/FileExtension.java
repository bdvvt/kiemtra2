package com.example.duanlon2.validations.annotations;


import com.example.duanlon2.validations.handle.FileExtensionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FileExtensionValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileExtension {
    String message() default "Invalid file extension";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String[] allowedExtensions() default {".jpg",".png",".jpeg",".webp",".mp4"};
}
