package org.jydev.shorturlapi.presentation

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.jydev.shorturlapi.app.GenerateShortUrlUseCase
import org.jydev.shorturlapi.presentation.model.request.GenerateShortUrlRequest
import org.jydev.shorturlapi.presentation.model.response.GenerateShortUrlResponse
import org.jydev.shorturlapi.presentation.property.BaseProperties
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ShortUrlApiController::class)
class ShortUrlApiControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var generateShortUrlUseCase: GenerateShortUrlUseCase

    @MockBean
    private lateinit var baseProperties: BaseProperties

    @Test
    fun `유효한 URL로 짧은 URL 생성`() {
        val originalUrl = "http://example.com/path"
        val shortUrlPath = "abcd1234"
        val baseUrl = "http://sho.rt"

        Mockito.`when`(generateShortUrlUseCase.invoke(originalUrl)).thenReturn(shortUrlPath)
        Mockito.`when`(baseProperties.url).thenReturn(baseUrl)

        val request = GenerateShortUrlRequest(originalUrl)
        val response = GenerateShortUrlResponse("$baseUrl/short-url/$shortUrlPath")

        mockMvc.perform(
            post("/short-url")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect { result ->
                assertEquals(objectMapper.writeValueAsString(response), result.response.contentAsString)
            }
    }

    @Test
    fun `유효하지 않은 URL 형식`() {
        val invalidUrl = "http://example"

        val request = GenerateShortUrlRequest(invalidUrl)

        mockMvc.perform(
            post("/short-url")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest)
    }
}