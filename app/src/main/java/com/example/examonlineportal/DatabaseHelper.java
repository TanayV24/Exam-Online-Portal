package com.example.examonlineportal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "exam_portal.db";
    public static final int DATABASE_VERSION = 1;

    // User table
    public static final String TABLE_USER = "users";
    public static final String COL_USER_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_ROLE = "role";

    // Exam table
    public static final String TABLE_EXAM = "exams";
    public static final String COL_EXAM_ID = "exam_id";
    public static final String COL_EXAM_NAME = "exam_name";
    public static final String COL_DURATION = "duration_minutes";

    // Question table
    public static final String TABLE_QUESTION = "questions";
    public static final String COL_QUESTION_ID = "question_id";
    public static final String COL_QUESTION_TEXT = "question_text";
    public static final String COL_OPTION_A = "option_a";
    public static final String COL_OPTION_B = "option_b";
    public static final String COL_OPTION_C = "option_c";
    public static final String COL_OPTION_D = "option_d";
    public static final String COL_CORRECT_OPTION = "correct_option";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        String createUserTable = "CREATE TABLE " + TABLE_USER + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT UNIQUE, " +
                COL_PASSWORD + " TEXT, " +
                COL_ROLE + " TEXT)";
        db.execSQL(createUserTable);

        // Create exams table
        String createExamTable = "CREATE TABLE " + TABLE_EXAM + " (" +
                COL_EXAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EXAM_NAME + " TEXT, " +
                COL_DURATION + " INTEGER)";
        db.execSQL(createExamTable);

        // Create questions table
        String createQuestionTable = "CREATE TABLE " + TABLE_QUESTION + " (" +
                COL_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EXAM_ID + " INTEGER, " +
                COL_QUESTION_TEXT + " TEXT, " +
                COL_OPTION_A + " TEXT, " +
                COL_OPTION_B + " TEXT, " +
                COL_OPTION_C + " TEXT, " +
                COL_OPTION_D + " TEXT, " +
                COL_CORRECT_OPTION + " TEXT, " +
                "FOREIGN KEY(" + COL_EXAM_ID + ") REFERENCES " + TABLE_EXAM + "(" + COL_EXAM_ID + "))";
        db.execSQL(createQuestionTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    // Register user
    public boolean registerUser(String username, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        values.put(COL_ROLE, role);
        long result = db.insert(TABLE_USER, null, values);
        return result != -1;
    }

    // Login user
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER +
                " WHERE " + COL_USERNAME + "=? AND " + COL_PASSWORD + "=?", new String[]{username, password});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // Insert exam
    public long insertExam(String examName, int durationMinutes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EXAM_NAME, examName);
        values.put(COL_DURATION, durationMinutes);
        return db.insert(TABLE_EXAM, null, values);
    }

    // Insert question
    public void insertQuestion(int examId, String questionText,
                               String a, String b, String c, String d, String correct) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EXAM_ID, examId);
        values.put(COL_QUESTION_TEXT, questionText);
        values.put(COL_OPTION_A, a);
        values.put(COL_OPTION_B, b);
        values.put(COL_OPTION_C, c);
        values.put(COL_OPTION_D, d);
        values.put(COL_CORRECT_OPTION, correct);
        db.insert(TABLE_QUESTION, null, values);
    }

    // Seed data: insert dummy exams + questions
    public void seedData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_EXAM, null);
        if (cursor.moveToFirst() && cursor.getInt(0) > 0) {
            cursor.close();
            return; // Already seeded
        }
        cursor.close();

        long mathExamId = insertExam("Math Test", 30);
        long sciExamId = insertExam("Science Quiz", 25);

        insertQuestion((int) mathExamId, "What is 5 + 3?", "6", "7", "8", "9", "C");
        insertQuestion((int) mathExamId, "What is 9 - 4?", "3", "4", "5", "6", "C");

        insertQuestion((int) sciExamId, "Which planet is known as the Red Planet?", "Earth", "Mars", "Jupiter", "Venus", "B");
        insertQuestion((int) sciExamId, "Which gas do plants absorb from the air?", "Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen", "C");
    }
}
