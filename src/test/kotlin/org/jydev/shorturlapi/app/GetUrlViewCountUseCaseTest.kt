package org.jydev.shorturlapi.app

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.jydev.shorturlapi.app.exception.GetUrlViewCountException
import org.jydev.shorturlapi.app.util.ShortUrlPathGenerator
import org.jydev.shorturlapi.domain.SequenceUrlPathGenerator
import org.jydev.shorturlapi.infra.MemoryShardUrlRepository

class GetUrlViewCountUseCaseTest {

    private val urlRepository = MemoryShardUrlRepository()

    private val getUrlViewCountUseCase = GetUrlViewCountUseCase(
        urlViewCountRepository = urlRepository
    )

    private val searchShortUrlUseCase = SearchShortUrlUseCase(
        urlRepository = urlRepository
    )

    private val sequenceUrlPathGenerator = SequenceUrlPathGenerator()

    private val generateShortUrlUseCase = GenerateShortUrlUseCase(
        shortUrlPathGenerator = ShortUrlPathGenerator(
            urlPathGenerator = sequenceUrlPathGenerator
        ),
        urlRepository = urlRepository
    )

    @Test
    fun `Short Url을 조회한 수 만큼 조회수를 반환해야합니다`() {

        val url = "url"
        val shortUrlPath = generateShortUrlUseCase(url = url)
        val searchCnt = 20

        repeat(searchCnt) {
            searchShortUrlUseCase(shortUrlPath = shortUrlPath)
        }

        val searchViewCount = getUrlViewCountUseCase(shortUrlPath)

        Assertions.assertEquals(searchViewCount, searchCnt.toLong())
    }

    @Test
    fun `유효하지 않는 URL로 조회수 조회시에 예외가 발생해야합니다`() {

        val shortUrlPath = "url"

        Assertions.assertThrows(GetUrlViewCountException::class.java) {
            getUrlViewCountUseCase(shortUrlPath)
        }
    }
}