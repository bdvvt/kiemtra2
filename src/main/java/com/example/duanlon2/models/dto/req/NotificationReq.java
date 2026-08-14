package com.example.duanlon2.models.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationReq {
    @NotNull(message = "ID người dùng không được để trống")
    private Long userId;

    @NotBlank(message = "Nội dung thông báo không được để trống")
    private String message;

    private String type;

    private String targetUrl;
}
