package org.jydev.shorturlapi.app.exception

class SearchShortUrlException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}