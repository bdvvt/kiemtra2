package com.example.duanlon2.models.dto.req;

import com.example.duanlon2.models.constants.EnrollmentStatus;
import com.example.duanlon2.models.entities.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.annotation.Before;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrollmentReq {
    @NotNull(message = "ID khóa học không được để trống")
    private Long courseId;

    private LocalDateTime enrollmentDate;

    private EnrollmentStatus status;

    @Future(message = "Ngày hoàn thành phải ở trong tương lai")
    private LocalDateTime completionDate;

    @DecimalMin(value = "0.0", inclusive = true, message = "Tiến độ phải lớn hơn hoặc bằng 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Tiến độ phải nhỏ hơn hoặc bằng 100")
    private BigDecimal progressPercent;

}

