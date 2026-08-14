package com.example.duanlon2.configs;

import com.example.duanlon2.models.constants.CourseStatus;
import com.example.duanlon2.models.constants.UserStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCourseStatusConverter implements Converter<String, CourseStatus> {
    @Override
    public CourseStatus convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        return CourseStatus.valueOf(source.trim().toUpperCase());
    }
}
