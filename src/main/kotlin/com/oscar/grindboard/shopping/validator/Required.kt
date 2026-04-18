package com.oscar.grindboard.shopping.validator

import jakarta.annotation.Nonnull
import jakarta.validation.Constraint
import jakarta.validation.ReportAsSingleViolation
import jakarta.validation.constraints.NotBlank
import kotlin.reflect.KClass

@Nonnull
@NotBlank
@ReportAsSingleViolation
@Constraint(validatedBy = [])
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Required(
    val message: String = "Required field is missing",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = [],
)
