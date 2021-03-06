package com.mtsoft.shorty.service

import com.mtsoft.shorty.api.model.ShortyCreateRequest
import com.mtsoft.shorty.persistence.model.MappingEntity
import com.mtsoft.shorty.persistence.repository.MappingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class MappingService @Autowired constructor(
        @Value("\${meta.domain}") private val domain: String,
        private val mappingRepository: MappingRepository
) {

    fun createMapping(
        request: ShortyCreateRequest
    ): String = mappingRepository.findByOriginalUrl(request.originalUrl)?.let {
        "$domain/${it.id}"
    } ?: run {
            val newEntity = MappingEntity()
            newEntity.originalUrl = request.originalUrl
            "$domain/${mappingRepository.save(newEntity).id}"
        }

    fun getMappedUrl(id: UUID): String? = mappingRepository.findByIdOrNull(id)?.originalUrl
}
