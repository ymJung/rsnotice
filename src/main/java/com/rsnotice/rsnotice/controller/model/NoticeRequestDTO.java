package com.rsnotice.rsnotice.controller.model;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record NoticeRequestDTO (
        @NotBlank(message = "Title must not be blank")
        @Size(max = 255, message = "Title must not exceed 255 characters")
        String title,

        @NotBlank(message = "Content must not be blank")
        String content,

        @Future(message = "Start date must be in the future")
        LocalDateTime startDate,

        @Future(message = "End date must be in the future")
        LocalDateTime endDate,

        @NotBlank(message = "Created by must not be blank")
        @Size(max = 100, message = "Created by must not exceed 100 characters")
        String createdBy
) {}