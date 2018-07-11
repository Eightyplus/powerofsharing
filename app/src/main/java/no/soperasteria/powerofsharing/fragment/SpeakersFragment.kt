package no.soperasteria.powerofsharing.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_speakers.*
import no.soperasteria.powerofsharing.*

class SpeakersFragment : DialogFragment() {

    private lateinit var speakersAdapter: RecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_speakers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val context = activity?.applicationContext ?: view.context

        speakersAdapter = RecyclerAdapter(context, ::update, ::show)

        recycler_view.apply {
            this.adapter = speakersAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun update() {
        activity?.runOnUiThread {
            speakersAdapter.notifyDataSetChanged()
            recycler_view.requestLayout()
            spinner.visibility = View.GONE
        }
    }

    private fun show(speaker: Speaker?) {
        activity?.let {
            if (speaker != null) {
                it.startActivity(Intent(it, SpeakerDetailsActivity::class.java).apply {
                    putExtra(EXTRA_SPEAKER, speaker)
                })
            } else {
                Toast.makeText(it.applicationContext, R.string.error_showing_speaker, Toast.LENGTH_LONG).show()
            }
        }
    }

}


