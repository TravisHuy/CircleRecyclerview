package com.nhathuy.circlerecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class KotlinActivity : AppCompatActivity() {
    private lateinit var circleRecyclerView: CircleRecyclerView
    private lateinit var floorsButton: TravisHuyButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        circleRecyclerView = findViewById(R.id.circle_recycler_view)
        floorsButton = findViewById(R.id.travis_huy_btn)

        val floors = listOf(
            "Floor 1", "Floor 2", "Floor 3",
            "Floor 4", "Floor 5"
        )

        val adapter = FloorAdapter(floors) { floor ->
            Toast.makeText(this, "Selected: $floor", Toast.LENGTH_SHORT).show()
            circleRecyclerView.toggleVisibility()
        }

        circleRecyclerView.adapter = adapter
        floorsButton.setCircleRecyclerView(circleRecyclerView)

        // Set initial center position
        floorsButton.post {
            val initialCenterX = floorsButton.x + floorsButton.width / 2
            val initialCenterY = floorsButton.y + floorsButton.height / 2
            circleRecyclerView.setCenterPosition(initialCenterX, initialCenterY)
        }

        floorsButton.setOnClickListener {
            circleRecyclerView.toggleVisibility()
            val centerX = floorsButton.x + floorsButton.width / 2
            val centerY = floorsButton.y + floorsButton.height / 2
            circleRecyclerView.setCenterPosition(centerX, centerY)
        }
    }
}