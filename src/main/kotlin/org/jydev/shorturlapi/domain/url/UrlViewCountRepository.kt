package org.jydev.shorturlapi.domain.url

interface UrlViewCountRepository {
    fun getViewCount(key : String) : Long?
    fun resetViewCount(key : String)
}