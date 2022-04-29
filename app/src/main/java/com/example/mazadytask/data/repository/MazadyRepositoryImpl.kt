package com.example.mazadytask.data.repository

import com.example.mazadytask.data.remote.MazadyApi
import com.example.mazadytask.data.remote.dto.categories.CategoriesRes
import com.example.mazadytask.data.remote.dto.properties.PropertiesRes
import com.example.mazadytask.domain.repository.MazadyRepository
import javax.inject.Inject

class MazadyRepositoryImpl @Inject constructor(
    private val api: MazadyApi
) : MazadyRepository {

    override suspend fun getCategories(): CategoriesRes {
        return api.getCategories()
    }

    override suspend fun getProperties(catId: Int): PropertiesRes {
        return api.getProperties(catId)
    }

    override suspend fun getPropertyChilds(propertyId: Int): PropertiesRes {
        return api.getPropertyChilds(propertyId)
    }
}