package org.jydev.shorturlapi.app

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.jydev.shorturlapi.app.util.ShortUrlPathGenerator
import org.jydev.shorturlapi.domain.SequenceUrlPathGenerator
import org.jydev.shorturlapi.infra.MemoryShardUrlRepository

class GenerateShortUrlUseCaseTest {

    private val sequenceUrlPathGenerator = SequenceUrlPathGenerator()
    private val urlRepository = MemoryShardUrlRepository()

    private val generateShortUrlUseCase = GenerateShortUrlUseCase(
        shortUrlPathGenerator = ShortUrlPathGenerator(
            urlPathGenerator = sequenceUrlPathGenerator
        ),
        urlRepository = urlRepository
    )

    @Test
    fun `ShortUrlPath를 생성할 때 실제 URL이 저장 되어야합니다`() {

        val url = "url"
        val shortUrlPath = generateShortUrlUseCase(url = url)

        Assertions.assertEquals(urlRepository.getUrl(shortUrlPath), url)
    }

    @Test
    fun `ShortUrl를 여러개 생성하는 경우 생성한 만큼 저장이 되어야 합니다`() {

        val url = "url"
        val repeatTimes = 1000

        repeat(repeatTimes) {
            val shortUrlPath = generateShortUrlUseCase(url = url)

            Assertions.assertEquals(urlRepository.getUrl(shortUrlPath), url)
        }
    }
}