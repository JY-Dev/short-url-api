package org.jydev.shorturlapi.domain

import org.springframework.stereotype.Component

@Component
class SequenceUrlPathGenerator {

    private val chars = ('a'..'z') + ('A'..'Z')

    fun generate(idx : Long, urlLength : Int) : String {

        val base = chars.size
        var currentIdx = idx
        val result = StringBuilder()

        for (i in 0 until urlLength) {
            result.insert(0, chars[(currentIdx % base).toInt()])
            currentIdx /= base
        }

        if (currentIdx > 0) {
            throw IllegalArgumentException("Index exceeds the number of possible words for the given length")
        }

        return result.toString()
    }

}