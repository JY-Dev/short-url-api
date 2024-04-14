package org.jydev.shorturlapi.util.map

interface CountingMap<K : Any, V : Any> : MutableMap<K, V> {
    fun count(key: K): Long?
}

data class CountingData<V : Any>(val data: V, val count: Long)