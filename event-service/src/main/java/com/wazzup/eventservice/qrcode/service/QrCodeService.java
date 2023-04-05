package com.wazzup.eventservice.qrcode.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.wazzup.eventservice.event.dto.user.UserInformationWithIdDTO;
import com.wazzup.eventservice.event.entity.Event;
import com.wazzup.eventservice.event.exception.InternalApiException;
import com.wazzup.eventservice.event.exception.QrCodeNotFoundException;
import com.wazzup.eventservice.event.repository.EventRepository;
import com.wazzup.eventservice.event.service.EventService;
import com.wazzup.eventservice.qrcode.entity.QrCode;
import com.wazzup.eventservice.qrcode.repository.QrCodeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@AllArgsConstructor
@Slf4j
public class QrCodeService {

    private final QrCodeRepository qrCodeRepository;
    private final EventService eventService;
    private final EventRepository eventRepository;
    /**
     * Funckcja generująca QrCode wydarzenia prowadzacy na strone głowną
     * @param event - Wydarzenia dla ktorego genrujemy qr code
     */
    public QrCode createQrCode(Event event) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(createUrlToEvent(event.getId()), BarcodeFormat.QR_CODE, 250, 250);
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", bos);

        } catch (WriterException | IOException e) {
            throw new InternalApiException();
        }
        return saveQrCode(bos.toByteArray(), event);
    }

    private QrCode saveQrCode(byte[] content, Event event) {
        QrCode qrCode = new QrCode();
        qrCode.setContent(content);
        qrCode.setEvent(event);

        return qrCodeRepository.save(qrCode);
    }

    private String createUrlToEvent(Long eventId) {
        return "127.0.0.1:8082/event/api/event/" + eventId;
    }

    public byte[] generateQrCode(Long id, HttpServletRequest request) {
        UserInformationWithIdDTO userDto = eventService.getUserInformation(request);
        eventService.checkEventOwner(userDto.getId(), id);
        Event event = eventService.getEventById(id);
        try {
            event.getQrCodeContent().getContent();
        } catch (NullPointerException ex) {
            if (!event.isQrCode()) {
                event.setQrCode(true);
                event.setQrCodeContent(createQrCode(event));
                eventRepository.save(event);
                return event.getQrCodeContent().getContent();
            }

            event.setQrCodeContent(createQrCode(event));
            eventRepository.save(event);
            return event.getQrCodeContent().getContent();
        }
        return event.getQrCodeContent().getContent();
    }
    public byte[] getQrCode(Long id) {
        return qrCodeRepository.findQrCodeContentByEventId(id).orElseThrow(QrCodeNotFoundException::new);
    }
}
