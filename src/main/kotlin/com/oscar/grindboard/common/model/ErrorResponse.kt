package com.oscar.grindboard.common.model

import java.time.Instant

data class ErrorResponse(
    val code: String,
    val message: String,
    val details: List<ErrorDetail>? = null,
    val timestamp: Instant = Instant.now(),
)

data class ErrorDetail(
    val field: String,
    val message: String,
)
