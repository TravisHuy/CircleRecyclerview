package com.nhathuy.circlerecyclerview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
/**
 *
 * This class represents a RecyclerView that displays items in a circle
 *
 * @return 0.1
 * @since 16.02.2025
 * @author TravisHuy
 */
public class CircleRecyclerView extends RecyclerView {
    private float centerX;
    private float centerY;
    private float radius = 300f; // Default radius
    private int itemCount;
    private double angleStep;
    private boolean isExpanded = false;
    private final OvershootInterpolator overshootInterpolator = new OvershootInterpolator(1.5f);

    // variables for touch handling
    private float lastTouchX, lastTouchY;
    private float currentRotation = 0f;


    public CircleRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }

    public CircleRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayoutManager(new CircleLayoutManager());
        setVisibility(VISIBLE);
        setAlpha(0f);
    }

    public void setCenterPosition(float x, float y) {
        this.centerX = x;
        this.centerY = y;
        requestLayout();
    }

    public void toggleVisibility() {
        if (isExpanded) {
            collapse();
        } else {
            expand();
        }
    }

    private void expand() {
        setVisibility(VISIBLE);
        animate()
                .alpha(1f)
                .setDuration(300)
                .setInterpolator(new DecelerateInterpolator())
                .start();
        isExpanded = true;
    }

    private void collapse() {
        animate()
                .alpha(0f)
                .setDuration(300)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> {
                    setVisibility(GONE);
                    isExpanded = false;
                })
                .start();
    }

    // handle touch event
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastTouchX = e.getX();
                lastTouchY = e.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float deltaX = e.getX() - lastTouchX;
                float deltaY = e.getY() - lastTouchY;

                // Calculate the angle of rotation based on touch movement
                float angle = calculateRotationAngle(deltaX, deltaY);
                currentRotation += angle;

                // Request layout with new rotation
                requestLayout();

                lastTouchX = e.getX();
                lastTouchY = e.getY();
                return true;
            case MotionEvent.ACTION_UP:
                ((CircleLayoutManager) getLayoutManager()).applyScrollPhysics();
                return true;
        }
        return super.onTouchEvent(e);
    }
    // Calculate the rotation angle based on touch movement
    private float calculateRotationAngle(float deltaX, float deltaY) {
        // Calculate rotation based on movement relative to center
        float touchAngle = (float) Math.toDegrees(Math.atan2(deltaY, deltaX));
        // Convert to radians and normalize
        return (float) Math.toRadians(touchAngle) * 0.5f; // Adjust multiplier for sensitivity
    }

    private class CircleLayoutManager extends LayoutManager {
        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return new RecyclerView.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
            );
        }

        @Override
        public void onLayoutChildren(Recycler recycler, State state) {
            detachAndScrapAttachedViews(recycler);

            itemCount = getItemCount();
            if (itemCount == 0) return;

            angleStep = 2 * Math.PI / itemCount;

            for (int i = 0; i < itemCount; i++) {
                View view = recycler.getViewForPosition(i);
                addView(view);

                // Start from top
                float baseAngle = (float) (i * angleStep - Math.PI / 2);
                float rotatedAngle = baseAngle + currentRotation;


                float x = (float) (centerX + radius * Math.cos(rotatedAngle));
                float y = (float) (centerY + radius * Math.sin(rotatedAngle));

                measureChildWithMargins(view, 0, 0);
                int width = getDecoratedMeasuredWidth(view);
                int height = getDecoratedMeasuredHeight(view);

                layoutDecoratedWithMargins(view,
                        (int) (x - width / 2),
                        (int) (y - height / 2),
                        (int) (x + width / 2),
                        (int) (y + height / 2));

                if (view.getScaleX() == 0f) {
                    animateItemView(view, i);
                }
            }
        }

        private void animateItemView(View view, int position) {
            view.setScaleX(0f);
            view.setScaleY(0f);
            view.setAlpha(0f);

            view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .alpha(1f)
                    .setDuration(300)
                    .setStartDelay(position * 50)
                    .setInterpolator(overshootInterpolator)
                    .start();
        }

        // Add method to handle circular scrolling physics
        private void applyScrollPhysics() {
            ValueAnimator momentum = ValueAnimator.ofFloat(currentRotation, currentRotation + 0.5f);
            momentum.setDuration(300);
            momentum.setInterpolator(new DecelerateInterpolator());
            momentum.addUpdateListener(animation -> {
                currentRotation = (float) animation.getAnimatedValue();
                requestLayout();
            });
            momentum.start();
        }
    }
}
