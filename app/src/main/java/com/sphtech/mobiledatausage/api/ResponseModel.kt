package com.sphtech.mobiledatausage.api

import com.sphtech.mobiledatausage.data.MobileDataUsage

object ResponseModel {

    data class DataStoreResponse(
        var success: Boolean,
        var result: DataStoreResult?
    )

    data class DataStoreResult(
        var resource_id: String,
        var limit: Int,
        var total: Long,
        var fields: List<Field>,
        var records: List<MobileDataUsage>?,
        var _links: Links
    )

    data class Field(var _id: String, var type: String)

    data class Links(var start: String, var next: String)
}