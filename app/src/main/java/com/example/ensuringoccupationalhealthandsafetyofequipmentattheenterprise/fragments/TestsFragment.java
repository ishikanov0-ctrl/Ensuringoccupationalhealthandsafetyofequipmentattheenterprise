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

import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.CustomToast;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.R;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.TestActivity;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.adapters.TestAdapter;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.database.DatabaseHelper;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.Test;

import java.util.List;

public class TestsFragment extends Fragment {
    private RecyclerView recyclerView;
    private TestAdapter adapter;
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
        View view = inflater.inflate(R.layout.fragment_tests, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DatabaseHelper(getContext());
        loadTests();

        return view;
    }

    private void loadTests() {
        List<Test> tests = dbHelper.getAllTests();
        if (tests == null || tests.isEmpty()) {
            CustomToast.show(getContext(), "Нет доступных тестов");
        }

        adapter = new TestAdapter(tests, test -> {
            Intent intent = new Intent(getActivity(), TestActivity.class);
            intent.putExtra("test_id", test.getId());
            intent.putExtra("test_title", test.getTitle());
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }
}