package org.jydev.shorturlapi.presentation.model.request

import jakarta.validation.constraints.Pattern

data class GenerateShortUrlRequest(
    @get:Pattern(regexp = "^https?://(www\\.)?[a-zA-Z0-9\\-.]+\\.[a-zA-Z]{2,}(/\\S*)?\$", message = "Invalid URL format")
    val url : String
)
