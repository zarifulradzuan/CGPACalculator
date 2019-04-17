package com.example.cgpacalculator;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import model.Subject;
import sqlite.DBSubject;

public class ListActivity extends Activity {
    RecyclerView subjectRecycler;
    String studentNo;
    DBSubject dbSubject;
    CustomAdapterSubjectList customAdapterSubjectList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();
        studentNo = intent.getStringExtra("studentNo");
        dbSubject = new DBSubject(getApplicationContext());
        final List<Subject> subjectList = dbSubject.fnGetSubjects(studentNo);
        customAdapterSubjectList = new CustomAdapterSubjectList(subjectList, this);

        subjectRecycler = findViewById(R.id.subjectRecycler);
        subjectRecycler.setAdapter(customAdapterSubjectList);
        subjectRecycler.setLayoutManager(new LinearLayoutManager(this));

        customAdapterSubjectList.setOnItemClickListener(new CustomAdapterSubjectList.ClickListener() {
            @Override
            public void onItemClick(final int position, View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("Options");
                //builder.setMessage("What do you want to do?");
                builder.setItems(new CharSequence[]{"Edit", "Delete", "Cancel"},
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch(which){
                                    case 0: Intent intent = new Intent(ListActivity.this,EditActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("subject", subjectList.get(position));
                                        intent.putExtras(bundle);
                                        startActivityForResult(intent,0);
                                        break;
                                    case 1: dbSubject.fnDeleteSubject(subjectList.get(position).getSubjectId());
                                        customAdapterSubjectList.setItems(dbSubject.fnGetSubjects(studentNo));
                                        customAdapterSubjectList.notifyDataSetChanged();
                                        break;

                                }
                            }
                        });
                /*builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbSubject.fnDeleteSubject(subjectList.get(position).getSubjectId());
                        customAdapterSubjectList.setItems(dbSubject.fnGetSubjects(studentNo));
                        customAdapterSubjectList.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(),EditActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("subject", subjectList.get(position));
                        intent.putExtras(bundle);
                        startActivityForResult(intent,0);
                    }
                });*/
                builder.show();
            }
        });
        customAdapterSubjectList.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            Bundle bundle = data.getExtras();
            Subject subject = (Subject) bundle.getSerializable("subject");
            dbSubject.fnEditSubject(subject);
            customAdapterSubjectList.setItems(dbSubject.fnGetSubjects(studentNo));
            customAdapterSubjectList.notifyDataSetChanged();
        }
    }
}
