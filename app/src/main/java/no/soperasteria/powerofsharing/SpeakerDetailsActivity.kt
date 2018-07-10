package no.soperasteria.powerofsharing

import android.os.Bundle
import android.support.v7.app.AppCompatActivity as Activity
import kotlinx.android.synthetic.main.activity_details.*

const val EXTRA_SPEAKER = "speaker"

class SpeakerDetailsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val speaker = intent.extras.getSerializable(EXTRA_SPEAKER) as Speaker
        speaker_name.text = speaker.name
        speaker_post.text = speaker.post
        speaker_details.text = speaker.details

        val file = photoFile(applicationContext.filesDir, fileForLink(speaker.photo))
        drawableFor(file)?.let {
            speaker_image.setImageDrawable(it)
        }
    }
}
