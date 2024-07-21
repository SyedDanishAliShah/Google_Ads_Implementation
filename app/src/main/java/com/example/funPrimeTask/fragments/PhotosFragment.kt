package com.example.funPrimeTask.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.funprimetask.R
import com.example.funPrimeTask.adapters.PhotoAdapter
import com.example.funPrimeTask.objectclasses.RetrofitInstance
import kotlinx.coroutines.launch

class PhotosFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhotoAdapter
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photos, container, false)
        recyclerView = view.findViewById(R.id.photos_rv)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize NavController
        navController = findNavController()

        // Pass NavController to the adapter
        adapter = PhotoAdapter(requireContext(), findNavController())
        recyclerView.adapter = adapter

        fetchPhotos()
        return view
    }

    private fun fetchPhotos() {
        val apiService = RetrofitInstance.api
        lifecycleScope.launch {
            try {
                val photos = apiService.getPhotos()
                adapter.setData(photos)
            } catch (e: Exception) {
                // Handle error
                Log.e("PhotosFragment", "Error fetching photos: ${e.message}")
            }
        }
    }
}