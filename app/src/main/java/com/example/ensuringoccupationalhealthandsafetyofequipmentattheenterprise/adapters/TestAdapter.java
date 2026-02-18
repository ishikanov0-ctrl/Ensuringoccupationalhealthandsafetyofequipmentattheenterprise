package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.R;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.Test;
import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    private List<Test> tests;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Test test);
    }

    public TestAdapter(List<Test> tests, OnItemClickListener listener) {
        this.tests = tests;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_test, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Test test = tests.get(position);
        holder.tvTitle.setText(test.getTitle());
        holder.tvQuestionsCount.setText("Вопросов: " + test.getQuestionsCount());
        if (test.getScore() != null) {
            holder.tvScore.setText("Результат: " + test.getScore() + "%");
        } else {
            holder.tvScore.setText("Не пройден");
        }
        holder.itemView.setOnClickListener(v -> listener.onItemClick(test));
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvQuestionsCount, tvScore;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvQuestionsCount = itemView.findViewById(R.id.tvQuestionsCount);
            tvScore = itemView.findViewById(R.id.tvScore);
        }
    }
}