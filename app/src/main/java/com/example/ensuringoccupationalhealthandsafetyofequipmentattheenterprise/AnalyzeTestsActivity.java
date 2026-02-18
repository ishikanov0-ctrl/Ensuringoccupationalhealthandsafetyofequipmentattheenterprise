package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.annotation.NonNull;

import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.database.DatabaseHelper;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.TestResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyzeTestsActivity extends BaseActivity {

    private ListView listView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_tests);

        setTitle("Анализ тестов");

        listView = findViewById(R.id.listView);
        dbHelper = new DatabaseHelper(this);

        List<TestResult> results = dbHelper.getTestResults();
        List<Map<String, String>> data = new ArrayList<>();

        if (results != null && !results.isEmpty()) {
            for (TestResult tr : results) {
                Map<String, String> map = new HashMap<>();
                map.put("employee", tr.getUserName());
                map.put("test", tr.getTestTitle());
                map.put("result", tr.getScore() + "% (" + tr.getDateTaken() + ")");
                data.add(map);
            }
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("employee", "Нет данных");
            map.put("test", "Нет данных");
            map.put("result", "Нет результатов");
            data.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                R.layout.list_item_3,
                new String[]{"employee", "test", "result"},
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