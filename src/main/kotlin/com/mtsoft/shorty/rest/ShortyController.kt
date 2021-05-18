package com.mtsoft.shorty.rest

import com.mtsoft.shorty.api.ShortyApi
import com.mtsoft.shorty.api.model.ShortyCreateRequest
import com.mtsoft.shorty.service.MappingService
import com.mtsoft.shorty.service.QRCodeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*

@RestController
class ShortyController @Autowired constructor(
        private val mappingService: MappingService,
        private val qrCodeService: QRCodeService
) : ShortyApi {

    override fun getMapping(mappingId: UUID): ResponseEntity<Unit> {
        val mappedUrl = mappingService.getMappedUrl(mappingId) ?: return ResponseEntity.notFound().build()

        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .location(URI.create(mappedUrl))
                .build()
    }

    override fun createMapping(shortyCreateRequest: ShortyCreateRequest): ResponseEntity<Resource> {
        val shortUrl = mappingService.createMapping(shortyCreateRequest)
        val resource = if (shortyCreateRequest.qr) {
            qrCodeService.generateQRCode(shortUrl)
        }else { null }

        return resource?.let {
            ResponseEntity.created(URI.create(shortUrl)).body(it)
        } ?: ResponseEntity.created(URI.create(shortUrl)).build()
    }
}
