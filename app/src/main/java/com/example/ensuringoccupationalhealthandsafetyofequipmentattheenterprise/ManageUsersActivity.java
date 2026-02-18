package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.database.DatabaseHelper;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageUsersActivity extends BaseActivity {

    private ListView listView;
    private Button btnAddUser;
    private DatabaseHelper dbHelper;
    private int currentAdminId;
    private String currentAdminName;
    private List<User> users;

    private final String[] roleValues = {"worker", "engineer", "admin"};
    private final String[] roleNames = {"Работник", "Инструктор", "Администратор"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        setTitle("Управление пользователями");

        currentAdminId = getIntent().getIntExtra("current_admin_id", -1);
        currentAdminName = getIntent().getStringExtra("current_admin_name");

        listView = findViewById(R.id.listView);
        btnAddUser = findViewById(R.id.btnAddUser);
        dbHelper = new DatabaseHelper(this);

        loadUsers();

        btnAddUser.setOnClickListener(v -> showAddEditUserDialog(null, -1));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedUser = users.get(position);
                showUserInfoDialog(selectedUser);
            }
        });
    }

    private void loadUsers() {
        users = dbHelper.getAllUsers();
        List<Map<String, String>> data = new ArrayList<>();

        for (User u : users) {
            Map<String, String> map = new HashMap<>();
            map.put("username", u.getUsername());
            map.put("fullname", u.getFullName());

            String roleStr = getRoleName(u.getRole());

            if (u.getId() == currentAdminId) {
                map.put("role", roleStr + " (это вы)");
            } else {
                map.put("role", roleStr);
            }

            data.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                R.layout.list_item_3,
                new String[]{"username", "fullname", "role"},
                new int[]{R.id.text1, R.id.text2, R.id.text3});
        listView.setAdapter(adapter);
    }

    private String getRoleName(String roleValue) {
        for (int i = 0; i < roleValues.length; i++) {
            if (roleValues[i].equals(roleValue)) {
                return roleNames[i];
            }
        }
        return roleValue;
    }

    private String getRoleValue(String roleName) {
        for (int i = 0; i < roleNames.length; i++) {
            if (roleNames[i].equals(roleName)) {
                return roleValues[i];
            }
        }
        return roleName;
    }

    private void showUserInfoDialog(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_user_info, null);
        builder.setView(dialogView);

        TextView tvUsername = dialogView.findViewById(R.id.tvUsername);
        TextView tvFullName = dialogView.findViewById(R.id.tvFullName);
        TextView tvRole = dialogView.findViewById(R.id.tvRole);
        Button btnEdit = dialogView.findViewById(R.id.btnEdit);
        Button btnDelete = dialogView.findViewById(R.id.btnDelete);

        tvUsername.setText("Логин: " + user.getUsername());
        tvFullName.setText("Полное имя: " + user.getFullName());
        tvRole.setText("Роль: " + getRoleName(user.getRole()));

        if (user.getId() == currentAdminId) {
            btnEdit.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        } else {
            btnEdit.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        }

        AlertDialog dialog = builder.create();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showAddEditUserDialog(user, user.getId());
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                confirmDeleteUser(user);
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void confirmDeleteUser(User user) {
        new AlertDialog.Builder(this)
                .setTitle("Подтверждение удаления")
                .setMessage("Вы уверены, что хотите удалить пользователя " + user.getFullName() + "?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteUser(user.getId());
                        CustomToast.show(ManageUsersActivity.this, "Пользователь удалён");
                        loadUsers();
                    }
                })
                .setNegativeButton("Нет", null)
                .show();
    }

    private void showAddEditUserDialog(User user, int userId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_user, null);
        builder.setView(dialogView);

        EditText etUsername = dialogView.findViewById(R.id.etUsername);
        EditText etPassword = dialogView.findViewById(R.id.etPassword);
        EditText etFullName = dialogView.findViewById(R.id.etFullName);
        Spinner spinnerRole = dialogView.findViewById(R.id.spinnerRole);
        Button btnSave = dialogView.findViewById(R.id.btnSave);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, roleNames);
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(roleAdapter);

        if (user != null) {
            etUsername.setText(user.getUsername());
            etPassword.setText(user.getPassword());
            etFullName.setText(user.getFullName());

            String roleName = getRoleName(user.getRole());
            for (int i = 0; i < roleNames.length; i++) {
                if (roleNames[i].equals(roleName)) {
                    spinnerRole.setSelection(i);
                    break;
                }
            }
        }

        AlertDialog dialog = builder.create();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String fullName = etFullName.getText().toString().trim();
                String selectedRoleName = spinnerRole.getSelectedItem().toString();

                String roleValue = getRoleValue(selectedRoleName);

                if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
                    CustomToast.show(ManageUsersActivity.this, "Заполните все поля");
                    return;
                }

                if (user != null) {
                    User updatedUser = new User(user.getId(), username, password, roleValue, fullName);
                    dbHelper.updateUser(updatedUser);
                    CustomToast.show(ManageUsersActivity.this, "Пользователь обновлён");
                } else {
                    User newUser = new User(0, username, password, roleValue, fullName);
                    dbHelper.addUser(newUser);
                    CustomToast.show(ManageUsersActivity.this, "Пользователь добавлен");
                }

                dialog.dismiss();
                loadUsers();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
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