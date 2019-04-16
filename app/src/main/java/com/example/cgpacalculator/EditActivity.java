package com.example.cgpacalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import model.Subject;

public class EditActivity extends AppCompatActivity {
    EditText subjCode, subjName, subjHour;
    Spinner gradeSpinner;
    Subject subject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        subjCode = findViewById(R.id.editSubjectCode);
        subjName = findViewById(R.id.editSubjectName);
        subjHour = findViewById(R.id.editCreditHour);
        gradeSpinner = findViewById(R.id.editGradeSpinner);
        gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gradeSpinner.setSelection(0);
            }
        });
        subject = (Subject) intent.getExtras().getSerializable("subject");
        subjCode.setText(subject.getSubjectCode());
        subjName.setText(subject.getSubjectName());
        subjHour.setText(String.valueOf(subject.getCreditHour()));
        gradeSpinner.setSelection(subject.getGrade());
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
                subject.setSubjectCode(subjectCode);
                String subjectName = subjName.getText().toString();
                subject.setSubjectName(subjectName);
                String subjectHour = subjHour.getText().toString();
                subject.setCreditHour(Double.valueOf(subjectHour));
                subject.setGrade(gradeSpinner.getSelectedItemPosition());
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("subject", subject);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        builder.show();

    }
}
