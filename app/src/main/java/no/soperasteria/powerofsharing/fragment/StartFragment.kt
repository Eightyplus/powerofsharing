package no.soperasteria.powerofsharing.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_start.*
import no.soperasteria.powerofsharing.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.fixedRateTimer

class StartFragment : DialogFragment() {

    private val mHandler = Handler(Looper.getMainLooper())
    private val dateFormatter = SimpleDateFormat("D-HH:mm:ss", Locale.getDefault())

    private var timer: Timer? = null
    private val calendar: Calendar = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, 23)
        set(Calendar.MONTH, 4)
        set(Calendar.YEAR, 0)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timer = fixedRateTimer("counter", true, Date(), 1000) {
            calendar.add(Calendar.SECOND, -1)
            update(dateFormatter.format(calendar.time))
        }
    }

    private fun update(text: String) {
        mHandler.post {
            counter.text = text
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}