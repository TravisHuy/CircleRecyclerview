package com.nhathuy.circlerecyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

object TravisHuyButtonFactory {
    @JvmStatic
    @JvmOverloads
    fun createButton(
        context: Context,
        attrs: AttributeSet? = null
    ): AppCompatButton {
        return TravisHuyButton(context, attrs)
    }
}