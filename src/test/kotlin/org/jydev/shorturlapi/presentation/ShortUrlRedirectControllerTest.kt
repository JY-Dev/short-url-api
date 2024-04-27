package org.jydev.shorturlapi.presentation

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.jydev.shorturlapi.app.SearchShortUrlUseCase
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@ExtendWith(SpringExtension::class)
@WebMvcTest(ShortUrlRedirectController::class)
class ShortUrlRedirectControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var searchShortUrlUseCase: SearchShortUrlUseCase

    @Test
    fun `주어진 단축 URL 경로로 리다이렉트 해야 함`() {
        // Given
        val shortUrlPath = "abcdAABB"
        val realUrl = "http://example.com"

        `when`(searchShortUrlUseCase.invoke(shortUrlPath)).thenReturn(realUrl)

        // When/Then
        mockMvc.get("/short-url/$shortUrlPath")
            .andExpect {
                status { is3xxRedirection() }
                redirectedUrl(realUrl)

            }
    }
}