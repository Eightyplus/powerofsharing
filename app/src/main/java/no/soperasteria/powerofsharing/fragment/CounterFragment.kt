package no.soperasteria.powerofsharing.fragment

import android.animation.Animator
import android.animation.AnimatorSet
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import no.soperasteria.powerofsharing.R
import no.soperasteria.powerofsharing.view.TextFlip

class CounterFragment : DialogFragment() {

    private val mHandler = Handler(Looper.getMainLooper())
    private lateinit var linearLayout: LinearLayout
    private var textViews = mutableListOf<TextFlip>()

    var text: String = ""
        set(value) {
            field = value
            mHandler.post {
                visualize(value)
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        linearLayout = inflater.inflate(R.layout.fragment_counter, container, false) as LinearLayout
        return linearLayout
    }

    fun init(text: String) {
        for (char in text) {
            create(char)
        }
    }

    private fun visualize(text: String) {
        val list = mutableListOf<Animator>()
        val chars: CharArray = text.toCharArray()
        for (i in 0..(chars.size-1)) {
            textViewFor(i).animate(chars[i])?.let {
                list.add(it)
            }
        }

        AnimatorSet().apply {
            playTogether(list)
        }.start()
    }

    private fun textViewFor(index: Int): TextFlip {
        return if (index < textViews.size) {
            textViews[index]
        } else {
            create(' ')
        }
    }

    private fun create(char: Char): TextFlip =
            layoutInflater.inflate(R.layout.textview_counter, linearLayout, false).run {
                linearLayout.addView(this)
                TextFlip(findViewById(R.id.text1), findViewById(R.id.text2)).apply {
                    init(char)
                    textViews.add(this)
                }
            }

}