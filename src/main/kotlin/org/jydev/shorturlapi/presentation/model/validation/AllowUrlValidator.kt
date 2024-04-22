package org.jydev.shorturlapi.presentation.model.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.jydev.shorturlapi.presentation.property.BaseProperties

class AllowUrlValidator(private val baseProperties: BaseProperties) : ConstraintValidator<AllowUrlConstraint, String> {

    private val urlPattern = "^https?://(www\\.)?[a-zA-Z0-9\\-.]+\\.[a-zA-Z]{2,}(/\\S*)?$".toRegex()

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {

        value ?: return false

        val urlMatches = urlPattern.matches(value)
        val serverHostUrlMatches = value.contains(baseProperties.url)

        return urlMatches && serverHostUrlMatches.not()
    }

}