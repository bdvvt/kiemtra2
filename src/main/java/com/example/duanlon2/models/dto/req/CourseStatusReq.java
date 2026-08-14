package com.example.duanlon2.models.dto.req;

import com.example.duanlon2.models.constants.CourseStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseStatusReq {
    @NotNull(message = "Trạng thái khóa học không được để trống")
    private CourseStatus status;
}

