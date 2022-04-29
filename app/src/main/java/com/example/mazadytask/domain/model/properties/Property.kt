package com.example.mazadytask.domain.model.properties

import com.example.mazadytask.domain.model.FormField

data class Property(val id: Int,val name: String,val children: List<Child>)

fun Property.toFormField(): FormField {
    return FormField(formFieldId = id,name = name, options = children.map { it.toOption() })
}