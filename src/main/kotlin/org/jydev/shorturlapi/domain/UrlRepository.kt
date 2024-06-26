package org.jydev.shorturlapi.domain

interface UrlRepository {
    fun getUrl(key : String) : String?
    fun storeUrl(key : String, url : String)
    fun deleteUrl(key : String)
}