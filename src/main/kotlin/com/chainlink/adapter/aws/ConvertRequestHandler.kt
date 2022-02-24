package com.chainlink.adapter.aws

import com.chainlink.domain.transport.ConvertRequest
import com.chainlink.domain.transport.ConvertResponse
import com.chainlink.infrastructure.client.ConversionClient
import io.micronaut.core.annotation.Introspected
import io.micronaut.function.aws.MicronautRequestHandler
import jakarta.inject.Inject

@Introspected
open class ConvertRequestHandler(
    @Inject private val conversionClient: ConversionClient
) : MicronautRequestHandler<ConvertRequest?, ConvertResponse?>() {

    override fun execute(input: ConvertRequest?): ConvertResponse? {
        return if (input != null) {
            val responseData = conversionClient.convert(input.data.from, input.data.to)
            ConvertResponse(input.id, responseData)
        } else {
            ConvertResponse(-1, "Input cannot be null")
        }
    }
}
