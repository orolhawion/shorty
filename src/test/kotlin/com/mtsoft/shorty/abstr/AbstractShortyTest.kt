package com.mtsoft.shorty.abstr

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.opentest4j.AssertionFailedError
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.lang.invoke.MethodHandles

@ActiveProfiles(profiles = ["test"])
abstract class AbstractShortyTest {

    private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())

    @Autowired protected lateinit var webApplicationContext: WebApplicationContext
    @Autowired lateinit var objectMapper: ObjectMapper

    private lateinit var mvc: MockMvc

    @BeforeEach
    fun prepare() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    protected fun get(
            target: String,
            expectedHttpState: HttpStatus = HttpStatus.OK
    ) : MvcResult {
        val result = mvc.perform(
                MockMvcRequestBuilders.get(target)
        ).andReturn()

        assertExpectedHttpState(result, expectedHttpState)

        return result
    }

    protected fun put(
            target: String,
            body: Any,
            expectedHttpState: HttpStatus = HttpStatus.OK,
            contentType: MediaType = MediaType.APPLICATION_JSON
    ): MvcResult {
        val bodyAsJson = objectMapper.writeValueAsString(body)
        val result =  mvc.perform(
                MockMvcRequestBuilders.put(target)
                        .contentType(contentType)
                        .content(bodyAsJson)
        ).andReturn()

        assertExpectedHttpState(result, expectedHttpState)

        return result
    }

    private fun assertExpectedHttpState(result: MvcResult, expectedHttpState: HttpStatus) {
        try {
            Assertions.assertThat(result.response.status).isEqualTo(expectedHttpState.value())
        }catch (e: AssertionFailedError) {
            log.error("Unexpected http response state for url={}, message={}",
                    result.request.requestURI,
                    result.response.errorMessage,
                    e)

            Assertions.fail("Unexpected http response state ${result.response.status} for " +
                    "url=" + result.request.requestURI +
                    ", message=" + result.response.errorMessage)
        }
    }
}
