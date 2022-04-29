package com.example.mazadytask.domain.model

data class FormField(val formFieldId: Int,val name:String,var isChild: Boolean = false,val options: List<Option>)

const val MAIN_CATEGORY = 1000
const val SUB_CATEGORY = 1001

