package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.R;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.QuestionResult;
import java.util.List;

public class QuestionResultAdapter extends RecyclerView.Adapter<QuestionResultAdapter.ViewHolder> {
    private List<QuestionResult> results;

    public QuestionResultAdapter(List<QuestionResult> results) {
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuestionResult result = results.get(position);
        holder.tvQuestion.setText(result.getQuestionText());
        holder.tvUserAnswer.setText("Ваш ответ: " + result.getUserAnswer());
        holder.tvCorrectAnswer.setText("Правильный ответ: " + result.getCorrectAnswer());

        if (result.isCorrect()) {
            holder.tvUserAnswer.setTextColor(holder.itemView.getContext().getColor(android.R.color.holo_green_dark));
        } else {
            holder.tvUserAnswer.setTextColor(holder.itemView.getContext().getColor(android.R.color.holo_red_dark));
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion, tvUserAnswer, tvCorrectAnswer;

        ViewHolder(View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvUserAnswer = itemView.findViewById(R.id.tvUserAnswer);
            tvCorrectAnswer = itemView.findViewById(R.id.tvCorrectAnswer);
        }
    }
}