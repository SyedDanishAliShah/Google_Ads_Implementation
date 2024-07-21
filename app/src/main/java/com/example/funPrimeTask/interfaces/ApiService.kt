package com.example.funPrimeTask.interfaces

import com.example.funPrimeTask.dataclasses.Photo
import retrofit2.http.GET

interface ApiService {
    @GET("photos")
    suspend fun getPhotos(): List<Photo>
}