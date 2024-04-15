package org.jydev.shorturlapi.domain.url

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.jydev.shorturlapi.domain.SequenceUrlPathGenerator

class SequenceUrlPathGeneratorTest {

    private val generator = SequenceUrlPathGenerator()

    @ParameterizedTest
    @CsvSource(
        "0, 1, a",
        "1, 1, b",
        "0, 2, aa",
        "1, 2, ab",
        "52, 2, ba",
        "2703, 2, ZZ"
    )
    fun `idx와 length가 주어지면 URL PATH는 length 길이와 idx 순서에 맞게 생성 되어야 한다`(
        idx: Long, urlLength: Int, expectedOutput: String
    ) {
        Assertions.assertEquals(
            expectedOutput,
            generator.generate(idx, urlLength),
            "Failed at idx $idx with length $urlLength"
        )
    }

    @Test
    fun `length로 만들 수 있는 단어의 갯수보다 idx가 더 큰 경우 예외가 발생 해야한다 `() {
        val largeIndex = 52L * 52L + 1

        Assertions.assertThrows(IllegalArgumentException::class.java, {
            generator.generate(largeIndex, 2)
        }, "Should throw IllegalArgumentException for index that exceeds possible words")
    }
}