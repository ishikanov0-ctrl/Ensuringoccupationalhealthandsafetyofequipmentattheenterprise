package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.fragments.LearningFragment;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.fragments.TestsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WorkerActivity extends BaseActivity {
    private BottomNavigationView bottomNavigationView;
    private int userId;
    private String fullName;

    {
        isRootActivity = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);

        userId = getIntent().getIntExtra("user_id", -1);
        fullName = getIntent().getStringExtra("fullname");

        if (userId == -1) {
            CustomToast.show(this, "Ошибка: пользователь не найден");
            finish();
            return;
        }

        setTitle("Работник: " + fullName);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        Fragment learningFragment = new LearningFragment();
        Bundle args = new Bundle();
        args.putInt("user_id", userId);
        learningFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, learningFragment)
                .commit();
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

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    int itemId = item.getItemId();
                    if (itemId == R.id.nav_learning) {
                        selectedFragment = new LearningFragment();
                        Bundle args = new Bundle();
                        args.putInt("user_id", userId);
                        selectedFragment.setArguments(args);
                    } else if (itemId == R.id.nav_tests) {
                        selectedFragment = new TestsFragment();
                        Bundle args = new Bundle();
                        args.putInt("user_id", userId);
                        selectedFragment.setArguments(args);
                    }
                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, selectedFragment)
                                .commit();
                    }
                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        CustomToast.show(this, "Используйте кнопку 'Выйти' для выхода");
    }
}