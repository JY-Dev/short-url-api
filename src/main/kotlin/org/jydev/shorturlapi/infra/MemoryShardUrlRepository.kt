package org.jydev.shorturlapi.infra

import jakarta.annotation.PostConstruct
import org.jydev.shorturlapi.domain.url.UrlRepository
import org.jydev.shorturlapi.domain.url.UrlViewCountRepository
import org.jydev.shorturlapi.util.shard.CountingShard
import org.jydev.shorturlapi.util.shard.checkingAndReBalanceShard
import org.springframework.stereotype.Repository

@Repository
class MemoryShardUrlRepository : UrlRepository, UrlViewCountRepository {

    private val shard = CountingShard<String, String>(INITIAL_CAPACITY)

    override fun getUrl(key: String): String? =
        shard.get(key)

    override fun storeUrl(key: String, url: String) {

        val reachThreshold = shard.size() / shard.limitSize() * 100 >= SHARD_DATA_PERCENT_THRESHOLD

        if(reachThreshold) {
            val currentCapacity = shard.capacity
            shard.changeShard(currentCapacity.plus(INC_CAPACITY))
        }

        shard.put(key, url)
    }

    override fun deleteUrl(key: String) {
        shard.remove(key)
    }

    override fun getViewCount(key: String): Long? =
        shard.getCount(key)

    override fun resetViewCount(key: String) {
        shard.resetCount(key)
    }

    @PostConstruct
    private fun checkingShardThreshold() {

        val checkingAndReBalanceShard = {
            shard.checkingAndReBalanceShard(
                thresholdPercent = SHARD_DATA_PERCENT_THRESHOLD,
                incCapacity = INC_CAPACITY
            )
        }

        Thread {
            try {
                while (!Thread.currentThread().isInterrupted) {
                    checkingAndReBalanceShard()
                    Thread.sleep(5000)
                }
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }.start()
    }

    companion object {
        const val INITIAL_CAPACITY = 5
        const val INC_CAPACITY = 2
        const val SHARD_DATA_PERCENT_THRESHOLD = 80
    }
}