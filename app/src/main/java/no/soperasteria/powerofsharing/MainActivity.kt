package no.soperasteria.powerofsharing

import android.support.v7.app.AppCompatActivity as Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    private lateinit var speakersAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        speakersAdapter = RecyclerAdapter(applicationContext) {
            runOnUiThread {
                speakersAdapter.notifyDataSetChanged()
                recycler_view.requestLayout()
            }
        }

        recycler_view.apply {
            this.adapter = speakersAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }
    }

}
