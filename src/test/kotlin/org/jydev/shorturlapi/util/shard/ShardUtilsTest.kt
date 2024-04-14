package org.jydev.shorturlapi.util.shard

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class ShardUtilsTest {

    @Test
    fun `임계값에 도달했을 때 shard의 수가 증가 되어야합니다`() {
        val capacity = 1
        val defaultShard = DefaultShard<String, Int>(
            capacity = capacity
        )

        val thresholdPercent = 1
        val thresholdSize = defaultShard.limitSize() / 100 * thresholdPercent
        repeat(thresholdSize.plus(1)) { value ->
            val key = UUID.randomUUID().toString()
            defaultShard.put(key , value)
        }
        val incCapacity = 2

        defaultShard.checkingAndReBalanceShard(
            thresholdPercent = thresholdPercent,
            incCapacity = incCapacity
        )

        Assertions.assertEquals(defaultShard.capacity, capacity.plus(incCapacity))
    }
}