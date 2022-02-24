package com.chainlink.adapter.rest

import com.chainlink.domain.transport.ConvertRequest
import com.chainlink.domain.transport.ConvertResponse
import com.chainlink.infrastructure.client.ConversionClient
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.cache.annotation.CacheConfig
import io.micronaut.cache.annotation.Cacheable
import io.micronaut.core.version.annotation.Version
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import jakarta.inject.Inject

@CacheConfig("prices")
@Controller
open class Convert(
    @Inject private val conversionClient: ConversionClient
) {

    private val objectMapper = ObjectMapper()

    @Cacheable
    @Version("1")
    @Post
    open fun convert(@Body convertRequest: ConvertRequest): String? {
        val responseData = conversionClient.convert(convertRequest.data.from, convertRequest.data.to)
        return objectMapper.writeValueAsString(ConvertResponse(convertRequest.id, responseData))
    }
}
