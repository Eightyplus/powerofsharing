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
import java.util.*
import kotlin.concurrent.fixedRateTimer

class StartFragment : DialogFragment() {

    var timer: Timer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var cnt = 0
        val mHandler = Handler(Looper.getMainLooper())
        this.timer = fixedRateTimer("counter", true, Date(), 1000) {
            mHandler.post {
                counter?.text = "Tick $cnt"
            }
            cnt++
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}