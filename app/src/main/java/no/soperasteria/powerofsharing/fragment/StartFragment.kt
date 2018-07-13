package no.soperasteria.powerofsharing.fragment

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import no.soperasteria.powerofsharing.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.fixedRateTimer

class StartFragment : DialogFragment() {

    private val datePattern = "D-HH:mm:ss"
    private val dateFormatter = SimpleDateFormat(datePattern, Locale.getDefault())

    private var timer: Timer? = null
    private val calendar: Calendar = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, 23)
        set(Calendar.MONTH, 4)
        set(Calendar.YEAR, 0)
    }

    private val startTime: Date by lazy {
        Calendar.getInstance().apply {
            add(Calendar.SECOND, 5)
        }.time
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val counter = childFragmentManager.findFragmentById(R.id.counter) as CounterFragment
        counter.init(getString(R.string.welcome))

        timer = fixedRateTimer("counter", true, startTime, 1000) {
            calendar.add(Calendar.SECOND, -1)
            counter.text = dateFormatter.format(calendar.time)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}