package com.logger.practicletask.models.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeedsModelItem(
    @Json(name = "city")
    var city: String? = null,
    @Json(name = "first_name")
    var firstName: String? = null,
    @Json(name = "id")
    var id: Int? = null,
    @Json(name = "is_selected")
    var isSelected: Boolean? = null,
    @Json(name = "last_name")
    var lastName: String? = null
)

