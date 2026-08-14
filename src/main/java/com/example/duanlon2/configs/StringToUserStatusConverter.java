package com.example.duanlon2.configs;

import com.example.duanlon2.models.constants.UserStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToUserStatusConverter implements Converter<String, UserStatus> {
    @Override
    public UserStatus convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        return UserStatus.valueOf(source.trim().toUpperCase());
    }
}
