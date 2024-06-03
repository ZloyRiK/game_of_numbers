package com.example.gameofnumbers.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.gameofnumbers.R
import com.example.gameofnumbers.data.GameRepositoryImpl
import com.example.gameofnumbers.databinding.FragmentGameBinding
import com.example.gameofnumbers.domain.entity.GameResult
import com.example.gameofnumbers.domain.entity.GameSettings
import com.example.gameofnumbers.domain.entity.Level

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private lateinit var level: Level
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun parseArgs() {
        requireArguments().getParcelable<Level>(LEVEL_KEY)?.let {
            level = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvOption1.setOnClickListener {
            launchGameFinishFragment(
                GameResult(
                    false,
                    2,
                    10,
                    GameSettings(0,0,0,0)
                )
            )
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