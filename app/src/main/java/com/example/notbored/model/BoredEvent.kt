package com.example.notbored.model

data class BoredEvent(
    val activity: String,
    val accessibility: Float,
    val type: String,
    val participants: Int,
    val price: Float,
    val link: String,
    val key: String,
    val error: String?
)
