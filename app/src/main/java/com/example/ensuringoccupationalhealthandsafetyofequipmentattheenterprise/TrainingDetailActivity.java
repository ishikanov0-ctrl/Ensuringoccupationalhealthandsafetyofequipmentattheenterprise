package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.database.DatabaseHelper;

public class TrainingDetailActivity extends BaseActivity {
    private TextView tvName, tvDescription, tvStatus, tvDueDate;
    private Button btnComplete;
    private DatabaseHelper dbHelper;
    private int assignmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_detail);

        setTitle("Детали инструктажа");

        assignmentId = getIntent().getIntExtra("assignment_id", -1);
        if (assignmentId == -1) {
            CustomToast.show(this, "Ошибка");
            finish();
            return;
        }

        dbHelper = new DatabaseHelper(this);
        tvName = findViewById(R.id.tvName);
        tvDescription = findViewById(R.id.tvDescription);
        tvStatus = findViewById(R.id.tvStatus);
        tvDueDate = findViewById(R.id.tvDueDate);
        btnComplete = findViewById(R.id.btnComplete);

        loadAssignmentDetails();

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.updateAssignmentStatus(assignmentId, "Пройдено");
                CustomToast.show(TrainingDetailActivity.this, "Инструктаж отмечен как пройденный");
                finish();
            }
        });
    }

    private void loadAssignmentDetails() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT ta.*, t.name as training_name, t.description as training_desc, u.fullname as user_name " +
                        "FROM " + DatabaseHelper.TABLE_TRAINING_ASSIGNMENTS + " ta " +
                        "JOIN " + DatabaseHelper.TABLE_TRAININGS + " t ON ta." + DatabaseHelper.COL_TRAINING_ID + " = t." + DatabaseHelper.COL_ID +
                        " JOIN " + DatabaseHelper.TABLE_USERS + " u ON ta." + DatabaseHelper.COL_USER_ID + " = u." + DatabaseHelper.COL_ID +
                        " WHERE ta." + DatabaseHelper.COL_ID + " = ?",
                new String[]{String.valueOf(assignmentId)});
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("training_name"));
            String desc = cursor.getString(cursor.getColumnIndex("training_desc"));
            String status = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_STATUS));
            String dueDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DUE_DATE));
            String userName = cursor.getString(cursor.getColumnIndex("user_name"));

            tvName.setText(name + " - " + userName);
            tvDescription.setText(desc);
            tvStatus.setText("Статус: " + status);
            tvDueDate.setText("Срок: " + dueDate);

            if (status.equals("Пройдено")) {
                btnComplete.setEnabled(false);
                btnComplete.setText("Уже пройдено");
            } else {
                btnComplete.setEnabled(true);
            }
        }
        cursor.close();
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