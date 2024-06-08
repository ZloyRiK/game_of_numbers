package com.example.gameofnumbers.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.gameofnumbers.R
import com.example.gameofnumbers.domain.entity.GameResult

@BindingAdapter("requireAnswers")
fun bindRequireAnswers(view: TextView, count: Int) {
    view.text = view.context.getString(R.string.required_score, count)
}

@BindingAdapter("scoreAnswers")
fun bindScoreAnswers(view: TextView, count: Int) {
    view.text = view.context.getString(R.string.score_answers, count)
}

@BindingAdapter("requirePercentage")
fun bindRequirePercentage(view: TextView, count: Int) {
    view.text = view.context.getString(R.string.required_percentage, count)
}

@BindingAdapter("resultEmoji")
fun bindResultEmoji(view: ImageView, winner: Boolean) {
    view.setImageResource(getEmoji(winner))
}
@BindingAdapter("scorePercentage")
fun bindScorePercentage(view: TextView, gameResult: GameResult) {
    view.text =
        view.context.getString(R.string.score_percentage, getPercentageOfRightAnswers(gameResult))
}

private fun getPercentageOfRightAnswers(gameResult: GameResult) = with(gameResult) {
    if (countOfQuestions == 0) {
        0
    } else {
        ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
}

private fun getEmoji(winner: Boolean) = if (winner) {
    R.drawable.ic_smile
} else {
    R.drawable.ic_sad
}