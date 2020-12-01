package com.example.wallsticker.Utilities

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import androidx.recyclerview.widget.LinearLayoutManager

class FadeInLinearLayoutManager : LinearLayoutManager {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    private val enterInterpolator = AnticipateOvershootInterpolator(1f)

    override fun addView(child: View, index: Int) {
        super.addView(child, index)
        val h = 300f
        // if index == 0 item is added on top if -1 it's on the bottom
        child.translationY = if (index == 0) -h else h
        // begin animation when view is laid out
        //child.alpha = 0.3f
        child.animate().translationY(0f).alpha(1f)
            .setInterpolator(enterInterpolator)
            .setDuration(1000L)
    }
}