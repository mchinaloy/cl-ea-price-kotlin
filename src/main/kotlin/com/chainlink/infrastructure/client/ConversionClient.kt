package com.chainlink.infrastructure.client

interface ConversionClient {

    fun convert(from: String, to: String): String
}
