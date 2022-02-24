package com.chainlink.adapter.aws

import com.chainlink.domain.transport.ConvertRequest
import com.chainlink.domain.transport.ConvertRequestData
import com.chainlink.domain.transport.ConvertResponse
import com.chainlink.fixtures.ConvertFixture.Companion.ETH
import com.chainlink.fixtures.ConvertFixture.Companion.USD
import com.chainlink.fixtures.ConvertFixture.Companion.getBody
import com.chainlink.infrastructure.client.ConversionClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class ConvertRequestHandlerTest {

    private val conversionClient = mock(ConversionClient::class.java)

    @Test
    fun `given a valid request when execute is called then a response is returned`() {
        `when`(conversionClient.convert(ETH, USD)).thenReturn(getBody())

        val expectedResponse = ConvertResponse(0, getBody())

        val convertRequestHandler = ConvertRequestHandler(conversionClient)
        val convertRequest = ConvertRequest(0, ConvertRequestData(ETH, USD))

        val actualResponse = convertRequestHandler.execute(convertRequest)
        assertEquals(expectedResponse, actualResponse)
        convertRequestHandler.applicationContext.close()
    }

    @Test
    fun `given an invalid request when execute is called then an error response is returned`() {
        `when`(conversionClient.convert(ETH, USD)).thenReturn(getBody())

        val expectedResponse = ConvertResponse(-1, "Input cannot be null")

        val convertRequestHandler = ConvertRequestHandler(conversionClient)

        val actualResponse = convertRequestHandler.execute(null)
        assertEquals(expectedResponse, actualResponse)
        convertRequestHandler.applicationContext.close()
    }
}
