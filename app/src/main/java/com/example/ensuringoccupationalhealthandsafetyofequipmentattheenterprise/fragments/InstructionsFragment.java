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
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.adapters.InstructionAdapter;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.database.DatabaseHelper;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.Instruction;

import java.util.List;

public class InstructionsFragment extends Fragment {
    private RecyclerView recyclerView;
    private InstructionAdapter adapter;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instructions, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DatabaseHelper(getContext());
        List<Instruction> instructionList = dbHelper.getAllInstructions();

        adapter = new InstructionAdapter(instructionList, instruction -> {
            Intent intent = new Intent(getActivity(), InstructionDetailActivity.class);
            intent.putExtra("instruction_id", instruction.getId());
            intent.putExtra("title", instruction.getTitle());
            intent.putExtra("content", instruction.getContent());
            intent.putExtra("date", instruction.getDate());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
        return view;
    }
}