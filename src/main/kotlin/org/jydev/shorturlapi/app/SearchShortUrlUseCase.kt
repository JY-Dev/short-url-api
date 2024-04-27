package org.jydev.shorturlapi.app

import org.jydev.shorturlapi.app.exception.SearchShortUrlException
import org.jydev.shorturlapi.domain.UrlRepository
import org.springframework.stereotype.Service

@Service
class SearchShortUrlUseCase(
    private val urlRepository: UrlRepository
) {

    operator fun invoke(shortUrlPath : String) : String =
        urlRepository.getUrl(shortUrlPath)
            ?: throw SearchShortUrlException("유효하지 않은 URL 입니다.")
}