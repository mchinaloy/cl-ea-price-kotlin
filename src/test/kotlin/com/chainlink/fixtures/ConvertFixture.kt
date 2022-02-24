package com.chainlink.fixtures

class ConvertFixture {

    companion object {

        const val ETH = "ETH"
        const val USD = "USD"

        fun getBody(): String {
            return """
           {
                "USD": 100.0
           } 
        """
        }

        fun getRequestBody(jobRunId: Int = 123): String {
            return """
            {
                "id": $jobRunId,
                "data": {
                "from": "ETH",
                "to": "USD"
                }
            }
            """
        }
    }
}
