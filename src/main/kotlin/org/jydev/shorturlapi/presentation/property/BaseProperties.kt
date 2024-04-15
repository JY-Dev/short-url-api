package org.jydev.shorturlapi.presentation.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding


@ConfigurationProperties(prefix = "base")
data class BaseProperties @ConstructorBinding constructor(
    val url : String
)