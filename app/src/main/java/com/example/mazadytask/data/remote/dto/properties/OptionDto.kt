package com.example.mazadytask.data.remote.dto.properties

import com.example.mazadytask.domain.model.properties.Child

data class OptionDto(
    val child: Boolean,
    val id: Int,
    val name: String,
    val parent: Int,
    val slug: String
)

fun OptionDto.toPropertyChild(): Child {
    return Child(id = id, parentId = parent,name=name,child = child)
}