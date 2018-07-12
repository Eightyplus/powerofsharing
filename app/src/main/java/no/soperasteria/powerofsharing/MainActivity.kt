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
    private fun showStart() = showFragment(FRAGMENT_START, StartFragment())
    private fun hideStart() = hideFragment(FRAGMENT_START)
    private fun showSpeakers() = showFragment(FRAGMENT_SPEAKERS, SpeakersFragment())
    private fun hideSpeakers() = hideFragment(FRAGMENT_SPEAKERS)

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_start -> {
                showStart()
                hideSpeakers()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_speakers -> {
                hideStart()
                showSpeakers()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_schedule -> {
                hideStart()
                hideSpeakers()
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
            showStart()
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
}
