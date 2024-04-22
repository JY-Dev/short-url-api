package org.jydev.shorturlapi.app.exception

class ConvertRealUrlException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}