package org.jydev.shorturlapi.presentation.model.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass


@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [AllowUrlValidator::class])
annotation class AllowUrlConstraint(
    val message: String = "Invalid Url",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
