package com.rsnotice.rsnotice.controller.model;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public record NoticeRequestDTO (
        String title,
        String content,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String createdBy,
        List<MultipartFile> attachments
) {}