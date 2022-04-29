package com.example.mazadytask.data.remote.dto.categories

data class Data(
    val ads_banners: List<AdsBanner>,
    val categories: List<CategoryDto>,
    val statistics: Statistics
)