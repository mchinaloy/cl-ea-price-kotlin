package com.chainlink.infrastructure.client

import com.chainlink.adapter.rest.Convert
import io.micronaut.context.annotation.Value
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.uri.UriBuilder
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class MinAPICryptoCompare(
    @field:Client("/") @Inject var httpClient: HttpClient,
    @Value("\${adapter.client.min_api_crypto_compare.base_url}") var baseURL: String
) : ConversionClient {

    private val logger: Logger = LoggerFactory.getLogger(Convert::class.java)

    override fun convert(from: String, to: String): String {
        logger.debug("Request to convert from=$from and to=$to")
        val uri = UriBuilder.of(baseURL)
            .queryParam("fsym", from)
            .queryParam("tsyms", to)
            .build()
        val response = httpClient.toBlocking().retrieve(uri.toString())
        logger.debug("Response=$response")
        return response
    }
}
