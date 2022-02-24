package com.chainlink.adapter.rest

import com.chainlink.domain.transport.ConvertResponse
import com.chainlink.fixtures.ConvertFixture.Companion.ETH
import com.chainlink.fixtures.ConvertFixture.Companion.USD
import com.chainlink.fixtures.ConvertFixture.Companion.getRequestBody
import com.chainlink.infrastructure.client.ConversionClient
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.exceptions.HttpException
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@MicronautTest
class ConvertTest {

    @Inject
    lateinit var conversionClient: ConversionClient

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    private val objectMapper = ObjectMapper()

    @Test
    fun `given a valid request to convert when convert is called then the conversion is successful`() {
        val expectedDataResponse = "{\"USD\":\"100.0\"}"
        val expectedResponse = ConvertResponse(123, expectedDataResponse)

        `when`(conversionClient.convert(ETH, USD)).thenReturn(expectedDataResponse)

        val request: HttpRequest<Any> = HttpRequest.POST(
            "/", getRequestBody()
        )
        val actualResponse = client.toBlocking().retrieve(request)
        assertEquals(expectedResponse, objectMapper.readValue(actualResponse, ConvertResponse::class.java))
    }

    @Test
    fun `given a valid request to convert when convert is called and throws then an http error is thrown`() {
        `when`(conversionClient.convert(ETH, USD)).thenThrow(RuntimeException("Endpoint failed!"))

        val request: HttpRequest<Any> = HttpRequest.POST(
            "/", getRequestBody()
        )

        assertThrows<HttpException> {
            client.toBlocking().retrieve(request)
        }
    }

    @Test
    fun `given an invalid request to convert when convert is called then an http error is thrown`() {
        val request: HttpRequest<Any> = HttpRequest.POST(
            "/", "should_fail"
        )
        assertThrows<HttpException> {
            client.toBlocking().retrieve(request)
        }
    }

    @MockBean(ConversionClient::class)
    fun mockConversionClient(): ConversionClient {
        return mock(ConversionClient::class.java)
    }
}
