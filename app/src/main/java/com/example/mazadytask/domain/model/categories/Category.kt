package com.example.mazadytask.domain.model.categories

import com.example.mazadytask.domain.model.Option

data class Category(val id: Int,val name: String,val children: List<Child>)

fun Category.toOption(): Option {
    return Option(id = id, name = name, hasChild = false)
}
