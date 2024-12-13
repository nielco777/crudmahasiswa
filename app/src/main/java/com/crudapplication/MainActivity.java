package com.crudapplication;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private EditText etNim, etName, etDob, etAddress;
    private Spinner spDepartment;
    private Spinner spAgama;
    private RadioGroup rgGender;
    private Button btnSave, btnView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNim = findViewById(R.id.et_nim);
        etName = findViewById(R.id.et_name);
        spDepartment = findViewById(R.id.sp_department);
        spAgama = findViewById(R.id.sp_agama);
        etDob = findViewById(R.id.et_dob);
        etAddress = findViewById(R.id.et_address);
        rgGender = findViewById(R.id.rg_gender);
        btnSave = findViewById(R.id.btn_save);
        btnView = findViewById(R.id.btn_view);

        databaseHelper = new DatabaseHelper(this);

        btnSave.setOnClickListener(view -> {
            String nim = etNim.getText().toString();
            String name = etName.getText().toString();
            String department = spDepartment.getSelectedItem().toString();
            String agama = spAgama.getSelectedItem().toString();
            String gender = ((RadioButton) findViewById(rgGender.getCheckedRadioButtonId())).getText().toString();
            String dob = etDob.getText().toString();
            String address = etAddress.getText().toString();

            boolean success = databaseHelper.insertStudent(nim, name, department, agama, gender, dob, address);
            Toast.makeText(this, success ? "Data Tersimpan!" : "Gagal Menyimpan Data", Toast.LENGTH_SHORT).show();
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StudentListActivity.class);
                startActivity(intent);
            }
        });

    }
}
