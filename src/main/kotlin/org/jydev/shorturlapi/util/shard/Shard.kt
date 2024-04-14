package org.jydev.shorturlapi.util.shard

interface Shard<K : Any ,V : Any> {
    val capacity : Int
    fun put(key: K, value: V)
    fun get(key: K): V?
}