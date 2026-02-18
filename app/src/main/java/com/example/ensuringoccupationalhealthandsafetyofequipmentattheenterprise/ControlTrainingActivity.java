package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.database.DatabaseHelper;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.TrainingAssignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlTrainingActivity extends BaseActivity {

    private ListView listView;
    private DatabaseHelper dbHelper;
    private List<TrainingAssignment> assignments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_training);

        setTitle("Контроль инструктажей");

        listView = findViewById(R.id.listView);
        dbHelper = new DatabaseHelper(this);

        loadAssignments();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrainingAssignment selected = assignments.get(position);

                if (selected.getStatus().equals("Назначено")) {
                    new AlertDialog.Builder(ControlTrainingActivity.this)
                            .setTitle("Подтверждение")
                            .setMessage("Отметить инструктаж как пройденный для сотрудника " + selected.getUserName() + "?")
                            .setPositiveButton("Да", (dialog, which) -> {
                                dbHelper.updateAssignmentStatus(selected.getId(), "Пройдено");
                                CustomToast.show(ControlTrainingActivity.this, "Инструктаж отмечен как пройденный");
                                loadAssignments();
                            })
                            .setNegativeButton("Нет", null)
                            .show();
                } else {
                    CustomToast.show(ControlTrainingActivity.this, "Инструктаж уже пройден");
                }
            }
        });
    }

    private void loadAssignments() {
        assignments = dbHelper.getAllAssignments();
        List<Map<String, String>> data = new ArrayList<>();

        if (assignments != null && !assignments.isEmpty()) {
            for (TrainingAssignment ta : assignments) {
                Map<String, String> map = new HashMap<>();
                map.put("employee", ta.getUserName());
                map.put("training", ta.getTrainingName());
                map.put("status", ta.getStatus() + " (срок: " + ta.getDueDate() + ")");
                data.add(map);
            }
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("employee", "Нет данных");
            map.put("training", "Нет назначенных инструктажей");
            map.put("status", "");
            data.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                R.layout.list_item_3,
                new String[]{"employee", "training", "status"},
                new int[]{R.id.text1, R.id.text2, R.id.text3});
        listView.setAdapter(adapter);
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