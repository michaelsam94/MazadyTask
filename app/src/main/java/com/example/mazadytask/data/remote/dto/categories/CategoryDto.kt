package com.example.mazadytask.data.remote.dto.categories

import com.example.mazadytask.domain.model.categories.Category

data class CategoryDto(
    val children: List<ChildrenDto>,
    val circle_icon: String,
    val description: String?,
    val disable_shipping: Int,
    val id: Int,
    val image: String,
    val name: String,
    val slug: String
)

fun CategoryDto.toCategory(): Category {
    val categoryChilds = children.map { it.toCategoryChild() }
    return Category(id = id,name= name, children = categoryChilds)
}