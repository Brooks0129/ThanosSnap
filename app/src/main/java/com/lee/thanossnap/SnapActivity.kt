package com.lee.thanossnap

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_snap.*
import kotlinx.android.synthetic.main.content_snap.*

class SnapActivity : AppCompatActivity() {
    private val avengersAdapter = AvengersAdapter(this)
    private var list: MutableList<Avenger> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snap)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Thanos snap his finger", Snackbar.LENGTH_SHORT).show()
            disappearAHalf()
        }
        avengersAdapter.mData = generateData()
        recycler_view.adapter = avengersAdapter
        val itemAnimation = DisappearItemAnimation()
        itemAnimation.removeDuration = 2000
        recycler_view.itemAnimator = itemAnimation
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)

    }

    private fun disappearAHalf() {
        list.removeAt(2)
        avengersAdapter.notifyItemRemoved(2)
    }


    private fun generateData(): List<Avenger> {

        list.add(Avenger("Iron Man", "1"))
        list.add(Avenger("Black Widow", "2"))
        list.add(Avenger("Captain America", "3"))
        list.add(Avenger("Spider Man", "4"))
        list.add(Avenger("Thor", "5"))
        list.add(Avenger("Hulk", "6"))
        list.add(Avenger("Black Panther", "7"))
        list.add(Avenger("Doctor Stranger", "8"))
        list.add(Avenger("Clint", "9"))
        list.add(Avenger("Drax", "10"))
        list.add(Avenger("Groot", "11"))
        list.add(Avenger("Loki", "12"))
        list.add(Avenger("Winter", "13"))
        list.add(Avenger("Mantis", "14"))
        list.add(Avenger("Nebula", "15"))
        list.add(Avenger("Vision", "16"))
        return list
    }

}
