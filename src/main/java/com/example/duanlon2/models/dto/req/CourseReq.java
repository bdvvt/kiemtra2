package com.example.duanlon2.models.dto.req;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseReq {
    @NotBlank(message = "Tiêu đề khóa học không được để trống")
    @Size(max = 255, message = "Tiêu đề không được vượt quá 255 ký tự")
    private String title;

    private String description;

    @NotNull(message = "ID giảng viên không được để trống")
    private Long teacherId;

    @NotNull(message = "Giá khóa học không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Giá khóa học phải lớn hơn hoặc bằng 0")
    private BigDecimal price;

    @Min(value = 1, message = "Thời lượng khóa học phải lớn hơn 0")
    private Long durationHours;

    private String status;
}
