package no.soperasteria.powerofsharing

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import no.soperasteria.powerofsharing.data.database
import no.soperasteria.powerofsharing.fragment.SpeakersFragment
import no.soperasteria.powerofsharing.fragment.SplashFragment
import org.jetbrains.anko.db.replace
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
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_speakers -> {
                showHideSpeakers()
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
            readSpeakers().let { speakers ->
                database.use {
                    for (speaker in speakers) {

                        replace("Speaker",
                                "name" to speaker.name,
                                "post" to speaker.post,
                                "photo" to speaker.photo,
                                "details" to speaker.details)
                    }

                }
            }
            Log.d(this.javaClass.name, "Done reading speakers to database")
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        showHideSplash()
        Timer().schedule(3000) {
            showHideSplash()
        }
    }

    private fun showHideSpeakers(hide: Boolean = false) {
        supportFragmentManager.beginTransaction().apply {
            supportFragmentManager.findFragmentByTag(FRAGMENT_SPEAKERS)?.let {
                if (hide) {
                    remove(it)
                }
            } ?: run {
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
