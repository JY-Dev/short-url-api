package org.jydev.shorturlapi.util.shard

import org.jydev.shorturlapi.util.map.ConcurrentCountingMap
import org.jydev.shorturlapi.util.shard.strategy.DefaultShardStrategy
import org.jydev.shorturlapi.util.shard.strategy.ShardStrategy

class CountingShard<K : Any, V : Any>(
    capacity: Int,
    private var shardStrategy: ShardStrategy<K, V> = DefaultShardStrategy()
) : Shard<K, V> {

    private var shardHolder = this.shardStrategy
        .createNewShardHolder(
            previousShard = listOf(),
            newCapacity = capacity,
            createMap = this::createMap
        )

    override val capacity: Int
        get() = shardHolder.size

    override fun put(key: K, value: V) {

        val idx = shardStrategy.shardIdx(shardHolder, key)
        shardHolder[idx][key] = value
    }

    override fun get(key: K): V? {

        val idx = shardStrategy.shardIdx(shardHolder, key)
        return shardHolder[idx][key]
    }

    fun getCount(key: K): Long? {

        val idx = shardStrategy.shardIdx(shardHolder, key)
        return shardHolder[idx].count(key)
    }

    fun resetCount(key : K)  {

        val idx = shardStrategy.shardIdx(shardHolder, key)
        return shardHolder[idx].resetCount(key)
    }

    @Synchronized
    fun changeShard(changeCapacity: Int, shardStrategy: ShardStrategy<K, V>? = null) {

        this.shardStrategy = shardStrategy ?: this.shardStrategy
        this.shardStrategy
            .createNewShardHolder(
                previousShard = this.shardHolder,
                newCapacity = changeCapacity,
                createMap = this::createMap
            ).also { this.shardHolder = it }
    }

    private fun createMap(): ConcurrentCountingMap<K, V> =
        ConcurrentCountingMap()

}