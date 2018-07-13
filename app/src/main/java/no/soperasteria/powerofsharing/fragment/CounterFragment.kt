package no.soperasteria.powerofsharing.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.LinearLayout
import android.widget.TextView
import no.soperasteria.powerofsharing.R
import no.soperasteria.powerofsharing.view.TextFlip
import no.soperasteria.powerofsharing.view.animateIn
import no.soperasteria.powerofsharing.view.animateOut


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

    private lateinit var animateIn: Animation
    private lateinit var animateOut: Animation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        linearLayout = inflater.inflate(R.layout.fragment_counter, container, false) as LinearLayout
        animateIn = animateIn(layoutInflater.context)
        animateOut = animateOut(layoutInflater.context)
        return linearLayout
    }

    fun init(text: String) {
        for (char in text) {
            create(char)
        }
    }

    private fun visualize(text: String) {
        val chars: CharArray = text.toCharArray()
        for (i in 0..(chars.size-1)) {
            val char = chars[i]
            val textFlip = textViewFor(i)

            if (textFlip.currentChar != char) {
                textFlip.currentChar = char
                textFlip.visibleText.animation = animateIn
            }
        }
        animateIn.start()
    }

    private fun textViewFor(index: Int): TextFlip {
        return if (index < textViews.size) {
            textViews[index]
        } else {
            create('!')
        }
    }

    private fun create(char: Char): TextFlip =
            (layoutInflater.inflate(R.layout.textview_counter, linearLayout, false) as LinearLayout).run {
                linearLayout.addView(this)
                TextFlip(findViewById(R.id.hidden), findViewById(R.id.visible)).apply {
                    currentChar = char
                    textViews.add(this)
                }
            }

    private fun animateText(textView: TextView, text: String) {
        textView.animation = animateIn
        textView.text = text
    }

}