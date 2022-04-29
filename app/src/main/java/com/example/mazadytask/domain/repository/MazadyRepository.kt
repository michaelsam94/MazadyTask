package com.example.mazadytask.domain.repository

import com.example.mazadytask.data.remote.dto.categories.CategoriesRes
import com.example.mazadytask.data.remote.dto.properties.PropertiesRes

interface MazadyRepository {

    suspend fun getCategories(): CategoriesRes

    suspend fun getProperties(catId: Int): PropertiesRes

    suspend fun getPropertyChilds(propertyId: Int): PropertiesRes


}