package no.soperasteria.powerofsharing.view

import android.content.Context
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.TextView
import no.soperasteria.powerofsharing.R

fun height (context: Context) = context.resources.getDimension(R.dimen.counterHeight)

fun animateIn (context: Context) : Animation =
        TranslateAnimation(0f, 0f, -height(context), 0f).apply {
            duration = 750
        }

fun animateOut (context: Context) : Animation =
        TranslateAnimation(0f, 0f, 0f, height(context)).apply {
            duration = 750
        }

class TextFlip (val hiddenText: TextView, val visibleText: TextView) {

    var currentChar: Char = '$'
        set(value) {
            field = value
            visibleText.text = "$value"
            hiddenText.text = "$value"
        }
}