package org.jydev.shorturlapi.app.exception

class GetUrlViewCountException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}