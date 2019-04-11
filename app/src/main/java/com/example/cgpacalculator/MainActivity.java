package com.example.cgpacalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import model.Subject;
import sqlite.DBSubject;

public class MainActivity extends AppCompatActivity {
    EditText subjCode, subjName, subjHour;
    TextView txtCgpa;
    Spinner gradeSpinner;
    DBSubject dbSubject;
    String studentNo;
    String studentName;
    int gradePos=0;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbSubject = new DBSubject(getApplicationContext());

        subjCode = findViewById(R.id.edtSubjectCode);
        subjName = findViewById(R.id.edtSubjectName);
        subjHour = findViewById(R.id.edtCreditHour);
        gradeSpinner = findViewById(R.id.edtGradeSpinner);
        txtCgpa = findViewById(R.id.txtCGPA);
        sharedPreferences = getApplicationContext().getSharedPreferences("cgpaCalculator", MODE_PRIVATE);
        studentNo = sharedPreferences.getString("studentNo", null);
        studentName = sharedPreferences.getString("studentName", null);
        if (studentNo==null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 0);
        }
        else
            loadData();
    }

    private void loadData(){
        List<Subject> subjects = dbSubject.fnGetSubject(studentNo);
        if (!subjects.isEmpty()){
            double hour=0.0, grade=0.0, totalHour=0.0, totalGrade=0.0;
            for( Subject subject : subjects){
                totalGrade+=subject.getGrade()*subject.getCreditHour();
                totalHour+=subject.getCreditHour();
            }
            double cgpa=0.0;
            cgpa=totalGrade/totalHour;
            System.out.println(cgpa+" "+totalGrade+" "+totalHour);
            txtCgpa.setText("Your CGPA is: "+String.valueOf(cgpa));
        }
    }

    protected void saveSubj(View vw){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm?");
        builder.setMessage("Confirm to save subject?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String subjectCode =subjCode.getText().toString();
                String subjectName = subjName.getText().toString();
                String subjectHour = subjHour.getText().toString();
                double gradePoint[] = {4, 3.7, 3.3, 3, 2.7, 2.3, 2, 1.7, 1.3, 1, 0};
                gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        int item = gradeSpinner.getSelectedItemPosition();
                        gradePos = item;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                Subject subject = new Subject(subjectCode, subjectName, Double.valueOf(subjectHour), gradePoint[gradePos],studentNo);
                dbSubject.fnInsertSubject(subject);
                loadData();
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode==RESULT_OK){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("studentName",intent.getStringExtra("studentName"));
            editor.putString("studentNo",intent.getStringExtra("studentNo"));
            editor.commit();
            loadData();
        }
    }
}
