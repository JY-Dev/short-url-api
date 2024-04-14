package org.jydev.shorturlapi.util.shard.strategy

import kotlin.math.absoluteValue

class DefaultShardStrategy<K: Any, V: Any> : ShardStrategy<K, V> {

    override fun <M : MutableMap<K, V>> createNewShardHolder(
        previousShard: List<Map<K, V>>,
        newCapacity: Int,
        createMap: () -> M
    ): List<M> {
        val newShardMaps = MutableList(newCapacity) { createMap() }

        previousShard.forEach { map ->
            map.entries.forEach { entry ->
                val idx = shardIdx(newShardMaps, entry.key)
                newShardMaps[idx][entry.key] = entry.value
            }
        }

        return newShardMaps
    }

    override fun shardIdx(shardHolder: List<Map<K, V>>, key: K): Int {
        return key.hashCode().absoluteValue % shardHolder.size
    }

}