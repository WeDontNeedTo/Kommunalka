package com.example.kommunalkaapp.model

import kotlinx.serialization.Serializable

@Serializable
data class PaymentModel(
    var id: String,
    var hotWaterCount: Int,
    var coldWaterCount: Int,
    var electricity: Int,
    var date: String
    )

@Serializable
data class PaymentPatchDTO(
    var hotWaterCount: Int?,
    var coldWaterCount: Int?,
    var electricity: Int?,
    var date: String?
)