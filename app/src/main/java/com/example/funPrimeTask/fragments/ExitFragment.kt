package com.example.funPrimeTask.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.funprimetask.R

class ExitFragment : Fragment() {
    private lateinit var exitButton: Button
    private lateinit var goBackButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exitButton = view.findViewById(R.id.exit_button)
        goBackButton = view.findViewById(R.id.go_back_button)

        exitButton.setOnClickListener{
            activity?.finishAffinity()
        }

        goBackButton.setOnClickListener {
            // Pop the current fragment (ExitFragment) from the back stack
            findNavController().popBackStack(R.id.exitScreenFragment_graph, false)

            // Navigate to the PhotosFragment
            findNavController().navigate(R.id.action_exitFragment_to_photosFragment_graph)
        }

    }


}