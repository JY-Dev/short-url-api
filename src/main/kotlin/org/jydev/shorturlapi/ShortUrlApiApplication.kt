package org.jydev.shorturlapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class ShortUrlApiApplication

fun main(args: Array<String>) {
    runApplication<ShortUrlApiApplication>(*args)
}
