package com.crudapplication;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

public class StudentListActivity extends AppCompatActivity {

    private ListView lvStudents;
    private DatabaseHelper databaseHelper;
    private ArrayList<String> studentList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        lvStudents = findViewById(R.id.lv_students);
        databaseHelper = new DatabaseHelper(this);

        loadStudentData();

        lvStudents.setOnItemClickListener((parent, view, position, id) -> {

            String selectedStudent = studentList.get(position);

            showUpdateDeleteDialog(selectedStudent);
        });
    }

    private void loadStudentData() {
        studentList = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllStudents();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                studentList.add(name);
            } while (cursor.moveToNext());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        lvStudents.setAdapter(adapter);
    }

    private void showUpdateDeleteDialog(String selectedStudent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_update_delete, null);
        builder.setView(dialogView);

        EditText etUpdateName = dialogView.findViewById(R.id.et_update_name);
        EditText etUpdateNim = dialogView.findViewById(R.id.et_update_nim);
        EditText etUpdateDepartment = dialogView.findViewById(R.id.et_update_department);
        EditText etUpdateAgama = dialogView.findViewById(R.id.et_update_agama);
        EditText etUpdateGender = dialogView.findViewById(R.id.et_update_gender);
        EditText etUpdateDob = dialogView.findViewById(R.id.et_update_dob);
        EditText etUpdateAddress = dialogView.findViewById(R.id.et_update_address);

        Button btnUpdate = dialogView.findViewById(R.id.btn_update);
        Button btnDelete = dialogView.findViewById(R.id.btn_delete);

        Cursor cursor = databaseHelper.getStudentByName(selectedStudent);

        if (cursor != null && cursor.moveToFirst()) {
            etUpdateName.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            etUpdateNim.setText(cursor.getString(cursor.getColumnIndexOrThrow("nim")));
            etUpdateDepartment.setText(cursor.getString(cursor.getColumnIndexOrThrow("department")));
            etUpdateAgama.setText(cursor.getString(cursor.getColumnIndex("agama")));
            etUpdateGender.setText(cursor.getString(cursor.getColumnIndexOrThrow("gender")));
            etUpdateDob.setText(cursor.getString(cursor.getColumnIndexOrThrow("dob")));
            etUpdateAddress.setText(cursor.getString(cursor.getColumnIndexOrThrow("address")));
            cursor.close();
        }

        AlertDialog dialog = builder.create();
        dialog.show();

        btnUpdate.setOnClickListener(v -> {
            String newName = etUpdateName.getText().toString();
            String newNim = etUpdateNim.getText().toString();
            String newDepartment = etUpdateDepartment.getText().toString();
            String newAgama = etUpdateAgama.getText().toString();
            String newGender = etUpdateGender.getText().toString();
            String newDob = etUpdateDob.getText().toString();
            String newAddress = etUpdateAddress.getText().toString();

            if (!newName.isEmpty() && !newNim.isEmpty()) {
                databaseHelper.updateStudent(selectedStudent, newName, newNim, newDepartment, newAgama, newGender, newDob, newAddress);
                loadStudentData();
                Toast.makeText(this, "Data telah diupdate", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(v -> {
            databaseHelper.deleteStudent(selectedStudent);
            loadStudentData();
            Toast.makeText(this, "Data telah dihapus", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
    }
}