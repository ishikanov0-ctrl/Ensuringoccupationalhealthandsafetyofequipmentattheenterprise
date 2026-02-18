package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import androidx.annotation.NonNull;

public class AdminActivity extends BaseActivity {

    private Button btnManageUsers, btnSystemMaintenance;
    private String userName = "Администратор";
    private int currentAdminId;

    {
        isRootActivity = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        if (getIntent().hasExtra("fullname")) {
            userName = getIntent().getStringExtra("fullname");
        }

        currentAdminId = getIntent().getIntExtra("user_id", -1);

        setTitle("Администратор: " + userName);

        btnManageUsers = findViewById(R.id.btnManageUsers);
        btnSystemMaintenance = findViewById(R.id.btnSystemMaintenance);

        btnManageUsers.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, ManageUsersActivity.class);
            intent.putExtra("current_admin_id", currentAdminId);
            intent.putExtra("current_admin_name", userName);
            startActivity(intent);
        });

        btnSystemMaintenance.setOnClickListener(v ->
                startActivity(new Intent(AdminActivity.this, SystemMaintenanceActivity.class)));
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