package no.soperasteria.powerofsharing.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.TextView
import no.soperasteria.powerofsharing.R


class CounterFragment : DialogFragment() {

    private val mHandler = Handler(Looper.getMainLooper())
    private lateinit var linearLayout: LinearLayout
    private var textViews = mutableListOf<TextView>()

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
            create()
        }
    }

    private fun visualize(text: String) {
        val chars: CharArray = text.toCharArray()
        for (i in 0..(chars.size-1)) {
            val char = chars[i].toString()

            if (textViewFor(i).text != char) {
                animateText(textViewFor(i), char)
            }
        }
    }

    private fun textViewFor(index: Int): TextView {
        return if (index < textViews.size) {
            textViews[index]
        } else {
            create()
        }
    }

    private fun create(): TextView =
            (layoutInflater.inflate(R.layout.textview_counter, linearLayout, false) as TextView).apply {
                linearLayout.addView(this)
                textViews.add(this)
            }

    private fun animateText(textView: TextView, text: String) {
        val animation = TranslateAnimation(0f, 0f, -textView.height.toFloat(), 0f).apply {
            duration = 750
        }
        textView.startAnimation(animation)
        textView.text = text
    }

}