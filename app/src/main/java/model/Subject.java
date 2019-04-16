package model;

import java.io.Serializable;

public class Subject implements Serializable {
    String subjectCode;
    String studentNo;
    String subjectName;
    String subjectId;
    double creditHour;
    int grade;

    public Subject(String subjectCode, String subjectName, double creditHour, int grade, String studentNo, String subjectId){
        this.creditHour = creditHour;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.grade = grade;
        this.studentNo = studentNo;
        this.subjectId = subjectId;
    }

    public Subject(String subjectCode, String subjectName, double creditHour, int grade, String studentNo){
        this.creditHour = creditHour;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.studentNo = studentNo;
        this.grade = grade;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public double getCreditHour() {
        return creditHour;
    }

    public String getSubjectName() {
        return subjectName;
    }
    public int getGrade() {
        return grade;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setCreditHour(double creditHour) {
        this.creditHour = creditHour;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }


}
