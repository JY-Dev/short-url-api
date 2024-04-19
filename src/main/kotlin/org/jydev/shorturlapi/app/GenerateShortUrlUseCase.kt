package org.jydev.shorturlapi.app

import org.jydev.shorturlapi.app.util.ShortUrlPathGenerator
import org.jydev.shorturlapi.domain.UrlRepository
import org.springframework.stereotype.Service

@Service
class GenerateShortUrlUseCase(
    private val shortUrlPathGenerator: ShortUrlPathGenerator,
    private val urlRepository: UrlRepository
) {

    operator fun invoke(url : String) : String =
        shortUrlPathGenerator.generate()
            .also { shortUrlPath ->
                urlRepository.storeUrl(shortUrlPath, url)
            }
}