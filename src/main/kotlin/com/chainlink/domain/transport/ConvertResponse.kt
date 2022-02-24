package com.chainlink.domain.transport

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected

@Introspected
data class ConvertResponse(@JsonProperty("jobRunId") val jobRunId: Int, @JsonProperty("data") val data: String)
