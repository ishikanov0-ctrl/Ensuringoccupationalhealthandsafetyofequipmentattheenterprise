package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.R;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.TrainingAssignment;

import java.util.List;

public class TrainingAssignmentAdapter extends RecyclerView.Adapter<TrainingAssignmentAdapter.ViewHolder> {
    private List<TrainingAssignment> assignments;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(TrainingAssignment assignment);
    }

    public TrainingAssignmentAdapter(List<TrainingAssignment> assignments, OnItemClickListener listener) {
        this.assignments = assignments;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_training_assignment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrainingAssignment ta = assignments.get(position);
        holder.tvName.setText(ta.getTrainingName());
        holder.tvStatus.setText("Статус: " + ta.getStatus());
        holder.tvDueDate.setText("Срок: " + ta.getDueDate());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(ta));
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvStatus, tvDueDate;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvDueDate = itemView.findViewById(R.id.tvDueDate);
        }
    }
}