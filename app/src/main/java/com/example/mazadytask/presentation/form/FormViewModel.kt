package com.example.mazadytask.presentation.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mazadytask.common.Resource
import com.example.mazadytask.domain.use_case.get_categories.GetCategoriesUseCase
import com.example.mazadytask.domain.use_case.get_properties.GetPropertiesUseCase
import com.example.mazadytask.domain.use_case.get_property_childs.GetPropertyChildsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getPropertiesUseCase: GetPropertiesUseCase,
    private val getPropertyChilds: GetPropertyChildsUseCase
) : ViewModel() {

    private val _state = MutableLiveData<FormState>()
    val state: LiveData<FormState> = _state

    init {
        getCategories()
    }

    private fun getCategories() {
        getCategoriesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = FormState(categories = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = FormState(error= result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    _state.value = FormState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getProperties(catId: Int) {
        getPropertiesUseCase(catId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = FormState(properties = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = FormState(error= result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    _state.value = FormState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getPropertyChild(propertyId: Int) {
        getPropertyChilds(propertyId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = FormState(childProperty = result.data?.get(0))
                }
                is Resource.Error -> {
                    _state.value = FormState(error= result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    _state.value = FormState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}