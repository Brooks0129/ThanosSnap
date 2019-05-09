package com.lee.thanossnap

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_snap.*
import kotlinx.android.synthetic.main.content_snap.*
import java.util.*
import kotlin.random.Random

class SnapActivity : AppCompatActivity() {
    private val avengersAdapter = AvengersAdapter(this)
    private var list: MutableList<Avenger> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snap)
        setSupportActionBar(toolbar)


        fab.setOnClickListener {
            val animationDrawable = fab.drawable as AnimationDrawable
            animationDrawable.start()
            disappearAHalf()
        }
        avengersAdapter.mData = generateData()
        recycler_view.adapter = avengersAdapter
        val itemAnimation = DisappearItemAnimation()
        itemAnimation.removeDuration = 4000
        recycler_view.itemAnimator = itemAnimation
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)

    }


    private fun disappearAHalf() {

        if (list.isNotEmpty()) {

            val size = list.size
            val set = TreeSet<Int>(kotlin.Comparator { o1, o2 ->
                o2 - o1
            })

            while (set.size < size / 2) {

                val index = Random.nextInt(size)
                set.add(index)
            }



            for (i in set) {
                list.removeAt(i)
                avengersAdapter.notifyItemRemoved(i)
            }


        }
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
