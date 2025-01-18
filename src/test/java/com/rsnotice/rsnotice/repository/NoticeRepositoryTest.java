package com.rsnotice.rsnotice.repository;

import com.rsnotice.rsnotice.repository.entity.NoticeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class NoticeRepositoryTest {

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    void testSaveNotice() {
        // Given
        NoticeEntity notice = new NoticeEntity();
        notice.setTitle("Test Notice");
        notice.setContent("This is a test notice.");
        notice.setStartDate(LocalDateTime.now().plusDays(1));
        notice.setEndDate(LocalDateTime.now().plusDays(10));
        notice.setCreatedBy("Test User");
        notice.setAttachments("Test attachment".getBytes());

        // When
        NoticeEntity savedNotice = noticeRepository.save(notice);

        // Then
        assertThat(savedNotice.getId()).isNotNull();
        assertThat(savedNotice.getTitle()).isEqualTo("Test Notice");
        assertThat(savedNotice.getContent()).isEqualTo("This is a test notice.");
        assertThat(savedNotice.getStartDate()).isAfter(LocalDateTime.now());
        assertThat(savedNotice.getEndDate()).isAfter(savedNotice.getStartDate());
        assertThat(savedNotice.getCreatedBy()).isEqualTo("Test User");
        assertThat(savedNotice.getAttachments()).isNotEmpty();
        assertThat(savedNotice.getViewCount()).isEqualTo(0L);
        assertThat(savedNotice.getCreatedAt()).isNotNull();
        assertThat(savedNotice.getUpdatedAt()).isNotNull();
    }

    @Test
    void testUpdateNotice() {
        // Given
        NoticeEntity notice = new NoticeEntity();
        notice.setTitle("Initial Title");
        notice.setContent("Initial Content");
        notice.setCreatedBy("Initial User");
        NoticeEntity savedNotice = noticeRepository.save(notice);

        // When
        savedNotice.setTitle("Updated Title");
        savedNotice.setContent("Updated Content");
        noticeRepository.save(savedNotice);

        // Then
        Optional<NoticeEntity> updatedNotice = noticeRepository.findById(savedNotice.getId());
        assertThat(updatedNotice).isPresent();
        assertThat(updatedNotice.get().getTitle()).isEqualTo("Updated Title");
        assertThat(updatedNotice.get().getContent()).isEqualTo("Updated Content");
    }

    @Test
    void testFindById() {
        // Given
        NoticeEntity notice = new NoticeEntity();
        notice.setTitle("Find Test");
        notice.setContent("Find by ID test.");
        notice.setStartDate(LocalDateTime.now().plusDays(1));
        notice.setEndDate(LocalDateTime.now().plusDays(5));
        NoticeEntity savedNotice = noticeRepository.save(notice);

        // When
        Optional<NoticeEntity> retrievedNotice = noticeRepository.findById(savedNotice.getId());

        // Then
        assertThat(retrievedNotice).isPresent();
        assertThat(retrievedNotice.get().getTitle()).isEqualTo("Find Test");
        assertThat(retrievedNotice.get().getContent()).isEqualTo("Find by ID test.");
        assertThat(retrievedNotice.get().getStartDate()).isAfter(LocalDateTime.now());
    }

    @Test
    void testDeleteNotice() {
        // Given
        NoticeEntity notice = new NoticeEntity();
        notice.setTitle("Delete Test");
        notice.setContent("This notice will be deleted.");
        NoticeEntity savedNotice = noticeRepository.save(notice);

        // When
        noticeRepository.deleteById(savedNotice.getId());
        Optional<NoticeEntity> deletedNotice = noticeRepository.findById(savedNotice.getId());

        // Then
        assertThat(deletedNotice).isNotPresent();
    }
}
