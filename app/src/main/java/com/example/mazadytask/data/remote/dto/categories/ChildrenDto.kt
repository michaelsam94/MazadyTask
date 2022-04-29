package com.example.mazadytask.data.remote.dto.categories

import com.example.mazadytask.domain.model.categories.Child

data class ChildrenDto(
    val children: Any?,
    val circle_icon: String,
    val description: Any?,
    val disable_shipping: Int,
    val id: Int,
    val image: String,
    val name: String,
    val slug: String
)

fun ChildrenDto.toCategoryChild() : Child {
    return Child(
        id= id,
        name= name,
    )
}