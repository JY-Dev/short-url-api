package org.jydev.shorturlapi.util.shard

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class DefaultShardTest {

    @Test
    fun `Put한 데이터를 Get으로 가져올 수 있어야 한다`() {

        val defaultShard = DefaultShard<String, Int>(
            capacity = 5
        )

        val data = mutableListOf<Pair<String, Int>>()
        repeat(500) { cnt ->
            val key = UUID.randomUUID().toString()
            data.add(key to cnt)
            defaultShard.put(key = key, value = cnt)
        }

        val isValueMatches : (Pair<String, Int>) -> Boolean = { (key, value) ->
            defaultShard.get(key) == value
        }
        val valueMatchesData = data.filter(isValueMatches)

        Assertions.assertEquals(valueMatchesData.size, data.size)
    }

    @Test
    fun `shard를 변경할 때 기존 데이터도 유지되야 한다`() {

        val defaultShard = DefaultShard<String, Int>(
            capacity = 5
        )

        val data = mutableListOf<Pair<String, Int>>()
        repeat(500) { cnt ->
            val key = UUID.randomUUID().toString()
            data.add(key to cnt)
            defaultShard.put(key = key, value = cnt)
        }

        val changeCapacity = 10
        defaultShard.changeShard(changeCapacity = changeCapacity)

        val isValueMatches : (Pair<String, Int>) -> Boolean = { (key, value) ->
            defaultShard.get(key) == value
        }
        val valueMatchesData = data.filter(isValueMatches)

        Assertions.assertEquals(valueMatchesData.size, data.size)
    }

    @Test
    fun `shard를 변경할 때 capacity도 변경 되어야 한다`() {

        val defaultShard = DefaultShard<String, Int>(
            capacity = 5
        )

        val data = mutableListOf<Pair<String, Int>>()
        repeat(500) { cnt ->
            val key = UUID.randomUUID().toString()
            data.add(key to cnt)
            defaultShard.put(key = key, value = cnt)
        }

        val changeCapacity = 10
        defaultShard.changeShard(changeCapacity = changeCapacity)

        Assertions.assertEquals(defaultShard.capacity, changeCapacity)
    }
}