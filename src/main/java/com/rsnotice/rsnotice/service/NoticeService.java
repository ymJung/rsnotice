package com.rsnotice.rsnotice.service;

import com.rsnotice.rsnotice.controller.model.NoticeRequestDTO;
import com.rsnotice.rsnotice.controller.model.NoticeResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class NoticeService {
    public NoticeResponseDTO createNotice(NoticeRequestDTO request, List<MultipartFile> files) {
        return null;
    }

    public List<NoticeResponseDTO> getNotices() {
        return null;
    }

    public NoticeResponseDTO updateNotice(Long id, NoticeRequestDTO request) {
        return null;
    }
}
