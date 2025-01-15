package com.rsnotice.rsnotice.service;

import com.rsnotice.rsnotice.controller.exception.InvalidNoticeException;
import com.rsnotice.rsnotice.controller.exception.NoticeNotFoundException;
import com.rsnotice.rsnotice.controller.model.NoticeRequestDTO;
import com.rsnotice.rsnotice.controller.model.NoticeResponseDTO;
import com.rsnotice.rsnotice.repository.NoticeRepository;
import com.rsnotice.rsnotice.repository.entity.NoticeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class NoticeServiceTest {

    @InjectMocks
    private NoticeService noticeService;

    @Mock
    private NoticeRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNotice_ShouldCreateNoticeSuccessfully() {
        // Given
        NoticeRequestDTO request = new NoticeRequestDTO("Title", "Content", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Creator");
        NoticeEntity savedEntity = new NoticeEntity();
        savedEntity.setId(1L);
        savedEntity.setTitle(request.title());
        savedEntity.setContent(request.content());
        savedEntity.setStartDate(request.startDate());
        savedEntity.setEndDate(request.endDate());
        savedEntity.setCreatedBy(request.createdBy());
        when(repository.save(any(NoticeEntity.class))).thenReturn(savedEntity);

        // When
        NoticeResponseDTO response = noticeService.createNotice(request, new ArrayList<>());

        // Then
        assertNotNull(response);
        assertEquals(request.title(), response.title());
        verify(repository, times(1)).save(any(NoticeEntity.class));
    }

    @Test
    void createNotice_ShouldThrowInvalidNoticeException_WhenStartDateIsAfterEndDate() {
        // Given
        NoticeRequestDTO request = new NoticeRequestDTO("Title", "Content", LocalDateTime.now().plusDays(1), LocalDateTime.now(), "Creator");

        // When & Then
        assertThrows(InvalidNoticeException.class, () -> noticeService.createNotice(request, new ArrayList<>()));
    }

    @Test
    void getNotices_ShouldReturnNoticeList() {
        // Given
        NoticeEntity entity = new NoticeEntity();
        entity.setId(1L);
        entity.setTitle("Title");
        entity.setContent("Content");
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setCreatedBy("Creator");
        when(repository.findAll()).thenReturn(List.of(entity));

        // When
        List<NoticeResponseDTO> notices = noticeService.getNotices();

        // Then
        assertNotNull(notices);
        assertEquals(1, notices.size());
        assertEquals("Title", notices.get(0).title());
        verify(repository, times(1)).findAll();
    }

    @Test
    void updateNotice_ShouldUpdateNoticeSuccessfully() {
        // Given
        Long id = 1L;
        NoticeRequestDTO request = new NoticeRequestDTO("Updated Title", "Updated Content", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Updater");
        NoticeEntity entity = new NoticeEntity();
        entity.setId(id);
        entity.setTitle("Old Title");
        entity.setContent("Old Content");
        entity.setStartDate(LocalDateTime.now());
        entity.setEndDate(LocalDateTime.now().plusDays(1));
        entity.setCreatedBy("Creator");
        when(repository.findById(eq(id))).thenReturn(Optional.of(entity));
        when(repository.save(any(NoticeEntity.class))).thenReturn(entity);

        // When
        NoticeResponseDTO response = noticeService.updateNotice(id, request);

        // Then
        assertNotNull(response);
        assertEquals("Updated Title", response.title());
        assertEquals("Updated Content", response.content());
        verify(repository, times(1)).findById(eq(id));
        verify(repository, times(1)).save(any(NoticeEntity.class));
    }

    @Test
    void updateNotice_ShouldThrowNoticeNotFoundException_WhenNoticeDoesNotExist() {
        // Given
        Long id = 1L;
        NoticeRequestDTO request = new NoticeRequestDTO("Title", "Content", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Updater");
        when(repository.findById(eq(id))).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoticeNotFoundException.class, () -> noticeService.updateNotice(id, request));
        verify(repository, times(1)).findById(eq(id));
        verify(repository, times(0)).save(any(NoticeEntity.class));
    }
}
