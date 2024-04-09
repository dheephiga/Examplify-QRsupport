package com.code.qrsp;

import com.code.qrsp.qrCodeGenerator;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QRCodeController {

    @GetMapping(value = "/generateQR/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generateQRCode(@PathVariable String id) {
        return qrCodeGenerator.generateQRCode(id);
    }
}
