package com.kiencute.landmarkremark.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiencute.landmarkremark.data.entities.Entity
import com.kiencute.landmarkremark.data.repository.EntityRepository
import com.kiencute.landmarkremark.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: EntityRepository
) : ViewModel() {
    private val _entities = MutableStateFlow<Resource<List<Entity>>>(Resource.Loading())
    val entities: StateFlow<Resource<List<Entity>>> = _entities

    init {
        loadEntities()
    }

    private fun loadEntities() {
        viewModelScope.launch {
            repository.getEntitiesAsFlow().collect { data ->
                _entities.value = data as Resource<List<Entity>>
            }
        }
    }
}