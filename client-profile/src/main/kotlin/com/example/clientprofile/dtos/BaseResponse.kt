package com.example.clientprofile.dtos

import com.fasterxml.jackson.annotation.JsonProperty

open class BaseResponse (
    @field: JsonProperty("resultDescription", required = false)
    open var resulDescription: String? = null,

    @field: JsonProperty("resultCode")
    open var resultCode: Int? = null,

    @field: JsonProperty("success")
    open var success: Boolean? = null,
)