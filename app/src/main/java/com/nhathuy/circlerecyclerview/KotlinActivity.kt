package com.nhathuy.circlerecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        val button = TravisHuyButton(this)
        button.text = "Hello Kotlin"
    }
}