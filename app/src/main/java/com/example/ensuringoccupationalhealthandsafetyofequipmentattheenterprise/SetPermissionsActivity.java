package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetPermissionsActivity extends BaseActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_permissions);

        setTitle("Настройка прав доступа");

        listView = findViewById(R.id.listView);

        List<Map<String, String>> data = new ArrayList<>();
        addItem(data, "Работник", "Просмотр инструкций, прохождение тестов, инструктажи");
        addItem(data, "Инструктор", "Создание инструктажей, назначение, контроль, анализ тестов, просмотр отчетов");
        addItem(data, "Администратор", "Управление пользователями, обслуживание системы");

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[]{"role", "permissions"},
                new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(adapter);
    }

    private void addItem(List<Map<String, String>> list, String role, String perms) {
        Map<String, String> map = new HashMap<>();
        map.put("role", role);
        map.put("permissions", perms);
        list.add(map);
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