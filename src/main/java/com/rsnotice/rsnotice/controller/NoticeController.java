package com.rsnotice.rsnotice.controller;


import com.rsnotice.rsnotice.controller.model.NoticeRequestDTO;
import com.rsnotice.rsnotice.controller.model.NoticeResponseDTO;
import com.rsnotice.rsnotice.service.NoticeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {
    private final NoticeService service;

    public NoticeController(NoticeService service) {
        this.service = service;
    }

    @GetMapping
    public List<NoticeResponseDTO> getAllNotices() {
        return service.getNotices();
    }

    @PostMapping
    public ResponseEntity<NoticeResponseDTO> createNotice(
            @RequestPart("notice") NoticeRequestDTO request,
            @RequestPart("files") List<MultipartFile> files) {
        return ResponseEntity.ok(service.createNotice(request, files));
    }
    @PutMapping("/{id}")
    public ResponseEntity<NoticeResponseDTO> updateNotice(
            @PathVariable Long id,
            @RequestBody NoticeRequestDTO request) {
        return ResponseEntity.ok(service.updateNotice(id, request));
    }


}