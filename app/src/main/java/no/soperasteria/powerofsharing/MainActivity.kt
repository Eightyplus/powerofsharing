package no.soperasteria.powerofsharing

import android.support.v7.app.AppCompatActivity as Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : Activity() {

    private var adapter: RecyclerAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = RecyclerAdapter(applicationContext) {
            runOnUiThread {
                adapter!!.notifyDataSetChanged()
                recyclerView.requestLayout()
            }
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        this.adapter = adapter
        this.recyclerView = recyclerView
    }

}
