package com.example.mazadytask.domain.model.properties

import com.example.mazadytask.domain.model.Option


data class Child (val id: Int,val parentId: Int,val name: String,val child: Boolean)


fun Child.toOption(): Option {
    return Option(id = id, name = name, hasChild = child)
}