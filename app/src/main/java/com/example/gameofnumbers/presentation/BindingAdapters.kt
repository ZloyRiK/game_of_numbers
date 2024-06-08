package com.example.gameofnumbers.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.gameofnumbers.R
import com.example.gameofnumbers.domain.entity.GameResult

interface OnOptionClickListener {
    fun onOptionClick(option: Int)
}

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

@BindingAdapter("numberAsText")
fun bindNumberAsText(view: TextView, number: Int) {
    view.text = number.toString()
}

@BindingAdapter("enoughCount")
fun bindEnoughCount(view: TextView, enough: Boolean) {
    view.setTextColor(getColorByState(view.context, enough))
}

@BindingAdapter("enoughPercent")
fun bindEnoughPercent(progressBar: ProgressBar, enough: Boolean) {
    progressBar.progressTintList = ColorStateList.valueOf(getColorByState(progressBar.context, enough))
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(view: TextView, clickListener: OnOptionClickListener) {
    view.setOnClickListener {
        clickListener.onOptionClick(view.text.toString().toInt())
    }
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

private fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorResId = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}