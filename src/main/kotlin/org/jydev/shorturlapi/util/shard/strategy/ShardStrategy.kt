package org.jydev.shorturlapi.util.shard.strategy

interface ShardStrategy<K : Any, V : Any> {
    fun <M : MutableMap<K,V>> createNewShardHolder(previousShard : List<Map<K,V>>, newCapacity: Int, createMap : () -> M) : List<M>
    fun shardIdx(shardHolder : List<Map<K,V>>, key : K) : Int
}