package com.mtsoft.shorty.rest

import com.mtsoft.shorty.api.ShortyApi
import com.mtsoft.shorty.api.model.ShortyCreateRequest
import com.mtsoft.shorty.service.MappingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*

@RestController
class ShortyController @Autowired constructor(
        private val mappingService: MappingService
) : ShortyApi {

    override fun getMapping(mappingId: UUID): ResponseEntity<Unit> {
        val mappedUrl = mappingService.getMappedUrl(mappingId) ?: return ResponseEntity.notFound().build()

        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .location(URI.create(mappedUrl))
                .build()
    }

    override fun createMapping(shortyCreateRequest: ShortyCreateRequest): ResponseEntity<Unit> {
        val shortUrl = mappingService.createMapping(shortyCreateRequest)
        return ResponseEntity.created(URI.create(shortUrl)).build()
    }
}
