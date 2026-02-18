package com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.Instruction;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.Option;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.Question;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.Test;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.TestResult;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.TrainingAssignment;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.TrainingType;
import com.example.ensuringoccupationalhealthandsafetyofequipmentattheenterprise.models.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SafetyDB";
    private static final int DATABASE_VERSION = 5;

    public static final String TABLE_USERS = "users";
    public static final String TABLE_INSTRUCTIONS = "instructions";
    public static final String TABLE_TRAININGS = "trainings";
    public static final String TABLE_TRAINING_ASSIGNMENTS = "training_assignments";
    public static final String TABLE_TESTS = "tests";
    public static final String TABLE_TEST_RESULTS = "test_results";
    public static final String TABLE_QUESTIONS = "questions";
    public static final String TABLE_OPTIONS = "options";

    public static final String COL_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_ROLE = "role";
    public static final String COL_FULLNAME = "fullname";
    public static final String COL_TITLE = "title";
    public static final String COL_CONTENT = "content";
    public static final String COL_DATE = "date";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_TRAINING_ID = "training_id";
    public static final String COL_STATUS = "status";
    public static final String COL_DUE_DATE = "due_date";
    public static final String COL_QUESTIONS_COUNT = "questions_count";
    public static final String COL_DEFAULT_DUE_DATE = "default_due_date";
    public static final String COL_TEST_ID = "test_id";
    public static final String COL_SCORE = "score";
    public static final String COL_DATE_TAKEN = "date_taken";
    public static final String COL_QUESTION_TEXT = "question_text";
    public static final String COL_CORRECT_OPTION_ID = "correct_option_id";
    public static final String COL_OPTION_TEXT = "option_text";
    public static final String COL_QUESTION_ID = "question_id";

    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_USERNAME + " TEXT UNIQUE,"
            + COL_PASSWORD + " TEXT,"
            + COL_ROLE + " TEXT,"
            + COL_FULLNAME + " TEXT)";

    private static final String CREATE_INSTRUCTIONS_TABLE = "CREATE TABLE " + TABLE_INSTRUCTIONS + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_TITLE + " TEXT,"
            + COL_CONTENT + " TEXT,"
            + COL_DATE + " TEXT)";

    private static final String CREATE_TRAININGS_TABLE = "CREATE TABLE " + TABLE_TRAININGS + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_NAME + " TEXT,"
            + COL_DESCRIPTION + " TEXT,"
            + COL_DEFAULT_DUE_DATE + " TEXT)";

    private static final String CREATE_TRAINING_ASSIGNMENTS_TABLE = "CREATE TABLE " + TABLE_TRAINING_ASSIGNMENTS + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_USER_ID + " INTEGER,"
            + COL_TRAINING_ID + " INTEGER,"
            + COL_STATUS + " TEXT,"
            + COL_DUE_DATE + " TEXT,"
            + "FOREIGN KEY(" + COL_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_ID + "),"
            + "FOREIGN KEY(" + COL_TRAINING_ID + ") REFERENCES " + TABLE_TRAININGS + "(" + COL_ID + "))";

    private static final String CREATE_TESTS_TABLE = "CREATE TABLE " + TABLE_TESTS + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_TITLE + " TEXT,"
            + COL_QUESTIONS_COUNT + " INTEGER)";

    private static final String CREATE_TEST_RESULTS_TABLE = "CREATE TABLE " + TABLE_TEST_RESULTS + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_USER_ID + " INTEGER,"
            + COL_TEST_ID + " INTEGER,"
            + COL_SCORE + " INTEGER,"
            + COL_DATE_TAKEN + " TEXT,"
            + "FOREIGN KEY(" + COL_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_ID + "),"
            + "FOREIGN KEY(" + COL_TEST_ID + ") REFERENCES " + TABLE_TESTS + "(" + COL_ID + "))";

    private static final String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_QUESTIONS + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_TEST_ID + " INTEGER,"
            + COL_QUESTION_TEXT + " TEXT,"
            + COL_CORRECT_OPTION_ID + " INTEGER,"
            + "FOREIGN KEY(" + COL_TEST_ID + ") REFERENCES " + TABLE_TESTS + "(" + COL_ID + "))";

    private static final String CREATE_OPTIONS_TABLE = "CREATE TABLE " + TABLE_OPTIONS + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_QUESTION_ID + " INTEGER,"
            + COL_OPTION_TEXT + " TEXT,"
            + "FOREIGN KEY(" + COL_QUESTION_ID + ") REFERENCES " + TABLE_QUESTIONS + "(" + COL_ID + "))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_INSTRUCTIONS_TABLE);
        db.execSQL(CREATE_TRAININGS_TABLE);
        db.execSQL(CREATE_TRAINING_ASSIGNMENTS_TABLE);
        db.execSQL(CREATE_TESTS_TABLE);
        db.execSQL(CREATE_TEST_RESULTS_TABLE);
        db.execSQL(CREATE_QUESTIONS_TABLE);
        db.execSQL(CREATE_OPTIONS_TABLE);

        insertInitialData(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_USERS + " (username, password, role, fullname) VALUES " +
                "('worker', '123', 'worker', 'Иван Петров')," +
                "('engineer', '123', 'engineer', 'Мария Иванова')," +
                "('admin', '123', 'admin', 'Петр Сидоров')");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTIONS + " (title, content, date) VALUES " +
                "('Инструкция по электробезопасности', '1. Общие требования: к работе допускаются лица, прошедшие инструктаж. 2. Запрещается прикасаться к оголенным проводам. 3. При поражении током немедленно отключить напряжение и оказать первую помощь. 4. Использовать только исправное оборудование.', '2025-02-01')," +
                "('Инструкция по пожарной безопасности', '1. Запрещается курение в неположенных местах. 2. Знать места расположения огнетушителей. 3. При возгорании вызвать пожарных по телефону 01, приступить к тушению первичными средствами. 4. Эвакуироваться согласно плану.', '2025-02-10')," +
                "('Инструкция по работе с оборудованием', '1. Перед началом работы проверить исправность. 2. Использовать средства индивидуальной защиты. 3. Не оставлять работающее оборудование без присмотра. 4. При неисправности отключить и сообщить руководителю.', '2025-01-15')");

        db.execSQL("INSERT INTO " + TABLE_TRAININGS + " (name, description, default_due_date) VALUES " +
                "('Вводный инструктаж', 'Вводный инструктаж проводится со всеми вновь принятыми работниками.\n\nТекст инструктажа:\n1. Общие правила внутреннего трудового распорядка.\n2. Основные требования безопасности на территории предприятия.\n3. Действия при возникновении чрезвычайных ситуаций (пожар, авария).\n4. Правила поведения на рабочих местах.\n5. Оказание первой помощи пострадавшим.', '2025-03-10')," +
                "('Повторный инструктаж', 'Повторный инструктаж проводится не реже одного раза в 6 месяцев.\n\nТекст инструктажа:\n1. Повторение основных правил безопасности.\n2. Анализ типичных нарушений и ошибок за прошедший период.\n3. Напоминание о мерах пожарной безопасности.\n4. Обновление знаний по электробезопасности.\n5. Инструктаж по охране труда при выполнении работ.', '2025-08-01')," +
                "('Внеплановый инструктаж', 'Внеплановый инструктаж проводится при изменении технологических процессов, замене оборудования, введении новых инструкций.\n\nТекст инструктажа:\n1. Новые правила эксплуатации оборудования.\n2. Изменения в технологических картах.\n3. Дополнительные меры безопасности при работе с новыми материалами.\n4. Пересмотр инструкций по охране труда.\n5. Действия в нештатных ситуациях, связанных с изменениями.', '2025-03-15')");

        db.execSQL("INSERT INTO " + TABLE_TRAINING_ASSIGNMENTS + " (user_id, training_id, status, due_date) VALUES " +
                "(1, 1, 'Назначено', '2025-03-10')," +
                "(1, 2, 'Пройдено', '2025-02-01')," +
                "(1, 3, 'Назначено', '2025-03-15')");

        db.execSQL("INSERT INTO " + TABLE_TESTS + " (title, questions_count) VALUES " +
                "('Тест по электробезопасности', 3)," +
                "('Тест по пожарной безопасности', 3)," +
                "('Тест по оказанию первой помощи', 3)");

        db.execSQL("INSERT INTO " + TABLE_QUESTIONS + " (test_id, question_text, correct_option_id) VALUES " +
                "(1, 'Какое напряжение считается опасным для жизни человека?', 2)," +
                "(1, 'Что необходимо сделать при поражении человека электрическим током?', 5)," +
                "(1, 'Какие средства защиты необходимо использовать при работе с электроустановками?', 8)");

        db.execSQL("INSERT INTO " + TABLE_OPTIONS + " (question_id, option_text) VALUES " +
                "(1, '12 В')," +
                "(1, '220 В')," +
                "(1, '36 В')");
        db.execSQL("UPDATE " + TABLE_QUESTIONS + " SET correct_option_id = 2 WHERE id = 1");

        db.execSQL("INSERT INTO " + TABLE_OPTIONS + " (question_id, option_text) VALUES " +
                "(2, 'Позвонить врачу и ждать')," +
                "(2, 'Отключить источник тока, вызвать скорую, приступить к реанимации')," +
                "(2, 'Полить водой')");
        db.execSQL("UPDATE " + TABLE_QUESTIONS + " SET correct_option_id = 5 WHERE id = 2");

        db.execSQL("INSERT INTO " + TABLE_OPTIONS + " (question_id, option_text) VALUES " +
                "(3, 'Резиновые перчатки, диэлектрический коврик, инструмент с изоляцией')," +
                "(3, 'Только перчатки')," +
                "(3, 'Защитные очки')");
        db.execSQL("UPDATE " + TABLE_QUESTIONS + " SET correct_option_id = 8 WHERE id = 3");

        db.execSQL("INSERT INTO " + TABLE_QUESTIONS + " (test_id, question_text, correct_option_id) VALUES " +
                "(2, 'Какой огнетушитель используется для тушения электроустановок под напряжением?', 11)," +
                "(2, 'Что делать при возгорании одежды на человеке?', 14)," +
                "(2, 'Какой номер телефона для вызова пожарной охраны?', 17)");

        db.execSQL("INSERT INTO " + TABLE_OPTIONS + " (question_id, option_text) VALUES " +
                "(4, 'Пенный'),(4, 'Порошковый'),(4, 'Углекислотный')");
        db.execSQL("UPDATE " + TABLE_QUESTIONS + " SET correct_option_id = 11 WHERE id = 4");

        db.execSQL("INSERT INTO " + TABLE_OPTIONS + " (question_id, option_text) VALUES " +
                "(5, 'Бежать, чтобы сбить пламя'),(5, 'Кататься по земле, накрыть плотной тканью'),(5, 'Залить водой')");
        db.execSQL("UPDATE " + TABLE_QUESTIONS + " SET correct_option_id = 14 WHERE id = 5");

        db.execSQL("INSERT INTO " + TABLE_OPTIONS + " (question_id, option_text) VALUES " +
                "(6, '01'),(6, '02'),(6, '03')");
        db.execSQL("UPDATE " + TABLE_QUESTIONS + " SET correct_option_id = 17 WHERE id = 6");

        db.execSQL("INSERT INTO " + TABLE_QUESTIONS + " (test_id, question_text, correct_option_id) VALUES " +
                "(3, 'Как правильно начать сердечно-легочную реанимацию?', 20)," +
                "(3, 'Что делать при артериальном кровотечении?', 23)," +
                "(3, 'Как транспортировать пострадавшего с подозрением на травму позвоночника?', 26)");

        db.execSQL("INSERT INTO " + TABLE_OPTIONS + " (question_id, option_text) VALUES " +
                "(7, 'Сделать искусственное дыхание'),(7, 'Начать с непрямого массажа сердца'),(7, 'Проверить пульс и вызвать скорую')");
        db.execSQL("UPDATE " + TABLE_QUESTIONS + " SET correct_option_id = 20 WHERE id = 7");

        db.execSQL("INSERT INTO " + TABLE_OPTIONS + " (question_id, option_text) VALUES " +
                "(8, 'Наложить жгут выше раны, зафиксировать время'),(8, 'Промыть рану водой'),(8, 'Наложить давящую повязку')");
        db.execSQL("UPDATE " + TABLE_QUESTIONS + " SET correct_option_id = 23 WHERE id = 8");

        db.execSQL("INSERT INTO " + TABLE_OPTIONS + " (question_id, option_text) VALUES " +
                "(9, 'Усадить пострадавшего'),(9, 'Переносить на руках'),(9, 'На жестких носилках, не меняя положения тела')");
        db.execSQL("UPDATE " + TABLE_QUESTIONS + " SET correct_option_id = 26 WHERE id = 9");

        db.execSQL("INSERT INTO " + TABLE_TEST_RESULTS + " (user_id, test_id, score, date_taken) VALUES " +
                "(1, 1, 100, '2025-02-05')," +
                "(1, 2, 67, '2025-02-10')");
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, user.getUsername());
        values.put(COL_PASSWORD, user.getPassword());
        values.put(COL_ROLE, user.getRole());
        values.put(COL_FULLNAME, user.getFullName());
        db.update(TABLE_USERS, values, COL_ID + "=?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTRUCTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAININGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING_ASSIGNMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TESTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST_RESULTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPTIONS);
        onCreate(db);
    }

    public User getUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_ID, COL_USERNAME, COL_PASSWORD, COL_ROLE, COL_FULLNAME};
        String selection = COL_USERNAME + "=? AND " + COL_PASSWORD + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Log.d("DB", "Найден пользователь: " + cursor.getString(1));
            User user = new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            cursor.close();
            db.close();
            return user;
        } else {
            Log.d("DB", "Пользователь не найден");
        }
        if (cursor != null) cursor.close();
        db.close();
        return null;
    }

    public List<Instruction> getAllInstructions() {
        List<Instruction> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_INSTRUCTIONS, null, null, null, null, null, COL_DATE + " DESC");
        while (cursor.moveToNext()) {
            list.add(new Instruction(
                    cursor.getInt(cursor.getColumnIndex(COL_ID)),
                    cursor.getString(cursor.getColumnIndex(COL_TITLE)),
                    cursor.getString(cursor.getColumnIndex(COL_CONTENT)),
                    cursor.getString(cursor.getColumnIndex(COL_DATE))
            ));
        }
        cursor.close();
        db.close();
        return list;
    }

    public void addInstruction(Instruction instruction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, instruction.getTitle());
        values.put(COL_CONTENT, instruction.getContent());
        values.put(COL_DATE, instruction.getDate());
        db.insert(TABLE_INSTRUCTIONS, null, values);
        db.close();
    }

    public List<TrainingAssignment> getAssignmentsForUser(int userId) {
        List<TrainingAssignment> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT ta.*, t.name as training_name, t.description as training_desc FROM " + TABLE_TRAINING_ASSIGNMENTS + " ta " +
                        "JOIN " + TABLE_TRAININGS + " t ON ta." + COL_TRAINING_ID + " = t." + COL_ID +
                        " WHERE ta." + COL_USER_ID + " = ?", new String[]{String.valueOf(userId)});
        while (cursor.moveToNext()) {
            TrainingAssignment ta = new TrainingAssignment();
            ta.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            ta.setUserId(userId);
            ta.setTrainingId(cursor.getInt(cursor.getColumnIndex(COL_TRAINING_ID)));
            ta.setTrainingName(cursor.getString(cursor.getColumnIndex("training_name")));
            ta.setDescription(cursor.getString(cursor.getColumnIndex("training_desc")));
            ta.setStatus(cursor.getString(cursor.getColumnIndex(COL_STATUS)));
            ta.setDueDate(cursor.getString(cursor.getColumnIndex(COL_DUE_DATE)));
            list.add(ta);
        }
        cursor.close();
        db.close();
        return list;
    }

    public void addTrainingType(String name, String description, String defaultDueDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_DESCRIPTION, description);
        values.put(COL_DEFAULT_DUE_DATE, defaultDueDate);
        db.insert(TABLE_TRAININGS, null, values);
        db.close();
    }

    public List<TrainingAssignment> getAllAssignments() {
        List<TrainingAssignment> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT ta.*, u.fullname as user_name, t.name as training_name, t.description as training_desc FROM " + TABLE_TRAINING_ASSIGNMENTS + " ta " +
                        "JOIN " + TABLE_USERS + " u ON ta." + COL_USER_ID + " = u." + COL_ID +
                        " JOIN " + TABLE_TRAININGS + " t ON ta." + COL_TRAINING_ID + " = t." + COL_ID, null);
        while (cursor.moveToNext()) {
            TrainingAssignment ta = new TrainingAssignment();
            ta.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            ta.setUserId(cursor.getInt(cursor.getColumnIndex(COL_USER_ID)));
            ta.setTrainingId(cursor.getInt(cursor.getColumnIndex(COL_TRAINING_ID)));
            ta.setTrainingName(cursor.getString(cursor.getColumnIndex("training_name")));
            ta.setDescription(cursor.getString(cursor.getColumnIndex("training_desc")));
            ta.setStatus(cursor.getString(cursor.getColumnIndex(COL_STATUS)));
            ta.setDueDate(cursor.getString(cursor.getColumnIndex(COL_DUE_DATE)));
            ta.setUserName(cursor.getString(cursor.getColumnIndex("user_name")));
            list.add(ta);
        }
        cursor.close();
        db.close();
        return list;
    }

    public void addAssignment(int userId, int trainingId, String dueDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_ID, userId);
        values.put(COL_TRAINING_ID, trainingId);
        values.put(COL_STATUS, "Назначено");
        values.put(COL_DUE_DATE, dueDate);
        db.insert(TABLE_TRAINING_ASSIGNMENTS, null, values);
        db.close();
    }

    public void updateAssignmentStatus(int assignmentId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STATUS, status);
        db.update(TABLE_TRAINING_ASSIGNMENTS, values, COL_ID + "=?", new String[]{String.valueOf(assignmentId)});
        db.close();
    }

    public List<Test> getAllTests() {
        List<Test> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TESTS, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            list.add(new Test(
                    cursor.getInt(cursor.getColumnIndex(COL_ID)),
                    cursor.getString(cursor.getColumnIndex(COL_TITLE)),
                    cursor.getInt(cursor.getColumnIndex(COL_QUESTIONS_COUNT)),
                    null
            ));
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Question> getQuestionsForTest(int testId) {
        List<Question> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_QUESTIONS, null, COL_TEST_ID + "=?",
                new String[]{String.valueOf(testId)}, null, null, null);
        while (cursor.moveToNext()) {
            Question q = new Question();
            q.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            q.setTestId(testId);
            q.setQuestionText(cursor.getString(cursor.getColumnIndex(COL_QUESTION_TEXT)));
            q.setCorrectOptionId(cursor.getInt(cursor.getColumnIndex(COL_CORRECT_OPTION_ID)));

            Cursor optCursor = db.query(TABLE_OPTIONS, null, COL_QUESTION_ID + "=?",
                    new String[]{String.valueOf(q.getId())}, null, null, null);
            List<Option> options = new ArrayList<>();
            while (optCursor.moveToNext()) {
                Option o = new Option();
                o.setId(optCursor.getInt(optCursor.getColumnIndex(COL_ID)));
                o.setQuestionId(q.getId());
                o.setOptionText(optCursor.getString(optCursor.getColumnIndex(COL_OPTION_TEXT)));
                options.add(o);
            }
            optCursor.close();
            q.setOptions(options);
            list.add(q);
        }
        cursor.close();
        db.close();
        return list;
    }

    public void saveTestResult(int userId, int testId, int score, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_ID, userId);
        values.put(COL_TEST_ID, testId);
        values.put(COL_SCORE, score);
        values.put(COL_DATE_TAKEN, date);
        db.insert(TABLE_TEST_RESULTS, null, values);
        db.close();
    }

    public List<TestResult> getTestResults() {
        List<TestResult> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT tr.*, u.fullname as user_name, t.title as test_title FROM " + TABLE_TEST_RESULTS + " tr " +
                        "JOIN " + TABLE_USERS + " u ON tr." + COL_USER_ID + " = u." + COL_ID +
                        " JOIN " + TABLE_TESTS + " t ON tr." + COL_TEST_ID + " = t." + COL_ID, null);
        while (cursor.moveToNext()) {
            TestResult tr = new TestResult();
            tr.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            tr.setUserId(cursor.getInt(cursor.getColumnIndex(COL_USER_ID)));
            tr.setTestId(cursor.getInt(cursor.getColumnIndex(COL_TEST_ID)));
            tr.setScore(cursor.getInt(cursor.getColumnIndex(COL_SCORE)));
            tr.setDateTaken(cursor.getString(cursor.getColumnIndex(COL_DATE_TAKEN)));
            tr.setUserName(cursor.getString(cursor.getColumnIndex("user_name")));
            tr.setTestTitle(cursor.getString(cursor.getColumnIndex("test_title")));
            list.add(tr);
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            list.add(new User(
                    cursor.getInt(cursor.getColumnIndex(COL_ID)),
                    cursor.getString(cursor.getColumnIndex(COL_USERNAME)),
                    cursor.getString(cursor.getColumnIndex(COL_PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(COL_ROLE)),
                    cursor.getString(cursor.getColumnIndex(COL_FULLNAME))
            ));
        }
        cursor.close();
        db.close();
        return list;
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, user.getUsername());
        values.put(COL_PASSWORD, user.getPassword());
        values.put(COL_ROLE, user.getRole());
        values.put(COL_FULLNAME, user.getFullName());
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public void deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, COL_ID + "=?", new String[]{String.valueOf(userId)});
        db.close();
    }

    public List<User> getWorkers() {
        List<User> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, COL_ROLE + "=?", new String[]{"worker"}, null, null, null);
        while (cursor.moveToNext()) {
            list.add(new User(
                    cursor.getInt(cursor.getColumnIndex(COL_ID)),
                    cursor.getString(cursor.getColumnIndex(COL_USERNAME)),
                    cursor.getString(cursor.getColumnIndex(COL_PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(COL_ROLE)),
                    cursor.getString(cursor.getColumnIndex(COL_FULLNAME))
            ));
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<TrainingType> getAllTrainingTypes() {
        List<TrainingType> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TRAININGS, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            TrainingType tt = new TrainingType();
            tt.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            tt.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
            tt.setDescription(cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION)));
            tt.setDefaultDueDate(cursor.getString(cursor.getColumnIndex(COL_DEFAULT_DUE_DATE)));
            list.add(tt);
        }
        cursor.close();
        db.close();
        return list;
    }

    public void addTrainingType(String name, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_DESCRIPTION, description);
        db.insert(TABLE_TRAININGS, null, values);
        db.close();
    }
}