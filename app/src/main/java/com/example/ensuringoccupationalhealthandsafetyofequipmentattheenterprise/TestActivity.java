package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.database.DatabaseHelper;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.Option;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.Question;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.QuestionResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TestActivity extends BaseActivity {
    private TextView tvQuestion, tvProgress;
    private RadioGroup radioGroup;
    private Button btnNext;
    private DatabaseHelper dbHelper;
    private int userId, testId;
    private List<Question> questions;
    private int currentIndex = 0;
    private int correctAnswers = 0;
    private String testTitle;

    private List<Integer> userAnswers = new ArrayList<>();
    private ArrayList<QuestionResult> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        testId = getIntent().getIntExtra("test_id", -1);
        userId = getIntent().getIntExtra("user_id", -1);
        testTitle = getIntent().getStringExtra("test_title");

        if (testId == -1 || userId == -1) {
            CustomToast.show(this, "Ошибка: тест не найден");
            finish();
            return;
        }

        setTitle(testTitle);

        dbHelper = new DatabaseHelper(this);
        questions = dbHelper.getQuestionsForTest(testId);

        if (questions == null || questions.isEmpty()) {
            CustomToast.showLong(this, "В тесте нет вопросов");
            finish();
            return;
        }

        tvQuestion = findViewById(R.id.tvQuestion);
        tvProgress = findViewById(R.id.tvProgress);
        radioGroup = findViewById(R.id.radioGroup);
        btnNext = findViewById(R.id.btnNext);

        showQuestion();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    CustomToast.show(TestActivity.this, "Выберите ответ");
                    return;
                }

                RadioButton selected = findViewById(selectedId);
                int selectedOptionId = (int) selected.getTag();

                userAnswers.add(selectedOptionId);

                Question current = questions.get(currentIndex);
                if (selectedOptionId == current.getCorrectOptionId()) {
                    correctAnswers++;
                }

                currentIndex++;
                if (currentIndex < questions.size()) {
                    showQuestion();
                } else {
                    prepareResults();

                    int score = (int) ((float) correctAnswers / questions.size() * 100);
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    dbHelper.saveTestResult(userId, testId, score, date);

                    Intent intent = new Intent(TestActivity.this, TestResultsActivity.class);
                    intent.putExtra("score", correctAnswers);
                    intent.putExtra("total", questions.size());
                    intent.putExtra("results", results);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void showQuestion() {
        radioGroup.clearCheck();
        Question q = questions.get(currentIndex);
        tvQuestion.setText(q.getQuestionText());
        tvProgress.setText("Вопрос " + (currentIndex + 1) + " из " + questions.size());

        radioGroup.removeAllViews();
        for (Option opt : q.getOptions()) {
            RadioButton rb = new RadioButton(this);
            rb.setText(opt.getOptionText());
            rb.setTag(opt.getId());
            radioGroup.addView(rb);
        }
    }

    private void prepareResults() {
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            int userAnswerId = userAnswers.get(i);
            boolean isCorrect = (userAnswerId == q.getCorrectOptionId());

            String userAnswerText = "";
            for (Option opt : q.getOptions()) {
                if (opt.getId() == userAnswerId) {
                    userAnswerText = opt.getOptionText();
                    break;
                }
            }

            String correctAnswerText = "";
            for (Option opt : q.getOptions()) {
                if (opt.getId() == q.getCorrectOptionId()) {
                    correctAnswerText = opt.getOptionText();
                    break;
                }
            }

            results.add(new QuestionResult(
                    q.getQuestionText(),
                    userAnswerText,
                    correctAnswerText,
                    isCorrect
            ));
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