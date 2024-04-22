package org.jydev.shorturlapi.app

import org.jydev.shorturlapi.app.exception.ConvertRealUrlException
import org.jydev.shorturlapi.domain.UrlRepository
import org.springframework.stereotype.Service

@Service
class ConvertRealUrlUseCase(
    private val urlRepository: UrlRepository
) {

    operator fun invoke(shortUrlPath : String) : String =
        urlRepository.getUrl(shortUrlPath)
            ?: throw ConvertRealUrlException("유효하지 않은 URL 입니다.")
}