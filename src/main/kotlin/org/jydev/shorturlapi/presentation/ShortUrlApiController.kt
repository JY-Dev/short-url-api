package org.jydev.shorturlapi.presentation

import jakarta.validation.Valid
import org.jydev.shorturlapi.app.GenerateShortUrlUseCase
import org.jydev.shorturlapi.app.GetUrlViewCountUseCase
import org.jydev.shorturlapi.presentation.model.request.GenerateShortUrlRequest
import org.jydev.shorturlapi.presentation.model.response.GenerateShortUrlResponse
import org.jydev.shorturlapi.presentation.model.response.GetViewCountResponse
import org.jydev.shorturlapi.presentation.property.BaseProperties
import org.springframework.web.bind.annotation.*

@RestController
class ShortUrlApiController(
    private val generateShortUrlUseCase: GenerateShortUrlUseCase,
    private val getUrlViewCountUseCase: GetUrlViewCountUseCase,
    private val baseProperties: BaseProperties
) {
    @PostMapping("/api/short-url")
    fun generateShortUrl(@RequestBody @Valid request: GenerateShortUrlRequest): GenerateShortUrlResponse {

        val cleanUrl = request.url.removeQueryParameters()
        val shortUrlPath = generateShortUrlUseCase.invoke(url = cleanUrl)

        val shortUrl = "${baseProperties.url}/$SHORT_URL_BASE/$shortUrlPath"

        return GenerateShortUrlResponse(url = shortUrl)
    }

    private fun String.removeQueryParameters(): String =
        this.substringBefore('?')

    @GetMapping("/short-url/{shortUrlPath}/view-count")
    fun getViewCount(@PathVariable shortUrlPath : String) : GetViewCountResponse =
        GetViewCountResponse(
            viewCount = getUrlViewCountUseCase(
                shortUrlPath = shortUrlPath
            )
        )

    companion object {
        const val SHORT_URL_BASE = "short-url"
    }
}