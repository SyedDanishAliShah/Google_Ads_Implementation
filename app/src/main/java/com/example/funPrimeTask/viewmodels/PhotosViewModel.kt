package com.example.funPrimeTask.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.funPrimeTask.dataclasses.Photo
import com.example.funPrimeTask.objectclasses.RetrofitInstance
import kotlinx.coroutines.launch

class PhotosViewModel : ViewModel() {
    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>> get() = _photos

    fun fetchPhotos() {
        viewModelScope.launch {
            try {
                val photoList = RetrofitInstance.api.getPhotos()
                _photos.value = photoList
            } catch (e: Exception) {
                // Handle the error
                _photos.value = emptyList()
            }
        }
    }
}