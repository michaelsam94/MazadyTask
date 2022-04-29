package com.example.mazadytask.presentation.form

import com.example.mazadytask.domain.model.categories.Category
import com.example.mazadytask.domain.model.properties.Property

data class FormState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val properties: List<Property> = emptyList(),
    val childProperty: Property? = null,
    val error: String = ""
)
