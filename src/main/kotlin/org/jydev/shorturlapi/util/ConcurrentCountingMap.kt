package org.jydev.shorturlapi.util

import java.util.concurrent.ConcurrentHashMap

class ConcurrentCountingMap<K : Any, V : Any> : CountingMap<K, V> {

    private val concurrentHashMap = ConcurrentHashMap<K, CountingData<V>>()

    override val entries = mutableMapOf<K, V>().apply {

        concurrentHashMap.entries
            .map { it.key to it.value.data }
            .also(this::putAll)

    }.entries

    override val keys: MutableSet<K> = this.concurrentHashMap.keys
    override val size: Int
        get() = this.concurrentHashMap.size

    override val values: MutableCollection<V> = this.concurrentHashMap
        .values
        .map { it.data }
        .toMutableList()

    override fun clear() {
        this.concurrentHashMap
            .clear()
    }

    override fun remove(key: K, value: V): Boolean {
        return this.concurrentHashMap.remove(key) != null
    }

    override fun put(key: K, value: V): V = CountingData(value, 0).let { countingData ->
        this.concurrentHashMap[key] = countingData
        countingData.data
    }

    override fun count(key: K): Long? =
        this.concurrentHashMap[key]?.count

    override fun containsKey(key: K): Boolean =
        this.concurrentHashMap.containsKey(key)

    override fun containsValue(value: V): Nothing =
        throw UnsupportedOperationException("지원하지 않는 메서드 입니다.")

    override fun get(key: K): V? =
        this.concurrentHashMap.computeIfPresent(key) { _, v ->
            CountingData(v.data, v.count.inc())
        }?.data

    override fun isEmpty(): Boolean =
        this.concurrentHashMap.isEmpty()

    override fun replace(key: K, value: V): Nothing =
        throw UnsupportedOperationException("지원하지 않는 메서드 입니다.")

    override fun replace(key: K, oldValue: V, newValue: V): Nothing =
        throw UnsupportedOperationException("지원하지 않는 메서드 입니다.")

    override fun putIfAbsent(key: K, value: V): V? {
        return this.concurrentHashMap.putIfAbsent(key, CountingData(value, 0))?.data
    }

    override fun remove(key: K): V? =
        this.concurrentHashMap.remove(key)?.data

    override fun putAll(from: Map<out K, V>) {

        from.map { it.key to CountingData(it.value, 0) }
            .also(this.concurrentHashMap::putAll)
    }
}