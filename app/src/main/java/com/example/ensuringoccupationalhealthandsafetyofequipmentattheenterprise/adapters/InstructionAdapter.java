package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.R;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.Instruction;
import java.util.List;

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.ViewHolder> {
    private List<Instruction> instructions;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Instruction instruction);
    }

    public InstructionAdapter(List<Instruction> instructions, OnItemClickListener listener) {
        this.instructions = instructions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_instruction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Instruction instruction = instructions.get(position);
        holder.tvTitle.setText(instruction.getTitle());
        holder.tvDate.setText(instruction.getDate());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(instruction));
    }

    @Override
    public int getItemCount() {
        return instructions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}