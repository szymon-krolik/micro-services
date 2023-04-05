package com.wazzup.eventservice.qrcode.controller;

import com.wazzup.eventservice.qrcode.service.QrCodeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Tag(name = "QrCode Managment")
@RestController
@RequestMapping("/api/qr-code")
@AllArgsConstructor
public class QrCodeController {
    private final QrCodeService qrCodeService;
    @PostMapping(value = "/qr-code/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] generateQrCode(@PathVariable Long id, HttpServletRequest request) {
        return qrCodeService.generateQrCode(id, request);
    }

    @GetMapping(value = "/qr-code/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getQrCode(@PathVariable Long id) {
        return qrCodeService.getQrCode(id);
    }
}
