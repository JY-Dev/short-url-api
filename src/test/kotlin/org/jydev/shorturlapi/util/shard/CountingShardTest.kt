package org.jydev.shorturlapi.util.shard

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class CountingShardTest {

    @Test
    fun `Put한 데이터를 Get으로 가져올 수 있어야 한다`() {

        val countingShard = CountingShard<String, Int>(
            capacity = 5
        )

        val data = mutableListOf<Pair<String, Int>>()
        repeat(500) { cnt ->
            val key = UUID.randomUUID().toString()
            data.add(key to cnt)
            countingShard.put(key = key, value = cnt)
        }

        val isValueMatches : (Pair<String, Int>) -> Boolean = { (key, value) ->
            countingShard.get(key) == value
        }
        val valueMatchesData = data.filter(isValueMatches)

        Assertions.assertEquals(valueMatchesData.size, data.size)
    }

    @Test
    fun `데이터를 삭제했을 때 해당 key로 조회시 null을 반환 해야한다`() {

        val countingShard = CountingShard<String, Int>(
            capacity = 5
        )
        val key = "key"
        val value = 0
        countingShard.put(key, value)

        Assertions.assertEquals(countingShard.get(key), value)

        countingShard.remove(key)

        Assertions.assertEquals(countingShard.get(key), null)
    }

    @Test
    fun `데이터를 넣은 갯수 만큼 size 함수에서 반환 해야한다`() {

        val capacity = 5
        val countingShard = CountingShard<String, Int>(
            capacity = capacity
        )
        val dataSize = 500
        val data = mutableListOf<Pair<String, Int>>()
        repeat(dataSize) { cnt ->
            val key = UUID.randomUUID().toString()
            data.add(key to cnt)
            countingShard.put(key = key, value = cnt)
        }

        Assertions.assertEquals(countingShard.size(), dataSize.toLong())
    }


    @Test
    fun `shard를 변경할 때 기존 데이터도 유지되야 한다`() {

        val countingShard = CountingShard<String, Int>(
            capacity = 5
        )

        val data = mutableListOf<Pair<String, Int>>()
        repeat(500) { cnt ->
            val key = UUID.randomUUID().toString()
            data.add(key to cnt)
            countingShard.put(key = key, value = cnt)
        }

        val changeCapacity = 10
        countingShard.changeShard(changeCapacity = changeCapacity)

        val isValueMatches : (Pair<String, Int>) -> Boolean = { (key, value) ->
            countingShard.get(key) == value
        }
        val valueMatchesData = data.filter(isValueMatches)

        Assertions.assertEquals(valueMatchesData.size, data.size)
    }

    @Test
    fun `shard를 변경할 때 capacity도 변경 되어야 한다`() {

        val countingShard = CountingShard<String, Int>(
            capacity = 5
        )

        val data = mutableListOf<Pair<String, Int>>()
        repeat(500) { cnt ->
            val key = UUID.randomUUID().toString()
            data.add(key to cnt)
            countingShard.put(key = key, value = cnt)
        }

        val changeCapacity = 10
        countingShard.changeShard(changeCapacity = changeCapacity)

        Assertions.assertEquals(countingShard.capacity, changeCapacity)
    }

    @Test
    fun `GET을 호출한 수 만큼 카운팅 할 수 있어야 한다`() {

        val countingShard = CountingShard<String, Int>(
            capacity = 5
        )
        val key = UUID.randomUUID().toString()
        countingShard.put(key, 0)
        val getCount = 500

        repeat(getCount) {
            countingShard.get(key)
        }

        Assertions.assertEquals(countingShard.getCount(key)?.toInt(), getCount)
    }
}