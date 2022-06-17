package com.example.kommunalkaapp.Model

import kotlinx.serialization.Serializable

@Serializable
data class PaymentModel(
    val id: String,
    val hotWaterCount: Int,
    val coldWaterCount: Int,
    val electricity: Int,
    val date: String
    )