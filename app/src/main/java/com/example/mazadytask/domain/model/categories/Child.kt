package com.example.mazadytask.domain.model.categories

import com.example.mazadytask.domain.model.Option

data class Child(
    val id: Int,
    val name: String,
)


fun Child.toOption(): Option {
    return Option(id = id, name = name, hasChild = false)
}

