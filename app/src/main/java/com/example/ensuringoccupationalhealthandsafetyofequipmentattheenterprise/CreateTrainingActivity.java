package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;

import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.database.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class CreateTrainingActivity extends BaseActivity {

    private EditText etName, etDescription, etDefaultDueDate;
    private Button btnSave;
    private DatabaseHelper dbHelper;
    private DateTextWatcher dateTextWatcher;
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");
    private SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_training);

        setTitle("Создание инструктажа");

        dbHelper = new DatabaseHelper(this);
        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etDefaultDueDate = findViewById(R.id.etDefaultDueDate);
        btnSave = findViewById(R.id.btnSave);

        dateTextWatcher = new DateTextWatcher(etDefaultDueDate);
        etDefaultDueDate.addTextChangedListener(dateTextWatcher);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String description = etDescription.getText().toString().trim();
                String displayDate = etDefaultDueDate.getText().toString().trim();

                if (name.isEmpty() || description.isEmpty() || displayDate.isEmpty()) {
                    CustomToast.show(CreateTrainingActivity.this, "Заполните все поля");
                    return;
                }

                if (!DATE_PATTERN.matcher(displayDate).matches()) {
                    CustomToast.showLong(CreateTrainingActivity.this, "Дата должна быть в формате ДД-ММ-ГГГГ (например, 31-12-2025)");
                    return;
                }

                if (!isValidDate(displayDate)) {
                    CustomToast.show(CreateTrainingActivity.this, "Указана несуществующая дата");
                    return;
                }

                if (isDateInPast(displayDate)) {
                    CustomToast.show(CreateTrainingActivity.this, "Срок инструктажа не может быть в прошлом");
                    return;
                }

                String dbDate = convertToDbFormat(displayDate);

                dbHelper.addTrainingType(name, description, dbDate);
                CustomToast.show(CreateTrainingActivity.this, "Инструктаж создан");
                finish();
            }
        });
    }

    private boolean isValidDate(String dateStr) {
        try {
            displayFormat.setLenient(false);
            Date date = displayFormat.parse(dateStr);

            String[] parts = dateStr.split("-");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            return cal.get(Calendar.DAY_OF_MONTH) == day &&
                    (cal.get(Calendar.MONTH) + 1) == month &&
                    cal.get(Calendar.YEAR) == year;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isDateInPast(String dateStr) {
        try {
            Date date = displayFormat.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date today = cal.getTime();

            return date.before(today);
        } catch (ParseException e) {
            return true;
        }
    }

    private String convertToDbFormat(String displayDate) {
        try {
            Date date = displayFormat.parse(displayDate);
            return dbFormat.format(date);
        } catch (ParseException e) {
            return displayDate;
        }
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