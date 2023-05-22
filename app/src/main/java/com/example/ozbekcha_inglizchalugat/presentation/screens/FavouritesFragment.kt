package com.example.ozbekcha_inglizchalugat.presentation.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.ozbekcha_inglizchalugat.R
import com.example.ozbekcha_inglizchalugat.databinding.FragmentFavouritesBinding
import com.example.ozbekcha_inglizchalugat.presentation.adapters.DictionaryEngAdapter
import com.example.ozbekcha_inglizchalugat.presentation.viewmodels.FavouritesViewModel
import com.example.ozbekcha_inglizchalugat.utils.Constants.LOG_TXT
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {
    private var _binding: FragmentFavouritesBinding? = null
    private lateinit var binding: FragmentFavouritesBinding

    private val viewModel by viewModel<FavouritesViewModel>()
    private val dictionaryEngAdapter by lazy { DictionaryEngAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavouritesBinding.bind(view)
        _binding = binding

        initLiveData()
        viewModel.getAllFavourites()

    }

    private fun initLiveData() = viewModel.run {
        favouriteWords.onEach {
            Log.d(LOG_TXT, "initLiveData: $it")
        }.launchIn(lifecycleScope)

        countedData.observe(viewLifecycleOwner) {
            binding.toolbar.title = "Favourites - $it"
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}