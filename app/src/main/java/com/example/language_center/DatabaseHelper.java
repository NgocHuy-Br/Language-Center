package com.example.language_center;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LanguageCenter.db";
    private static final int DATABASE_VERSION = 1;

    // Table User
    private static final String TABLE_USER = "User";
    private static final String USER_ID = "id";
    private static final String USER_USERNAME = "username";
    private static final String USER_PASSWORD = "password";

    // Table Student
    private static final String TABLE_STUDENT = "Student";
    private static final String STUDENT_CODE = "code";
    private static final String STUDENT_NAME = "name";
    private static final String STUDENT_LEVEL = "level";
    private static final String STUDENT_CLASS = "class_name";

    // Table Teacher
    private static final String TABLE_TEACHER = "Teacher";
    private static final String TEACHER_CODE = "code";
    private static final String TEACHER_NAME = "name";
    private static final String TEACHER_LANGUAGE = "language";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + TABLE_USER + " (" +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_USERNAME + " TEXT UNIQUE NOT NULL, " +
                USER_PASSWORD + " TEXT NOT NULL)";
        db.execSQL(createUserTable);

        String createStudentTable = "CREATE TABLE " + TABLE_STUDENT + " (" +
                STUDENT_CODE + " TEXT PRIMARY KEY, " +
                STUDENT_NAME + " TEXT NOT NULL, " +
                STUDENT_LEVEL + " TEXT NOT NULL, " +
                STUDENT_CLASS + " TEXT NOT NULL)";
        db.execSQL(createStudentTable);

        String createTeacherTable = "CREATE TABLE " + TABLE_TEACHER + " (" +
                TEACHER_CODE + " TEXT PRIMARY KEY, " +
                TEACHER_NAME + " TEXT NOT NULL, " +
                TEACHER_LANGUAGE + " TEXT NOT NULL)";
        db.execSQL(createTeacherTable);

        ContentValues values = new ContentValues();
        values.put(USER_USERNAME, "admin");
        values.put(USER_PASSWORD, "123456");
        db.insert(TABLE_USER, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHER);
        onCreate(db);
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,
                new String[]{USER_ID},
                USER_USERNAME + "=? AND " + USER_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkStudentExists(String code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENT, new String[]{STUDENT_CODE}, STUDENT_CODE + "=?", new String[]{code}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_CODE, student.getCode());
        values.put(STUDENT_NAME, student.getName());
        values.put(STUDENT_LEVEL, student.getLevel());
        values.put(STUDENT_CLASS, student.getClassName());
        long result = db.insert(TABLE_STUDENT, null, values);
        return result != -1;
    }

    public boolean updateStudent(String oldCode, Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_CODE, student.getCode());
        values.put(STUDENT_NAME, student.getName());
        values.put(STUDENT_LEVEL, student.getLevel());
        values.put(STUDENT_CLASS, student.getClassName());
        int result = db.update(TABLE_STUDENT, values, STUDENT_CODE + "=?", new String[]{oldCode});
        return result > 0;
    }

    public boolean deleteStudent(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_STUDENT, STUDENT_CODE + "=?", new String[]{code});
        return result > 0;
    }

    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_STUDENT, null);
    }

    public boolean checkTeacherExists(String code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TEACHER, new String[]{TEACHER_CODE}, TEACHER_CODE + "=?", new String[]{code}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean addTeacher(Teacher teacher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEACHER_CODE, teacher.getCode());
        values.put(TEACHER_NAME, teacher.getName());
        values.put(TEACHER_LANGUAGE, teacher.getLanguage());
        long result = db.insert(TABLE_TEACHER, null, values);
        return result != -1;
    }

    public boolean updateTeacher(String oldCode, Teacher teacher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEACHER_CODE, teacher.getCode());
        values.put(TEACHER_NAME, teacher.getName());
        values.put(TEACHER_LANGUAGE, teacher.getLanguage());
        int result = db.update(TABLE_TEACHER, values, TEACHER_CODE + "=?", new String[]{oldCode});
        return result > 0;
    }

    public boolean deleteTeacher(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_TEACHER, TEACHER_CODE + "=?", new String[]{code});
        return result > 0;
    }

    public Cursor getAllTeachers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TEACHER, null);
    }
}
