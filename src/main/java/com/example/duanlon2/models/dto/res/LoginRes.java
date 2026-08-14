package com.example.duanlon2.models.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRes {
    private String accessToken;
    @Builder.Default
    private String type = "Bearer";
    private Set<String> roles;
}
