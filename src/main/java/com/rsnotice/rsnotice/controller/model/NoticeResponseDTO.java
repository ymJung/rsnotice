package com.rsnotice.rsnotice.controller.model;

import java.time.LocalDateTime;

public record NoticeResponseDTO(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long viewCount,
        String createdBy
) {}