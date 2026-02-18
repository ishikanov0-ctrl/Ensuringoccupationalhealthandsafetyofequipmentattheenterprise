package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.R;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.adapters.TrainingAssignmentAdapter;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.database.DatabaseHelper;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.TrainingAssignment;

import java.util.List;

public class TrainingsFragment extends Fragment {
    private RecyclerView recyclerView;
    private TrainingAssignmentAdapter adapter;
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
        View view = inflater.inflate(R.layout.fragment_trainings, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DatabaseHelper(getContext());
        loadAssignments();

        return view;
    }

    private void loadAssignments() {
        List<TrainingAssignment> assignments = dbHelper.getAssignmentsForUser(userId);
        adapter = new TrainingAssignmentAdapter(assignments, new TrainingAssignmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TrainingAssignment assignment) {
                if (assignment.getStatus().equals("Назначено")) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Прохождение инструктажа")
                            .setMessage("Отметить инструктаж как пройденный?")
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dbHelper.updateAssignmentStatus(assignment.getId(), "Пройдено");
                                    Toast.makeText(getContext(), "Инструктаж пройден", Toast.LENGTH_SHORT).show();
                                    loadAssignments();
                                }
                            })
                            .setNegativeButton("Нет", null)
                            .show();
                } else {
                    Toast.makeText(getContext(), "Инструктаж уже пройден", Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }
}