package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import androidx.annotation.NonNull;

public class EngineerActivity extends BaseActivity {

    private Button btnCreateTraining, btnAssignTraining, btnControlTraining, btnAnalyzeTests, btnViewReports;
    private String userName = "Инструктор";

    {
        isRootActivity = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer);

        if (getIntent().hasExtra("fullname")) {
            userName = getIntent().getStringExtra("fullname");
        }

        setTitle("Инструктор: " + userName);

        btnCreateTraining = findViewById(R.id.btnCreateTraining);
        btnAssignTraining = findViewById(R.id.btnAssignTraining);
        btnControlTraining = findViewById(R.id.btnControlTraining);
        btnAnalyzeTests = findViewById(R.id.btnAnalyzeTests);
        btnViewReports = findViewById(R.id.btnViewReports);

        btnCreateTraining.setOnClickListener(v ->
                startActivity(new Intent(EngineerActivity.this, CreateTrainingActivity.class)));

        btnAssignTraining.setOnClickListener(v ->
                startActivity(new Intent(EngineerActivity.this, AssignTrainingActivity.class)));

        btnControlTraining.setOnClickListener(v ->
                startActivity(new Intent(EngineerActivity.this, ControlTrainingActivity.class)));

        btnAnalyzeTests.setOnClickListener(v ->
                startActivity(new Intent(EngineerActivity.this, AnalyzeTestsActivity.class)));

        btnViewReports.setOnClickListener(v ->
                startActivity(new Intent(EngineerActivity.this, ViewReportsActivity.class)));
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

    @Override
    public void onBackPressed() {
        CustomToast.show(this, "Используйте кнопку 'Выйти' для выхода");
    }
}