package com.example.gameofnumbers.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gameofnumbers.R
import com.example.gameofnumbers.databinding.FragmentGameBinding
import com.example.gameofnumbers.domain.entity.GameResult
import com.example.gameofnumbers.domain.entity.Level

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private lateinit var level: Level

    private val viewModelFactory by lazy {
        GameViewModelFactory(requireActivity().application, level)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    private val answersOptions = mutableListOf<Int>()
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        requireArguments().getParcelable<Level>(LEVEL_KEY)?.let {
            level = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        initClickListeners()

    }

    private fun observeViewModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            answersOptions.clear()
            answersOptions.addAll(it.options)
            binding.tvOption1.text = answersOptions[0].toString()
            binding.tvOption2.text = answersOptions[1].toString()
            binding.tvOption3.text = answersOptions[2].toString()
            binding.tvOption4.text = answersOptions[3].toString()
            binding.tvOption5.text = answersOptions[4].toString()
            binding.tvOption6.text = answersOptions[5].toString()
        }

        viewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }

        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }

        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
        }

        viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.tvAnswersProgress.setTextColor(color)
        }

        viewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }

        viewModel.minPercentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }

        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishFragment(it)
        }
    }

    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if (goodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    private fun initClickListeners() {
        with(binding) {
            tvOption1.setOnClickListener {
                viewModel.chooseAnswer(answersOptions[0])
            }
            tvOption2.setOnClickListener {
                viewModel.chooseAnswer(answersOptions[1])
            }
            tvOption3.setOnClickListener {
                viewModel.chooseAnswer(answersOptions[2])
            }
            tvOption4.setOnClickListener {
                viewModel.chooseAnswer(answersOptions[3])
            }
            tvOption5.setOnClickListener {
                viewModel.chooseAnswer(answersOptions[4])
            }
            tvOption6.setOnClickListener {
                viewModel.chooseAnswer(answersOptions[5])
            }
        }
    }

    private fun launchGameFinishFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newGameFinishInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    companion object {

        const val NAME = "GameFragment"

        private const val LEVEL_KEY = "level"

        fun newGameInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(LEVEL_KEY, level)
                }
            }
        }
    }
}