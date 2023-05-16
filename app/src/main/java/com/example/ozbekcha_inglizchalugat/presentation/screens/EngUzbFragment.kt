package com.example.ozbekcha_inglizchalugat.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ozbekcha_inglizchalugat.R
import com.example.ozbekcha_inglizchalugat.databinding.FragmentEngUzbBinding

class EngUzbFragment : Fragment(R.layout.fragment_eng_uzb) {
    private var _binding: FragmentEngUzbBinding? = null
    private lateinit var binding: FragmentEngUzbBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEngUzbBinding.bind(view)
        _binding = binding


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}