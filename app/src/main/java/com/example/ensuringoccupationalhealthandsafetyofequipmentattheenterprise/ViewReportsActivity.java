package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.database.DatabaseHelper;

public class ViewReportsActivity extends BaseActivity {

    private TextView tvReport;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        setTitle("Просмотр отчетов");

        tvReport = findViewById(R.id.tvReport);
        dbHelper = new DatabaseHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int totalWorkers = 0;
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_USERS + " WHERE role='worker'", null);
        if (c.moveToFirst()) totalWorkers = c.getInt(0);
        c.close();

        int completedTrainings = 0;
        c = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_TRAINING_ASSIGNMENTS + " WHERE status='Пройдено'", null);
        if (c.moveToFirst()) completedTrainings = c.getInt(0);
        c.close();

        int totalTrainings = 0;
        c = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_TRAINING_ASSIGNMENTS, null);
        if (c.moveToFirst()) totalTrainings = c.getInt(0);
        c.close();

        double avgScore = 0;
        c = db.rawQuery("SELECT AVG(score) FROM " + DatabaseHelper.TABLE_TEST_RESULTS, null);
        if (c.moveToFirst()) avgScore = c.getDouble(0);
        c.close();

        String report = "Отчет по охране труда\n\n" +
                "Всего сотрудников: " + totalWorkers + "\n" +
                "Назначено инструктажей: " + totalTrainings + "\n" +
                "Пройдено инструктажей: " + completedTrainings + "\n" +
                "Средний балл тестов: " + (avgScore > 0 ? String.format("%.1f%%", avgScore) : "нет данных") + "\n";
        tvReport.setText(report);
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