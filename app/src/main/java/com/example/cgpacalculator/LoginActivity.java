package com.example.cgpacalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import sqlite.DBSubject;

public class LoginActivity extends AppCompatActivity {
    EditText edtStudentName;
    EditText edtStudentNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtStudentName = findViewById(R.id.editStudentName);
        edtStudentNo = findViewById(R.id.editStudentNo);
    }

    protected void login(View vw){
        String studentName = edtStudentName.getText().toString();
        String studentNo = edtStudentNo.getText().toString();

        DBSubject dbSubject = new DBSubject(getApplicationContext());
        try {
            dbSubject.fnInsertStudent(studentName, studentNo);
        }catch (Exception e){

        }
        Intent intent = new Intent();
        intent.putExtra("studentName", studentName);
        intent.putExtra("studentNo", studentNo);
        setResult(RESULT_OK, intent);
        finish();
    }
}

