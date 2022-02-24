package com.chainlink.domain.transport

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected

data class ConvertRequestData(@JsonProperty("from") val from: String, @JsonProperty("to") val to: String)

@Introspected
data class ConvertRequest(@JsonProperty("id") val id: Int, @JsonProperty("data") val data: ConvertRequestData)
