package com.rsnotice.rsnotice.service;

import com.rsnotice.rsnotice.controller.exception.InvalidNoticeException;
import com.rsnotice.rsnotice.controller.exception.NoticeNotFoundException;
import com.rsnotice.rsnotice.controller.model.NoticeRequestDTO;
import com.rsnotice.rsnotice.controller.model.NoticeResponseDTO;
import com.rsnotice.rsnotice.repository.NoticeRepository;
import com.rsnotice.rsnotice.repository.entity.NoticeEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticeService {
    private final NoticeRepository repository;

    public NoticeService(NoticeRepository repository) {
        this.repository = repository;
    }

    public NoticeResponseDTO createNotice(NoticeRequestDTO request, List<MultipartFile> files) {
        validateNoticeRequest(request);

        NoticeEntity notice = new NoticeEntity();
        notice.setTitle(request.title());
        notice.setContent(request.content());
        notice.setStartDate(request.startDate());
        notice.setEndDate(request.endDate());
        notice.setCreatedBy(request.createdBy());

        repository.save(notice);
        return mapToResponse(notice);
    }
    private void validateNoticeRequest(NoticeRequestDTO request) {
        if (request.startDate().isAfter(request.endDate())) {
            throw new InvalidNoticeException("Start date must be before end date");
        }
    }

    @Cacheable("notices")
    public List<NoticeResponseDTO> getNotices() {
        return repository.findAll().stream()
                .map(notice -> new NoticeResponseDTO(
                        notice.getId(),
                        notice.getTitle(),
                        notice.getContent(),
                        notice.getCreatedAt(),
                        notice.getUpdatedAt(),
                        notice.getViewCount(),
                        notice.getCreatedBy()
                )).toList();
    }

    @Transactional
    public NoticeResponseDTO updateNotice(Long id, NoticeRequestDTO request) {
        validateNoticeRequest(request);

        NoticeEntity notice = repository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("Notice with ID " + id + " not found"));

        notice.setTitle(request.title());
        notice.setContent(request.content());
        notice.setStartDate(request.startDate());
        notice.setEndDate(request.endDate());
        notice.setUpdatedAt(LocalDateTime.now());

        repository.save(notice);
        return mapToResponse(notice);
    }

    private NoticeResponseDTO mapToResponse(NoticeEntity notice) {
        return new NoticeResponseDTO(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getCreatedAt(),
                notice.getUpdatedAt(),
                notice.getViewCount(),
                notice.getCreatedBy()
        );
    }
}
