package com.mtsoft.shorty

import com.mtsoft.shorty.abstr.AbstractShortyTest
import com.mtsoft.shorty.api.model.ShortyCreateRequest
import com.mtsoft.shorty.persistence.repository.MappingRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import java.util.*

@SpringBootTest
class ShortyApplicationTests : AbstractShortyTest() {

    @Autowired private lateinit var mappingRepository: MappingRepository

    @Test
    fun `can create mapping and get mapped URL`() {
        // insert entry
        val originalUrl = "https://www.martinschroeder.net"
        val request = ShortyCreateRequest(originalUrl)
        val putResult = put("/", request, HttpStatus.CREATED)
        val shortenedUrl = putResult.response.getHeader(HttpHeaders.LOCATION)

        assertThat(shortenedUrl).isNotBlank

        // verify persisted content
        val id = shortenedUrl!!.split("/").last()
        val entity = mappingRepository.findByIdOrNull(UUID.fromString(id))
        assertThat(entity).isNotNull
        assertThat(entity!!.id.toString()).isEqualTo(id)
        assertThat(entity.originalUrl).isEqualTo(originalUrl)

        // verify service and REST
        val getResult = get(shortenedUrl, HttpStatus.SEE_OTHER)
        val mappedUrl = getResult.response.getHeader(HttpHeaders.LOCATION)

        assertThat(mappedUrl).isEqualTo(originalUrl)
    }

    @Test
    fun `can handle unknown mapping ids`() {
        get("http://localhost:8080/7FA6547F-F768-4385-A5C7-B36B4E3C9E65", HttpStatus.NOT_FOUND)
    }

    @Test
    fun `can handle invalid mapping ids`() {
        get("http://localhost:8080/any.invalid.mapping.id", HttpStatus.BAD_REQUEST)
    }
}
