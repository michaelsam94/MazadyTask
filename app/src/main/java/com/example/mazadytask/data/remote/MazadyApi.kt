package com.example.mazadytask.data.remote

import com.example.mazadytask.data.remote.dto.categories.CategoriesRes
import com.example.mazadytask.data.remote.dto.properties.PropertiesRes
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MazadyApi {

    @GET("api/get_all_cats")
    suspend fun getCategories(): CategoriesRes

    @GET("api/properties")
    suspend fun getProperties(@Query("cat") categoryId: Int): PropertiesRes

    @GET("get-options-child/{id}")
    suspend fun getPropertyChilds(@Path("id") id: Int): PropertiesRes

}