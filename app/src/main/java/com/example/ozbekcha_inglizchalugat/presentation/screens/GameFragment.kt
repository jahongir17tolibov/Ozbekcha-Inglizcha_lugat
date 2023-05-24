package com.example.ozbekcha_inglizchalugat.presentation.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ozbekcha_inglizchalugat.R
import com.example.ozbekcha_inglizchalugat.databinding.FragmentGameBinding
import com.example.ozbekcha_inglizchalugat.presentation.adapters.QuizAdapter
import com.example.ozbekcha_inglizchalugat.presentation.viewmodels.QuizViewModel
import com.example.ozbekcha_inglizchalugat.utils.Constants.LOG_TXT
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameFragment : Fragment(R.layout.fragment_game) {
    private var _binding: FragmentGameBinding? = null
    private lateinit var binding: FragmentGameBinding

    private val viewModel by viewModel<QuizViewModel>()
    private lateinit var quizAdapter: QuizAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGameBinding.bind(view)
        _binding = binding

        quizAdapter = QuizAdapter()

        setupRecycler()
        initLiveData()
        //start quiz
        viewModel.startQuiz()
        initClicks()

    }

    private fun initLiveData() = viewModel.run {
        quizQuestions.observe(viewLifecycleOwner) {
            quizAdapter.quizQuestions = it
            Log.d(LOG_TXT, "initLiveData: $it")
        }

        currentQuestionIndex.observe(viewLifecycleOwner) { currentQues ->
            binding.toolbar.title = currentQues.toString()
        }
    }

    private fun initClicks() {
        binding.nextBtn.setOnClickListener {
            viewModel.nextQuestion()
        }
    }

    private fun setupRecycler() = binding.quizList.apply {
        layoutManager = LinearLayoutManager(requireContext())
        overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        adapter = quizAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}