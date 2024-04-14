package org.jydev.shorturlapi.util.shard

import org.jydev.shorturlapi.util.shard.strategy.ShardStrategy

interface Shard<K : Any ,V : Any> {
    val capacity : Int
    fun put(key: K, value: V)
    fun get(key: K): V?
    fun remove(key : K)
    fun size() : Long
    fun limitSize() : Int
    fun changeShard(changeCapacity: Int, shardStrategy: ShardStrategy<K, V>? = null)
}