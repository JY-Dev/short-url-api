package org.jydev.shorturlapi.util.shard

import org.jydev.shorturlapi.util.shard.strategy.DefaultShardStrategy
import org.jydev.shorturlapi.util.shard.strategy.ShardStrategy

class DefaultShard<K : Any, V : Any>(
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

    override fun size(): Long =
        this.shardHolder.sumOf {
            it.size.toLong()
        }

    override fun limitSize(): Int =
        capacity * Shard.SHARD_LIMIT_SIZE

    override fun remove(key: K) {

        val idx = shardStrategy.shardIdx(shardHolder, key)
        shardHolder[idx].remove(key)
    }


    override fun put(key: K, value: V) {

        val idx = shardStrategy.shardIdx(shardHolder, key)
        shardHolder[idx][key] = value
    }

    override fun get(key: K): V? {

        val idx = shardStrategy.shardIdx(shardHolder, key)
        return shardHolder[idx][key]
    }

    @Synchronized
    override fun changeShard(changeCapacity: Int, shardStrategy: ShardStrategy<K, V>?) {

        this.shardStrategy = shardStrategy ?: this.shardStrategy
        this.shardStrategy
            .createNewShardHolder(
                previousShard = this.shardHolder,
                newCapacity = changeCapacity,
                createMap = this::createMap
            ).also { this.shardHolder = it }
    }


    private fun createMap(): MutableMap<K, V> =
        mutableMapOf()

}