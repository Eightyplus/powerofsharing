package no.soperasteria.powerofsharing

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import no.soperasteria.powerofsharing.fragment.SpeakersFragment
import no.soperasteria.powerofsharing.fragment.SplashFragment
import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.thread
import android.support.v7.app.AppCompatActivity as Activity

const val FRAGMENT_SPLASH = "SPLASH"
const val FRAGMENT_SPEAKERS = "SPEAKERS"

class MainActivity : Activity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_start -> {
                showHideSpeakers()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_speakers -> {
                showHideSpeakers(true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_schedule -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread {
            readSpeakers(applicationContext)
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        showHideSplash()
        Timer().schedule(3000) {
            showHideSplash()
        }
    }

    private fun showHideSpeakers(show: Boolean = false) {
        supportFragmentManager.beginTransaction().apply {
            supportFragmentManager.findFragmentByTag(FRAGMENT_SPEAKERS)?.let {
                remove(it)
            } ?: if (show) {
                replace(R.id.main, SpeakersFragment(), FRAGMENT_SPEAKERS)
            }
        }.commit()
    }

    private fun showHideSplash() {
        supportFragmentManager.beginTransaction().apply {
            supportFragmentManager.findFragmentByTag(FRAGMENT_SPLASH)?.let {
                remove(it)
            } ?: run {
                replace(R.id.splash, SplashFragment(), FRAGMENT_SPLASH)
            }
        }.commit()
    }
}
