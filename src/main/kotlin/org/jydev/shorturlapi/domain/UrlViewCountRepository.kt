package org.jydev.shorturlapi.domain

interface UrlViewCountRepository {
    fun getViewCount(key : String) : Long?
    fun resetViewCount(key : String)
}