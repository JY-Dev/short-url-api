package org.jydev.shorturlapi.presentation.model.request

import org.jydev.shorturlapi.presentation.model.validation.AllowUrlConstraint

data class GenerateShortUrlRequest(
    @field:AllowUrlConstraint
    val url: String
)
