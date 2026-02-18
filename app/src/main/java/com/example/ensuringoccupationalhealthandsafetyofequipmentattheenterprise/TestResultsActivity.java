package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.adapters.QuestionResultAdapter;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.QuestionResult;

import java.util.ArrayList;

public class TestResultsActivity extends BaseActivity {
    private TextView tvScore;
    private RecyclerView recyclerView;
    private Button btnFinish;
    private QuestionResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_results);

        setTitle("Результаты теста");

        tvScore = findViewById(R.id.tvScore);
        recyclerView = findViewById(R.id.recyclerView);
        btnFinish = findViewById(R.id.btnFinish);

        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 0);
        ArrayList<QuestionResult> results = (ArrayList<QuestionResult>) getIntent().getSerializableExtra("results");

        tvScore.setText("Результат: " + score + " из " + total + " (" + (score * 100 / total) + "%)");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QuestionResultAdapter(results);
        recyclerView.setAdapter(adapter);

        btnFinish.setOnClickListener(v -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}