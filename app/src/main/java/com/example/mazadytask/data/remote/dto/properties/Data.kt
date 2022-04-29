package com.example.mazadytask.data.remote.dto.properties

import com.example.mazadytask.domain.model.properties.Property

data class Data(
    val description: Any,
    val id: Int,
    val list: Boolean,
    val name: String,
    val options: List<OptionDto>,
    val other_value: Any,
    val parent: Any,
    val slug: String,
    val type: Any,
    val value: String
)

fun Data.toProperty() : Property {
    return Property(id = id,name= name, children = options.map { it.toPropertyChild() })
}

