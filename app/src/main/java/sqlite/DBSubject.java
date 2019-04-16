package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import model.Subject;

/**
 * Created by user on 3/20/2019.
 */

public class DBSubject extends SQLiteOpenHelper {
    public static final String dbName = "dbSubject";
    public static final String tblNameSubject = "subject";
    public static final String tblNameStudent = "student";
    public static final String colStudName = "student_name";
    public static final String colStudNo = "student_no";
    public static final String colSubjName = "subject_name";
    public static final String colSubCode = "subject_code";
    public static final String colSubjHour = "subject_hour";
    public static final String colSubGrade = "subject_grade";
    public static final String colSubjId = "subject_id";

    public static final String strCrtTblStudent = "CREATE TABLE "+ tblNameStudent +
            " ("+ colStudNo + " TEXT PRIMARY KEY, "+ colStudName +" TEXT) ";
    public static final String strCrtTblSubject = "CREATE TABLE "+ tblNameSubject +
            " ("+ colSubjId + " INTEGER PRIMARY KEY, "+  colStudNo + " TEXT, "+ colSubCode +" TEXT, " +
            colSubGrade+ " REAL, "+ colSubjName+" TEXT, " + colSubjHour+ " REAL)";
    public static final String strDropTblSubject = "DROP TABLE IF EXISTS " + tblNameSubject;
    public static final String strDropTblStudent = "DROP TABLE IF EXISTS " + tblNameStudent;

    public DBSubject(Context context){
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(strCrtTblStudent);
        sqLiteDatabase.execSQL(strCrtTblSubject);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        sqLiteDatabase.execSQL(strDropTblStudent);
        sqLiteDatabase.execSQL(strDropTblSubject);
        onCreate(sqLiteDatabase);
    }

    public float fnInsertSubject(Subject subject) {
        float retResult = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(colStudNo, subject.getStudentNo());
        values.put(colSubCode, subject.getSubjectCode());
        values.put(colSubjName, subject.getSubjectName());
        values.put(colSubjHour, subject.getCreditHour());
        values.put(colSubGrade, subject.getGrade());

        retResult = db.insert(tblNameSubject, null, values);
        return retResult;
    }

    public float fnInsertStudent(String studentName, String studentNo){
        float retResult = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(colStudNo, studentNo);
        values.put(colStudName, studentName);


        retResult = db.insert(tblNameStudent, null, values);
        return retResult;
    }

    public float fnEditSubject(Subject subject){
        float retResult;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(colSubCode, subject.getSubjectCode());
        values.put(colSubjName, subject.getSubjectName());
        values.put(colSubjHour, subject.getCreditHour());
        values.put(colSubGrade, subject.getGrade());

        retResult = db.update(tblNameSubject, values, colSubjId+" = ?", new String[]{subject.getSubjectId()});
        return retResult;
    }

    /*public ExpensesDBModel fnGetExpenses(String intExpId){

        String strSelQry = "Select * from " + tblNameExpense + " where "+ colExpId +" = " + intExpId;
        Cursor cursor = this.getReadableDatabase().rawQuery(strSelQry,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        return new ExpensesDBModel(
                cursor.getString(cursor.getColumnIndex(colExpName)),
                cursor.getDouble(cursor.getColumnIndex(colExpPrice)),
                cursor.getString(cursor.getColumnIndex(colExpDate)),
                cursor.getString(cursor.getColumnIndex(colExpTime)));

    }*/

    public List<Subject> fnGetSubjects(String studentNo){
        List<Subject> list = new ArrayList<Subject>();
        String strSelAll = "Select * from " + tblNameSubject + " where "+ colStudNo +" = '" + studentNo+"'";

        Cursor cursor = this.getReadableDatabase().rawQuery(strSelAll,null);
        if(cursor.moveToFirst()){
            do{
                //System.out.println(cursor.getDouble(cursor.getColumnIndex(colSubjHour))+ " "+ cursor.getDouble(cursor.getColumnIndex(colSubjHour)));
                Subject model =  new Subject(
                            cursor.getString(cursor.getColumnIndex(colSubCode)),
                            cursor.getString(cursor.getColumnIndex(colSubjName)),
                            cursor.getDouble(cursor.getColumnIndex(colSubjHour)),
                            cursor.getInt(cursor.getColumnIndex(colSubGrade)),
                            cursor.getString(cursor.getColumnIndex(colStudNo)),
                            cursor.getString(cursor.getColumnIndex(colSubjId)));
                list.add(model);
            }while(cursor.moveToNext());
        }
        return list;
    }
}

