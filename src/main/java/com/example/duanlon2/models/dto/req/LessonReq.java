package com.example.duanlon2.models.dto.req;


import com.example.duanlon2.validations.annotations.FileExtension;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonReq {
    @NotNull(message = "ID khóa học không được để trống")
    private Long courseId;

    @NotBlank(message = "Tiêu đề bài học không được để trống")
    @Size(max = 255, message = "Tiêu đề không được vượt quá 255 ký tự")
    private String title;

    @FileExtension(allowedExtensions = {".jpg",".png",".webp",".mp4"},message = "File không đúng định dạng")
    private MultipartFile contentUrl;

    private String textContent;

    @NotNull(message = "Thứ tự bài học không được để trống")
    @Min(value = 1, message = "Thứ tự bài học phải bắt đầu từ 1")
    private Long orderIndex;

    private Boolean isPublished;
}
