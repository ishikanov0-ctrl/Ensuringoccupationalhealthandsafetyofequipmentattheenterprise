package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.InstructionDetailActivity;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.R;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.TrainingDetailActivity;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.adapters.LearningAdapter;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.database.DatabaseHelper;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.Instruction;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.LearningItem;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.TrainingAssignment;

import java.util.ArrayList;
import java.util.List;

public class LearningFragment extends Fragment {
    private RecyclerView recyclerView;
    private LearningAdapter adapter;
    private DatabaseHelper dbHelper;
    private int userId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt("user_id");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DatabaseHelper(getContext());
        loadLearningItems();

        return view;
    }

    private void loadLearningItems() {
        List<LearningItem> items = new ArrayList<>();

        List<Instruction> instructions = dbHelper.getAllInstructions();
        for (Instruction i : instructions) {
            items.add(new LearningItem(
                    i.getId(),
                    i.getTitle(),
                    i.getContent(),
                    i.getDate(),
                    "instruction"
            ));
        }

        List<TrainingAssignment> assignments = dbHelper.getAssignmentsForUser(userId);
        for (TrainingAssignment ta : assignments) {
            String description = "Статус: " + ta.getStatus();
            items.add(new LearningItem(
                    ta.getId(),
                    ta.getTrainingName(),
                    description,
                    ta.getDueDate(),
                    "training",
                    ta.getId()
            ));
        }

        adapter = new LearningAdapter(items, item -> {
            if (item.getType().equals("instruction")) {
                Intent intent = new Intent(getActivity(), InstructionDetailActivity.class);
                intent.putExtra("instruction_id", item.getId());
                intent.putExtra("title", item.getTitle());
                intent.putExtra("content", item.getContent());
                intent.putExtra("date", item.getDate());
                startActivity(intent);
            } else if (item.getType().equals("training")) {
                Intent intent = new Intent(getActivity(), TrainingDetailActivity.class);
                intent.putExtra("assignment_id", item.getTrainingAssignmentId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadLearningItems();
    }
}