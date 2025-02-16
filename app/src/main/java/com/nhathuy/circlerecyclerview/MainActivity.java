package com.nhathuy.circlerecyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 *
 * This class represents the main activity of the application
 *
 * @return 0.1
 * @since 16.02.2025
 * @author TravisHuy
 */
public class MainActivity extends AppCompatActivity {
    private CircleRecyclerView circleRecyclerView;
    private TravisHuyButton floorsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleRecyclerView = findViewById(R.id.circle_recycler_view);
        floorsButton = findViewById(R.id.button);

        List<String> floors = Arrays.asList(
                "Floor 1", "Floor 2", "Floor 3",
                "Floor 4", "Floor 5"
        );

        FloorAdapter adapter = new FloorAdapter(floors, floor -> {
            Toast.makeText(this, "Selected: " + floor, Toast.LENGTH_SHORT).show();
            circleRecyclerView.toggleVisibility();
        });

        circleRecyclerView.setAdapter(adapter);
        floorsButton.setCircleRecyclerView(circleRecyclerView);

        // Set initial center position
        floorsButton.post(() -> {
            float initialCenterX = floorsButton.getX() + floorsButton.getWidth() / 2;
            float initialCenterY = floorsButton.getY() + floorsButton.getHeight() / 2;
            circleRecyclerView.setCenterPosition(initialCenterX, initialCenterY);
        });

        floorsButton.setOnClickListener(v -> {
            circleRecyclerView.toggleVisibility();
            float centerX = floorsButton.getX() + floorsButton.getWidth() / 2;
            float centerY = floorsButton.getY() + floorsButton.getHeight() / 2;
            circleRecyclerView.setCenterPosition(centerX, centerY);
        });
    }
}