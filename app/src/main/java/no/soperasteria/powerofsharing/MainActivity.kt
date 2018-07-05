package no.soperasteria.powerofsharing

import android.support.v7.app.AppCompatActivity as Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : Activity() {

    private var speakersAdapter: RecyclerAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        speakersAdapter = RecyclerAdapter(applicationContext) {
            runOnUiThread {
                speakersAdapter?.notifyDataSetChanged()
                recyclerView?.requestLayout()
            }
        }

        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
            this.adapter = speakersAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }
    }

}
