package org.jydev.shorturlapi.util.shard

fun Shard<out Any, out Any>.checkingAndReBalanceShard(
    thresholdPercent: Int,
    incCapacity: Int
) {
    val dataStorePercent = (this.size() / this.limitSize().toDouble()) * 100
    val reachThreshold = dataStorePercent >= thresholdPercent

    if (reachThreshold) {
        val currentCapacity = this.capacity
        this.changeShard(currentCapacity.plus(incCapacity))
    }
}