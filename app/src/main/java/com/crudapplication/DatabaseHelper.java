package com.crudapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "students";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nim TEXT, " +
                "name TEXT, " +
                "department TEXT, " +
                "agama Text, " +
                "gender TEXT, " +
                "dob TEXT, " +
                "address TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertStudent(String nim, String name, String department, String gender, String dob, String agama, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nim", nim);
        values.put("name", name);
        values.put("department", department);
        values.put("agama", agama);
        values.put("gender", gender);
        values.put("dob", dob);
        values.put("address", address);

        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean updateStudent(String oldName, String newName, String newNim, String newDepartment, String newAgama, String newGender, String newDob, String newAddress) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", newName);
        values.put("nim", newNim);
        values.put("department", newDepartment);
        values.put("agama", newAgama);
        values.put("gender", newGender);
        values.put("dob", newDob);
        values.put("address", newAddress);

        int result = db.update("students", values, "name = ?", new String[]{oldName});
        return result > 0;
    }

    public boolean deleteStudent(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("students", "name = ?", new String[]{name});
        return result > 0;
    }

    public Cursor getStudentByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM students WHERE name = ?", new String[]{name});
    }
}
