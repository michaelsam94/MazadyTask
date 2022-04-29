package com.example.mazadytask.domain.use_case.get_categories

import com.example.mazadytask.common.Resource
import com.example.mazadytask.data.remote.dto.categories.toCategory
import com.example.mazadytask.domain.model.categories.Category
import com.example.mazadytask.domain.repository.MazadyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val repository: MazadyRepository) {

    operator fun invoke(): Flow<Resource<List<Category>>> = flow {
        try {
            emit(Resource.Loading())
            val categories = repository.getCategories().data.categories.map { it.toCategory() }
            emit(Resource.Success(categories))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}