package com.nhathuy.circlerecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
/**
 *
 * This class represents an adapter for the floor list
 *
 * @return 0.1
 * @since 16.02.2025
 * @author TravisHuy
 */
public class FloorAdapter extends RecyclerView.Adapter<FloorAdapter.FloorViewHolder> {
    private final List<String> floors;
    private final OnFloorClickListener listener;

    public interface OnFloorClickListener {
        void onFloorClick(String floor);
    }

    public FloorAdapter(List<String> floors, OnFloorClickListener listener) {
        this.floors = floors;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FloorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_floor, parent, false);
        return new FloorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FloorViewHolder holder, int position) {
        String floor = floors.get(position);
        holder.floorButton.setText(floor);
        holder.floorButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFloorClick(floor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return floors.size();
    }

    static class FloorViewHolder extends RecyclerView.ViewHolder {

        TextView floorButton;

        FloorViewHolder(View itemView) {
            super(itemView);
            floorButton = itemView.findViewById(R.id.text_floor);
        }
    }
}