package com.example.duanlon2.models.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.duanlon2.models.constants.UserStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStatusReq {
    @NotNull(message = "Trạng thái status không được để trống")
    private UserStatus status;
}
