package org.jydev.shorturlapi.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.concurrent.thread

class ConcurrentCountingMapTest {

    @Test
    fun `데이터를 넣었을 때 Key에 대한 값이 정상적으로 반환되야한다`( ) {
        val map = ConcurrentCountingMap<String, String>()
        val key = "test-key"
        val value = "test-value"

        map[key] = value

        assertEquals(map.size, 1)
        assertEquals(map[key], value)
    }

    @Test
    fun `멀티 쓰레드 환경에서 조회한 수 만큼 카운팅 되야한다`() {

        val map = ConcurrentCountingMap<String, String>()
        val key = "test-key"
        val value = "test-value"

        map[key] = value

        val numberOfThreads = 100
        val iterationsPerThread = 1000

        val getValueThreads = List(numberOfThreads) {
            thread {
                repeat(iterationsPerThread) {
                    map[key]
                }
            }
        }
        getValueThreads.forEach { it.join() }

        assertEquals(numberOfThreads * iterationsPerThread, map.count(key)?.toInt())

    }
}