package com.chainlink.infrastructure.client

import com.chainlink.fixtures.ConvertFixture.Companion.ETH
import com.chainlink.fixtures.ConvertFixture.Companion.USD
import com.chainlink.fixtures.ConvertFixture.Companion.getBody
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Value
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.uri.UriBuilder
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

@MicronautTest
class MinAPICryptoCompareTest {

    @Inject lateinit var httpClient: HttpClient
    @Inject lateinit var blockingHttpClient: BlockingHttpClient
    @Inject lateinit var conversionClient: ConversionClient
    @Value("\${adapter.client.min_api_crypto_compare.base_url}") lateinit var baseURL: String

    @Test
    fun `given a non-empty to and from when convert is called then make an HTTP GET request`() {
        `when`(httpClient.toBlocking()).thenReturn(blockingHttpClient)
        `when`(blockingHttpClient.retrieve(getURI())).thenReturn(getBody())

        val actualResponse = conversionClient.convert(ETH, USD)

        verify(httpClient.toBlocking()).retrieve(getURI())
        assertEquals(getBody(), actualResponse)
    }

    private fun getURI(): String {
        val uri = UriBuilder.of(baseURL)
            .queryParam("fsym", ETH)
            .queryParam("tsyms", USD)
            .build()

        return uri.toString()
    }

    @MockBean
    fun blockingHttpClient(): BlockingHttpClient {
        return mock(BlockingHttpClient::class.java)
    }

    @Replaces
    @MockBean
    fun httpClient(): HttpClient {
        return mock(HttpClient::class.java)
    }
}
