package com.daakknights.room

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class DreamViewModel(private val repository: DreamRepository) : ViewModel() {

    val allDreams: LiveData<List<Dream>> = repository.allDreams.asLiveData()


    fun insert(dream: Dream) = viewModelScope.launch {
        repository.insert(dream)
    }
}

class DreamViewModelFactory(private val repository: DreamRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DreamViewModel::class.java)) {
            return DreamViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}