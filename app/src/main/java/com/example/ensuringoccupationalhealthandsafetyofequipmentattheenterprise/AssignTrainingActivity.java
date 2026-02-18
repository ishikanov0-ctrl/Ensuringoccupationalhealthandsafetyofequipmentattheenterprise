package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.database.DatabaseHelper;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.TrainingType;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssignTrainingActivity extends BaseActivity {

    private RadioGroup radioGroupAssignType;
    private RadioButton radioSpecific, radioAll;
    private Spinner spinnerEmployee, spinnerTraining;
    private EditText etDueDate;
    private TextView tvEmployeeLabel;
    private Button btnAssign;
    private DatabaseHelper dbHelper;
    private List<User> workers;
    private List<TrainingType> trainings;
    private SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_training);

        setTitle("Назначение инструктажа");

        dbHelper = new DatabaseHelper(this);

        radioGroupAssignType = findViewById(R.id.radioGroupAssignType);
        radioSpecific = findViewById(R.id.radioSpecific);
        radioAll = findViewById(R.id.radioAll);
        spinnerEmployee = findViewById(R.id.spinnerEmployee);
        spinnerTraining = findViewById(R.id.spinnerTraining);
        etDueDate = findViewById(R.id.etDueDate);
        tvEmployeeLabel = findViewById(R.id.tvEmployeeLabel);
        btnAssign = findViewById(R.id.btnAssign);

        workers = dbHelper.getWorkers();
        if (workers != null && !workers.isEmpty()) {
            ArrayAdapter<User> workerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, workers);
            workerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerEmployee.setAdapter(workerAdapter);
        } else {
            CustomToast.show(this, "Нет доступных сотрудников");
        }

        trainings = dbHelper.getAllTrainingTypes();
        if (trainings != null && !trainings.isEmpty()) {
            ArrayAdapter<TrainingType> trainingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, trainings);
            trainingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTraining.setAdapter(trainingAdapter);
        } else {
            CustomToast.show(this, "Нет доступных инструктажей");
        }

        spinnerTraining.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (trainings != null && !trainings.isEmpty()) {
                    TrainingType selected = trainings.get(position);
                    String dbDate = selected.getDefaultDueDate();
                    String displayDate = convertToDisplayFormat(dbDate);
                    etDueDate.setText(displayDate);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                etDueDate.setText("");
            }
        });

        radioGroupAssignType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioAll) {
                    spinnerEmployee.setEnabled(false);
                    spinnerEmployee.setVisibility(View.GONE);
                    tvEmployeeLabel.setVisibility(View.GONE);
                } else {
                    spinnerEmployee.setEnabled(true);
                    spinnerEmployee.setVisibility(View.VISIBLE);
                    tvEmployeeLabel.setVisibility(View.VISIBLE);
                }
            }
        });

        btnAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trainings == null || trainings.isEmpty()) {
                    CustomToast.show(AssignTrainingActivity.this, "Нет доступных инструктажей");
                    return;
                }

                TrainingType selectedTraining = (TrainingType) spinnerTraining.getSelectedItem();
                String dueDate = etDueDate.getText().toString().trim();

                if (dueDate.isEmpty()) {
                    CustomToast.show(AssignTrainingActivity.this, "Ошибка: срок не указан");
                    return;
                }

                if (radioAll.isChecked()) {
                    if (workers != null && !workers.isEmpty()) {
                        for (User worker : workers) {
                            dbHelper.addAssignment(worker.getId(), selectedTraining.getId(), selectedTraining.getDefaultDueDate());
                        }
                        CustomToast.show(AssignTrainingActivity.this, "Инструктаж назначен всем (" + workers.size() + " сотрудникам)");
                    } else {
                        CustomToast.show(AssignTrainingActivity.this, "Нет сотрудников для назначения");
                    }
                } else {
                    if (workers != null && !workers.isEmpty() && spinnerEmployee.getSelectedItem() != null) {
                        User selectedWorker = (User) spinnerEmployee.getSelectedItem();
                        dbHelper.addAssignment(selectedWorker.getId(), selectedTraining.getId(), selectedTraining.getDefaultDueDate());
                        CustomToast.show(AssignTrainingActivity.this, "Инструктаж назначен сотруднику " + selectedWorker.getFullName());
                    } else {
                        CustomToast.show(AssignTrainingActivity.this, "Выберите сотрудника");
                        return;
                    }
                }
                finish();
            }
        });
    }

    private String convertToDisplayFormat(String dbDate) {
        if (dbDate == null || dbDate.isEmpty()) return "";
        try {
            Date date = dbFormat.parse(dbDate);
            return displayFormat.format(date);
        } catch (ParseException e) {
            return dbDate;
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