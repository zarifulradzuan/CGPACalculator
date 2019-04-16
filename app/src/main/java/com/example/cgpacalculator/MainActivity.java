package com.example.cgpacalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbSubject = new DBSubject(getApplicationContext());

        subjCode = findViewById(R.id.addSubjectCode);
        subjName = findViewById(R.id.addSubjectName);
        subjHour = findViewById(R.id.addCreditHour);
        gradeSpinner = findViewById(R.id.addGradeSpinner);
        gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gradeSpinner.setSelection(0);
            }
        });
        txtCgpa = findViewById(R.id.txtCGPA);
        sharedPreferences = getApplicationContext().getSharedPreferences("cgpaCalculator", MODE_PRIVATE);
        studentNo = sharedPreferences.getString("studentNo", null);
        studentName = sharedPreferences.getString("studentName", null);
        if (studentNo==null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 0);
        }
        else{
            Toast.makeText(getApplicationContext(),"Welcome "+studentName,Toast.LENGTH_SHORT).show();
            loadData();
        }
    }

    private void loadData(){
        List<Subject> subjects = dbSubject.fnGetSubjects(studentNo);
        if (!subjects.isEmpty()){
            double gradePoint[] = {4, 3.7, 3.3, 3, 2.7, 2.3, 2, 1.7, 1.3, 1, 0};
            double totalHour=0.0, totalGrade=0.0;
            Resources res = getResources();
            TypedArray gradeChar = res.obtainTypedArray(R.array.grade);
            for( Subject subject : subjects){

                //System.out.println(subject.getSubjectName()+" "+subject.getSubjectCode()+" "+gradePoint[subject.getGrade()]+" "+gradeChar.getString(subject.getGrade()));
                totalGrade+=gradePoint[subject.getGrade()]*subject.getCreditHour();
                totalHour+=subject.getCreditHour();
            }
            txtCgpa.setText("Your CGPA is: "+String.valueOf(totalGrade/totalHour));
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
                int grade = gradeSpinner.getSelectedItemPosition();
                System.out.println(subjectCode+" "+subjectName+" "+subjectHour+ " "+ grade+" "+ studentNo);
                Subject subject = new Subject(subjectCode, subjectName, Double.valueOf(subjectHour), gradeSpinner.getSelectedItemPosition(),studentNo);
                dbSubject.fnInsertSubject(subject);
                loadData();
                subjCode.setText("");
                subjHour.setText("");
                subjName.setText("");
                gradeSpinner.setSelection(0);
            }
        });
        builder.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == 0){
            if(resultCode==RESULT_OK){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("studentName",intent.getStringExtra("studentName"));
                editor.putString("studentNo",intent.getStringExtra("studentNo"));
                editor.commit();
                finish();
                startActivity(getIntent());
            }
            else
                finish();
        }
        else
            loadData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idMenu = item.getItemId();
        Intent intent = null;

        if(idMenu == R.id.menuListSubject){
            intent = new Intent(getApplicationContext(),ListActivity.class);
            intent.putExtra("studentNo", studentNo);
            startActivityForResult(intent,1);
        }
        if(idMenu == R.id.menuLogOut){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            finish();
            startActivity(getIntent());
        }
        return super.onOptionsItemSelected(item);
    }

}






