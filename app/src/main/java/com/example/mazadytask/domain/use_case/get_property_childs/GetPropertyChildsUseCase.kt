package com.example.mazadytask.domain.use_case.get_property_childs

import com.example.mazadytask.common.Resource
import com.example.mazadytask.data.remote.dto.properties.toProperty
import com.example.mazadytask.domain.model.properties.Property
import com.example.mazadytask.domain.repository.MazadyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPropertyChildsUseCase @Inject constructor(private val repository: MazadyRepository) {

    operator fun invoke(propertyId: Int): Flow<Resource<List<Property>>> = flow {
        try {
            emit(Resource.Loading())
            val categories = repository.getPropertyChilds(propertyId).data.map { it.toProperty() }
            emit(Resource.Success(categories))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}