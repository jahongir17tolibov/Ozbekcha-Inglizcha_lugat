package com.example.ozbekcha_inglizchalugat.presentation.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ozbekcha_inglizchalugat.R
import com.example.ozbekcha_inglizchalugat.data.models.DictionaryModel
import com.example.ozbekcha_inglizchalugat.databinding.FragmentUzbEngBinding
import com.example.ozbekcha_inglizchalugat.domain.resource.DictionaryStateUI
import com.example.ozbekcha_inglizchalugat.presentation.adapters.DictionaryUzbAdapter
import com.example.ozbekcha_inglizchalugat.presentation.viewmodels.DictionaryDataViewModel
import com.example.ozbekcha_inglizchalugat.utils.BaseUtils.showSnackToast
import com.example.ozbekcha_inglizchalugat.utils.Constants.LOG_TXT
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class UzbEngFragment : Fragment(R.layout.fragment_uzb_eng) {
    private var _binding: FragmentUzbEngBinding? = null
    private lateinit var binding: FragmentUzbEngBinding

    private val viewModel by viewModel<DictionaryDataViewModel>()
    private val dictionaryUzbAdapter by lazy { DictionaryUzbAdapter() }
    private var list = emptyList<DictionaryModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUzbEngBinding.bind(view)
        _binding = binding

        initStateFlow()
        initLoadData()
        setupRecycler()
        initClicks()

    }

    private fun initLoadData() = viewModel.loadDictionaryData()

    private fun initStateFlow() = viewModel.dictionaryData.onEach { state ->
        when (state) {
            is DictionaryStateUI.Success -> {
                val sortedData = state.dictionaryData.sortedBy { it.uzbek }
                list = state.dictionaryData
                dictionaryUzbAdapter.baseList = sortedData
                showProgress(false)
            }

            is DictionaryStateUI.Loading -> showProgress(true)

            is DictionaryStateUI.Error -> {
                showProgress(false)
                showSnackToast(state.errorMessage)
            }

        }
    }.launchIn(lifecycleScope)

    private fun setupRecycler() = binding.sozlarList.apply {
        layoutManager = LinearLayoutManager(requireContext())
        setHasFixedSize(true)
        overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        adapter = dictionaryUzbAdapter
    }

    private fun initClicks() {
        dictionaryUzbAdapter.setOnItemClickListener {
            it.checkFavourite()
        }
    }

    private fun showProgress(state: Boolean) {
        binding.progressInd.isVisible = state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}