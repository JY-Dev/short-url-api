package org.jydev.shorturlapi.util.shard

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.jydev.shorturlapi.util.shard.strategy.DefaultShardStrategy
import java.util.UUID

class DefaultShardStrategyTest {

    @Test
    fun `shard를 생성할 때 항상 capacity 크기로 생성해야한다`() {
        val shardStrategy = DefaultShardStrategy<String, String>()
        val capacity = 5

        val newShard = shardStrategy.createNewShardHolder(arrayListOf(), capacity) {
            mutableMapOf()
        }

        Assertions.assertEquals(newShard.size, capacity)
    }

    @Test
    fun `shard를 생성할 때 이전 데이터도 유지되어야 한다`() {
        val shardStrategy = DefaultShardStrategy<String, String>()
        val capacity = 5

        val data = arrayOf(
            "data" to "value",
            "data1" to "value1",
            "data2" to "value2"
        )

        val previousData = List(1){
            mutableMapOf<String,String>().apply {
                putAll(data)
            }
        }

        val newShard = shardStrategy.createNewShardHolder(
            previousShard = previousData,
            newCapacity = capacity
        ) {
            mutableMapOf()
        }

        var cnt = 0
        newShard.forEach { map ->
            map.entries.forEach { (key, value) ->

                val isDataMatches = data.contains(key to value)
                if(isDataMatches) cnt++
            }
        }

        Assertions.assertEquals(cnt, data.size)
    }

    @Test
    fun `shardIdx는 capactiy 보다 항상 작아야 합니다`() {
        val shardStrategy = DefaultShardStrategy<String, String>()
        val capacity = 5

        val shardHolder = shardStrategy.createNewShardHolder(arrayListOf(), capacity) {
            mutableMapOf()
        }
        val repeatCount = 200
        repeat(repeatCount) {

            val idx = shardStrategy.shardIdx(shardHolder, UUID.randomUUID().toString())
            val idxLowerThanCapacity = idx < capacity

            Assertions.assertEquals(idxLowerThanCapacity, true)
        }

    }
}