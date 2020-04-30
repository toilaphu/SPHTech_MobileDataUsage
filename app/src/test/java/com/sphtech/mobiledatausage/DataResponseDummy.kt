package com.sphtech.mobiledatausage

import com.sphtech.mobiledatausage.data.MobileDataUsage
import okhttp3.mockwebserver.MockResponse
import java.math.BigDecimal
import java.net.HttpURLConnection

object DataResponseDummy {

    fun getDataUsageResponse(): MockResponse {
        return MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(
                "{\"help\": \"https://data.gov.sg/api/3/action/help_show?name=datastore_search\", " +
                        "\"success\": true, " +
                        "\"result\": " +
                        "{\"resource_id\": \"a807b7ab-6cad-4aa6-87d0-e283a7353a0f\", " +
                        "\"fields\": " +
                        "[{\"type\": \"int4\", \"id\": \"_id\"}, {\"type\": \"text\", \"id\": \"quarter\"}, " +
                        "{\"type\": \"numeric\", \"id\": \"volume_of_mobile_data\"}], " +
                        "\"records\": [" +
                        "{\"volume_of_mobile_data\": \"0.000384\", \"quarter\": \"2004-Q3\", \"_id\": 1}, " +
                        "{\"volume_of_mobile_data\": \"0.000543\", \"quarter\": \"2004-Q4\", \"_id\": 2}, " +
                        "{\"volume_of_mobile_data\": \"0.00062\", \"quarter\": \"2005-Q1\", \"_id\": 3}, " +
                        "{\"volume_of_mobile_data\": \"0.000634\", \"quarter\": \"2005-Q2\", \"_id\": 4}, " +
                        "{\"volume_of_mobile_data\": \"0.000718\", \"quarter\": \"2005-Q3\", \"_id\": 5}], " +
                        "\"_links\": " +
                        "{\"start\": \"/api/action/datastore_search?limit=5&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f\", " +
                        "\"next\": \"/api/action/datastore_search?offset=5&limit=5&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f\"}, " +
                        "\"limit\": 5," +
                        " \"total\": 59}}"
            )
    }

    fun getDataUsageEmptyRecordsResponse(): MockResponse {
        return MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(
                "{\"help\": \"https://data.gov.sg/api/3/action/help_show?name=datastore_search\", " +
                        "\"success\": true, " +
                        "\"result\": " +
                        "{\"resource_id\": \"a807b7ab-6cad-4aa6-87d0-e283a7353a0f\", " +
                        "\"fields\": " +
                        "[{\"type\": \"int4\", \"id\": \"_id\"}, {\"type\": \"text\", \"id\": \"quarter\"}, " +
                        "{\"type\": \"numeric\", \"id\": \"volume_of_mobile_data\"}], " +
                        "\"records\": [], " +
                        "\"_links\": " +
                        "{\"start\": \"/api/action/datastore_search?limit=5&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f\", " +
                        "\"next\": \"/api/action/datastore_search?offset=5&limit=5&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f\"}, " +
                        "\"limit\": 5," +
                        " \"total\": 59}}"
            )
    }

    fun getDataUsageBadRequestResponse(): MockResponse {
        return MockResponse().setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
    }

    fun getListMobileDataUsageDummy(): List<MobileDataUsage> {
        return listOf(
            MobileDataUsage(1, "2009-Q1", BigDecimal.valueOf(1.4)),
            MobileDataUsage(2, "2009-Q2", BigDecimal.valueOf(1.5)),
            MobileDataUsage(3, "2009-Q3", BigDecimal.valueOf(1.2)),
            MobileDataUsage(4, "2009-Q4", BigDecimal.valueOf(1.6)),
            MobileDataUsage(5, "2010-Q1", BigDecimal.valueOf(2.1)),
            MobileDataUsage(6, "2010-Q2", BigDecimal.valueOf(2.2)),
            MobileDataUsage(7, "2010-Q3", BigDecimal.valueOf(2.3)),
            MobileDataUsage(8, "2010-Q4", BigDecimal.valueOf(2.4)),
            MobileDataUsage(9, "2011-Q1", BigDecimal.valueOf(5.1)),
            MobileDataUsage(10, "2011-Q2", BigDecimal.valueOf(2.1)),
            MobileDataUsage(11, "2012-Q1", BigDecimal.valueOf(3.1)),
            MobileDataUsage(12, "2012-Q2", BigDecimal.valueOf(12.1)),
            MobileDataUsage(13, "2012-Q3", BigDecimal.valueOf(12.2)),
            MobileDataUsage(14, "2012-Q4", BigDecimal.valueOf(12.3)),
            MobileDataUsage(15, "2013-Q1", BigDecimal.valueOf(13.4)),
            MobileDataUsage(16, "2013-Q2", BigDecimal.valueOf(13.5)),
            MobileDataUsage(17, "2013-Q3", BigDecimal.valueOf(13.6)),
            MobileDataUsage(18, "2013-Q4", BigDecimal.valueOf(13.7)),
            MobileDataUsage(19, "2014-Q1", BigDecimal.valueOf(14.8)),
            MobileDataUsage(20, "2014-Q2", BigDecimal.valueOf(14.9)),
            MobileDataUsage(21, "2014-Q3", BigDecimal.valueOf(14.10)),
            MobileDataUsage(22, "2014-Q4", BigDecimal.valueOf(14.11)),
            MobileDataUsage(23, "2015-Q1", BigDecimal.valueOf(15.12)),
            MobileDataUsage(24, "2015-Q2", BigDecimal.valueOf(15.13))
        )
    }
}