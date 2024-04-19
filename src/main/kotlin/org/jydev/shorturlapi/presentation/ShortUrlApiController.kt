package org.jydev.shorturlapi.presentation

import jakarta.validation.Valid
import org.jydev.shorturlapi.app.GenerateShortUrlUseCase
import org.jydev.shorturlapi.presentation.model.request.GenerateShortUrlRequest
import org.jydev.shorturlapi.presentation.model.response.GenerateShortUrlResponse
import org.jydev.shorturlapi.presentation.property.BaseProperties
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ShortUrlApiController(
    private val generateShortUrlUseCase: GenerateShortUrlUseCase,
    private val baseProperties: BaseProperties
) {
    @PostMapping("/api/short-url")
    fun generateShortUrl(@RequestBody @Valid request: GenerateShortUrlRequest): GenerateShortUrlResponse {

        val cleanUrl = removeQueryParameters(request.url)
        val shortUrlPath = generateShortUrlUseCase.invoke(cleanUrl)

        val shortUrl = "${baseProperties.url}/short-url/$shortUrlPath"

        return GenerateShortUrlResponse(shortUrl)
    }

    private fun removeQueryParameters(url: String): String {
        return url.substringBefore('?')
    }
}