package org.jydev.shorturlapi.app.util

import org.jydev.shorturlapi.domain.SequenceUrlPathGenerator
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicLong

@Component
class ShortUrlPathGenerator(
    private val urlPathGenerator: SequenceUrlPathGenerator
) {

    private val generateIdx = AtomicLong(0)

    fun generate(): String = generateIdx.getAndIncrement()
        .let { idx ->
            urlPathGenerator.generate(idx, URL_LENGTH)
        }

    companion object {
        private const val URL_LENGTH = 8
    }
}