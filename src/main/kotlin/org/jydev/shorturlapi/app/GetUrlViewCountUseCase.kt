package org.jydev.shorturlapi.app

import org.jydev.shorturlapi.app.exception.GetUrlViewCountException
import org.jydev.shorturlapi.domain.UrlViewCountRepository
import org.springframework.stereotype.Service

@Service
class GetUrlViewCountUseCase(
    private val urlViewCountRepository: UrlViewCountRepository
) {

    operator fun invoke(shortUrlPath: String) =
        urlViewCountRepository.getViewCount(shortUrlPath)
            ?: throw GetUrlViewCountException("존재하지 않는 Short Url Path 입니다")
}