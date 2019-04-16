package com.example.cgpacalculator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import model.Subject;

/**
 * Created by user on 3/20/2019.
 */

public class CustomAdapterSubjectList extends RecyclerView.Adapter<CustomAdapterSubjectList.ViewHolder>  {

    List<Subject> subjects;
    private static ClickListener clickListener;
    Context context;
    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public CustomAdapterSubjectList (List<Subject> subjects, Context context){
        this.subjects = subjects;
        this.context = context;
    }

    public void setItems(List<Subject> subjects){
        this.subjects = subjects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_subjects_recycler,parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CustomAdapterSubjectList.ViewHolder holder, int position){
        Subject subject = subjects.get(position);
        System.out.println(subject.getSubjectCode()+" "+subject.getSubjectName()+" "+subject.getCreditHour()+ " "+subject.getGrade()+" "+subject.getStudentNo());
        holder.txtVwSubjectName.setText(subject.getSubjectName());
        holder.txtVwSubjectCode.setText(subject.getSubjectCode());
        Resources res = context.getResources();
        TypedArray gradeChar = res.obtainTypedArray(R.array.grade);
        holder.txtVwSubjectGrade.setText(gradeChar.getString(subject.getGrade()));
        holder.txtVwSubjectHour.setText(String.valueOf(subject.getCreditHour()));
    }

    @Override
    public int getItemCount(){
        return subjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtVwSubjectName, txtVwSubjectCode, txtVwSubjectGrade, txtVwSubjectHour;

        public ViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            txtVwSubjectName = (TextView) itemView.findViewById(R.id.txtVwSubjName);
            txtVwSubjectGrade = (TextView) itemView.findViewById(R.id.txtVwSubjGrade);
            txtVwSubjectCode = (TextView) itemView.findViewById(R.id.txtVwSubjCode);
            txtVwSubjectHour = (TextView) itemView.findViewById(R.id.txtVwSubjHour);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);

        }


    }


}
