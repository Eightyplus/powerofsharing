package no.soperasteria.powerofsharing.view

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.widget.TextView
import no.soperasteria.powerofsharing.R

fun height (context: Context) = context.resources.getDimension(R.dimen.counterHeight)

class TextFlip (val text1: TextView, val text2: TextView) {

    private val animateIn = AnimatorInflater.loadAnimator(text1.context, R.animator.flip) as AnimatorSet

    fun init(char: Char) {
        currentChar = char
        text1.text = " "
        text2.text = "$char"
    }

    private var currentChar: Char = '$'

    fun animate(char: Char) : Animator? {
        if (currentChar == char) return null
        currentChar = char

        text1.clearAnimation()
        text2.text = text1.text

        animateIn.setTarget(text1)
        text1.text = "$currentChar"

        return animateIn
    }
}
