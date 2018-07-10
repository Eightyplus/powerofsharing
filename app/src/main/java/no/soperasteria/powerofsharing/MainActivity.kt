package no.soperasteria.powerofsharing

import android.content.Intent
import android.support.v7.app.AppCompatActivity as Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    private lateinit var speakersAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        speakersAdapter = RecyclerAdapter(applicationContext, ::update, ::show)

        recycler_view.apply {
            this.adapter = speakersAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }
    }

    private fun update() {
        runOnUiThread {
            speakersAdapter.notifyDataSetChanged()
            recycler_view.requestLayout()
        }
    }

    private fun show(speaker: Speaker?) {
        if (speaker != null) {
            startActivity(Intent(this, SpeakerDetailsActivity::class.java).apply {
                putExtra(EXTRA_SPEAKER, speaker)
            })
        } else {
            Toast.makeText(applicationContext, R.string.error_showing_speaker, Toast.LENGTH_LONG).show()
        }

    }

}
