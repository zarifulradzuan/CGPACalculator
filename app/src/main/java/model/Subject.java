package model;

public class Subject {
    String subjectCode;
    String studentNo;
    String subjectName;
    double creditHour;
    double grade;

    public Subject(String subjectCode, String subjectName, double creditHour, double grade, String studentNo){
        this.creditHour = creditHour;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.grade = grade;
        this.studentNo = studentNo;
    }

    public Subject(String subjectCode, String subjectName, double creditHour, double grade){
        this.creditHour = creditHour;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
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

    public double getGrade() {
        return grade;
    }
}
