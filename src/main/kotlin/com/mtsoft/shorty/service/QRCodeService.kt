package com.mtsoft.shorty.service

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import net.glxn.qrgen.javase.QRCode
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets

@Service
class QRCodeService {

    fun generateQRCode(url: String): Resource {
        return QRCode.from(url)
                .withSize(250, 250)
                .withCharset(StandardCharsets.UTF_8.name())
                .withErrorCorrection(ErrorCorrectionLevel.H)
                .stream().use {
            ByteArrayResource(it.toByteArray(), url)
        }
    }
}
