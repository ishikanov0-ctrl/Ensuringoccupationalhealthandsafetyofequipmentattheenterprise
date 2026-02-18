package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.R;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.Training;
import java.util.List;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.ViewHolder> {
    private List<Training> trainings;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Training training);
    }

    public TrainingAdapter(List<Training> trainings, OnItemClickListener listener) {
        this.trainings = trainings;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_training, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Training training = trainings.get(position);
        holder.tvName.setText(training.getName());
        holder.tvStatus.setText(training.getStatus());
        holder.tvDueDate.setText(training.getDueDate());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(training));
    }

    @Override
    public int getItemCount() {
        return trainings.size();
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