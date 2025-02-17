package com.nhathuy.circlerecyclerview

import android.content.Context
import android.util.AttributeSet

object CircleRecyclerViewFactory {
    @JvmStatic
    @JvmOverloads
    fun createCircleRecyclerView(
        context: Context,
        attrs: AttributeSet? = null
    ): CircleRecyclerView {
        return CircleRecyclerView(context, attrs)
    }
}