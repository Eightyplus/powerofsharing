package no.soperasteria.powerofsharing

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import no.soperasteria.powerofsharing.fragment.SpeakersFragment
import no.soperasteria.powerofsharing.fragment.SplashFragment
import no.soperasteria.powerofsharing.fragment.StartFragment
import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.thread
import android.support.v7.app.AppCompatActivity as Activity

const val FRAGMENT_SPLASH = "SPLASH"
const val FRAGMENT_START = "START"
const val FRAGMENT_SPEAKERS = "SPEAKERS"

class MainActivity : Activity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_start -> {
                showHideStart(true)
                showHideSpeakers()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_speakers -> {
                showHideStart()
                showHideSpeakers(true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_schedule -> {
                showHideStart()
                showHideSpeakers()
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
            showHideStart(true)
            runOnUiThread {
                navigation.visibility = View.VISIBLE
            }
        }
    }

    private fun showHideSplash() {
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(R.anim.splash_enter, R.anim.splash_exit);
            supportFragmentManager.findFragmentByTag(FRAGMENT_SPLASH)?.let {
                remove(it)
            } ?: run {
                replace(R.id.splash, SplashFragment(), FRAGMENT_SPLASH)
            }
        }.commit()
    }


    private fun showHideStart(show: Boolean = false) {
        supportFragmentManager.beginTransaction().apply {
            if (show) {
                supportFragmentManager.findFragmentByTag(FRAGMENT_START)
                        ?: replace(R.id.main, StartFragment(), FRAGMENT_START)
            } else {
                supportFragmentManager.findFragmentByTag(FRAGMENT_START)?.let {
                    remove(it)
                }
            }
        }.commit()
    }

    private fun showHideSpeakers(show: Boolean = false) {
        supportFragmentManager.beginTransaction().apply {
            if (show) {
                supportFragmentManager.findFragmentByTag(FRAGMENT_SPEAKERS)
                        ?: replace(R.id.main, SpeakersFragment(), FRAGMENT_SPEAKERS)
            } else {
                supportFragmentManager.findFragmentByTag(FRAGMENT_SPEAKERS)?.let {
                    remove(it)
                }
            }
        }.commit()
    }
}
