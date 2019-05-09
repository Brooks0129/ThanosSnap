package com.lee.thanossnap

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lee.avengergone.DisappearView


class AvengersAdapter(activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mActivity = activity
    var mData: List<Avenger> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(mActivity).inflate(R.layout.item_avengers, parent, false)
        return AvengersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val avengersViewHolder = holder as AvengersViewHolder
        avengersViewHolder.textView.text = mData[position].name
        val id = mActivity.resources.getIdentifier("p${mData[position].image}", "drawable", mActivity.packageName)
        avengersViewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(mActivity, id))
    }
}

class AvengersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var imageView: ImageView = itemView.findViewById(R.id.imageView)
    var textView: TextView = itemView.findViewById(R.id.textView)
    var stub: ImageView = itemView.findViewById(R.id.image_stub)
}

class DisappearItemAnimation : BaseItemAnimator() {


    override fun animateRemoveImpl(holder: RecyclerView.ViewHolder?) {
        if (holder == null) {
            return
        }
        if (holder !is AvengersViewHolder) {
            return
        }
        val view = holder.itemView
        val imageView = holder.imageView
        val textView = holder.textView

        val disappearView = DisappearView.attach(view.context as Activity)
        disappearView.execute(view,removeDuration)
        val animation = view.animate()
        animation.alpha(0F).translationYBy(200F).setDuration(removeDuration).setListener(object :
            AnimatorListenerAdapter() {
            override fun onAnimationStart(animator: Animator) {
                dispatchRemoveStarting(holder)
            }

            override fun onAnimationEnd(animator: Animator) {
                animation.setListener(null)
                view.alpha = 1f
                imageView.visibility = View.VISIBLE
                textView.visibility = View.VISIBLE
                view.background = ContextCompat.getDrawable(view.context, R.drawable.item_avengers_bg)
                dispatchRemoveFinished(holder)
                mRemoveAnimations.remove(holder)
                dispatchFinishedWhenDone()
            }
        }).start()
    }
}