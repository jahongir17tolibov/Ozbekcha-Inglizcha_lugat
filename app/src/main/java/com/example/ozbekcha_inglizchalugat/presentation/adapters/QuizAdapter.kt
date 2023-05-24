package com.example.ozbekcha_inglizchalugat.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.ozbekcha_inglizchalugat.R
import com.example.ozbekcha_inglizchalugat.data.models.QuizModel
import com.example.ozbekcha_inglizchalugat.databinding.QuizAnswerLyItemBinding
import com.example.ozbekcha_inglizchalugat.databinding.QuizQuestionLyItemBinding

class QuizAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var quizQuestions = emptyList<QuizModel>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var selectedAnswer: String? = null
    private var onAnswerSelectedListener: ((String) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setSelectedAnswer(answer: String?) {
        selectedAnswer = answer
        notifyDataSetChanged()
    }

    fun setOnAnswerSelectionListener(listener: ((String) -> Unit)? = null) {
        onAnswerSelectedListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            QUESTION_VIEW_HOLDER -> {
                val binding = QuizQuestionLyItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                QuestionViewHolder(binding)
            }

            ANSWER_VIEW_TYPE -> {
                val binding = QuizAnswerLyItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                AnswerViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")

        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is QuestionViewHolder -> {
                val question = quizQuestions[position / 5]
                holder.bind(question)
            }

            is AnswerViewHolder -> {
                val context = holder.itemView.context
                val question = quizQuestions[position / 5]
                val answerIndex = position % 5 - 1
                val answer = question.options[answerIndex]
                holder.apply {
                    bind(answer, answer == selectedAnswer)
                    itemView.animation = AnimationUtils.loadAnimation(context, R.anim.scale)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int = if (position % 5 == 0) {
        QUESTION_VIEW_HOLDER
    } else {
        ANSWER_VIEW_TYPE
    }

    override fun getItemCount(): Int = quizQuestions.size * 5

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        if (holder is AnswerViewHolder) {
            holder.clearSelectedState()
        }
        super.onViewRecycled(holder)
    }

    inner class QuestionViewHolder(private val binding: QuizQuestionLyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(question: QuizModel) {
            binding.quizAnswersText.text = question.question
        }

    }

    inner class AnswerViewHolder(private val binding: QuizAnswerLyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(answer: String, isSelected: Boolean) = binding.apply {
            variantsText.text = answer
            root.isSelected = isSelected
            if (isSelected) {
                binding.root.setBackgroundResource(R.drawable.checked_variant_btn_bckg)
            } else {
                binding.root.setBackgroundResource(R.drawable.variants_btn_bckg)
            }
            root.setOnClickListener {
                onAnswerSelectedListener?.invoke(answer)
            }
        }

        fun clearSelectedState() {
            binding.root.isSelected = false
        }

    }

    companion object {
        private const val QUESTION_VIEW_HOLDER = 0
        private const val ANSWER_VIEW_TYPE = 1
    }

}