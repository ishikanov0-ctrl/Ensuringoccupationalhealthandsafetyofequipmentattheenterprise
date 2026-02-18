package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.database.DatabaseHelper;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.User;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        dbHelper = new DatabaseHelper(this);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    CustomToast.show(LoginActivity.this, "Введите логин и пароль");
                    return;
                }

                User user = dbHelper.getUser(username, password);
                if (user != null) {
                    Intent intent = null;
                    switch (user.getRole()) {
                        case "worker":
                            intent = new Intent(LoginActivity.this, WorkerActivity.class);
                            intent.putExtra("user_id", user.getId());
                            intent.putExtra("fullname", user.getFullName());
                            break;
                        case "engineer":
                            intent = new Intent(LoginActivity.this, EngineerActivity.class);
                            intent.putExtra("fullname", user.getFullName());
                            break;
                        case "admin":
                            intent = new Intent(LoginActivity.this, AdminActivity.class);
                            intent.putExtra("user_id", user.getId());
                            intent.putExtra("fullname", user.getFullName());
                            break;
                    }
                    startActivity(intent);
                    finish();
                } else {
                    CustomToast.show(LoginActivity.this, "Неверный логин или пароль");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}