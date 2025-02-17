package com.nhathuy.circlerecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import kotlin.jvm.JvmField;

/**
 *
 * This class represents a button that can move on the screen
 *
 * @return 0.1
 * @since 15.02.2025
 * @author TravisHuy
 */
public class TravisHuyButton extends AppCompatButton implements TravisHuyButtonInterface{

    /**
     * The distance between the left edge of the button and the touch point
     */
    private float dX, dY;
    /**
     * The last touch point
     */
    private float lastTouchX, lastTouchY;
    /**
     * The flag to check if the button is dragging
     */
    private boolean isDragging = false;
    /**
     * The click listener
     */
    private OnClickListener clickListener;

    private CircleRecyclerView circleRecyclerView;


    public TravisHuyButton(Context context) {
        super(context);
    }

    public TravisHuyButton(Context context, AttributeSet attrs) {
        super(context,attrs);
        init();
    }
    /**
     * This method initializes the button
     */
    public void init(){
        setBackgroundResource(R.drawable.circle_button_bg);
    }

    /**
     * This method handles the touch event
     * @param event the touch event
     * @return true if the event is handled, false otherwise
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            //store the initial position relative to button's top-left corner
            case MotionEvent.ACTION_DOWN:
                dX = getX() - event.getRawX();
                dY = getY() - event.getRawY();
                lastTouchX = event.getRawX();
                lastTouchY = event.getRawY();
                return true;
            case MotionEvent.ACTION_MOVE:
                // calculate the new position based on touch movement
                float newX = event.getRawX() + dX;
                float newY = event.getRawY() + dY;

                // Ensure the button stays within the parent view’s boundaries.
                newX = Math.max(0, Math.min(newX, ((View) getParent()).getWidth() - getWidth()));
                newY = Math.max(0, Math.min(newY, ((View) getParent()).getHeight() - getHeight()));

                // update the button's position
                setX(newX);
                setY(newY);

                // Update CircleRecyclerView center position
                if (circleRecyclerView != null) {
                    // Calculate the center of the button
                    float centerX = newX + getWidth() / 2;
                    float centerY = newY + getHeight() / 2;
                    circleRecyclerView.setCenterPosition(centerX, centerY);
                }


                // Mark as dragging to differentiate from click events.
                isDragging = true;

                return true;
            case MotionEvent.ACTION_UP:
                // If the button was not dragged, consider it a click event.
                if (!isDragging && clickListener != null) {
                    clickListener.onClick(this);
                }
                isDragging = false;
                return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * Override the default click listener to store the a reference to the listener
     * This ensures the button still handles click events even with custom touch handling.
     * @param l The click listener to be set
     */
    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        this.clickListener = l;
    }

    public void setCircleRecyclerView(CircleRecyclerView recyclerView) {
        this.circleRecyclerView = recyclerView;
    }

    @Override
    public boolean isDragging() {
        return isDragging;
    }

    @Override
    public void setDragging(boolean value) {
        isDragging = value;
    }
}
