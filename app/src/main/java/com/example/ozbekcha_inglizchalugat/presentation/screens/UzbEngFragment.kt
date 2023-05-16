package com.example.ozbekcha_inglizchalugat.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ozbekcha_inglizchalugat.R
import com.example.ozbekcha_inglizchalugat.databinding.FragmentUzbEngBinding

class UzbEngFragment : Fragment(R.layout.fragment_uzb_eng) {
    private var _binding: FragmentUzbEngBinding? = null
    private lateinit var binding: FragmentUzbEngBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUzbEngBinding.bind(view)
        _binding = binding


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}