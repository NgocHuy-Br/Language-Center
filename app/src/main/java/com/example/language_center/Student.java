package com.example.language_center;

public class Student {
    private String code;
    private String name;
    private int yob;
    private String gender;
    private double gpa;

    public Student(String code, String name, int yob, String gender, double gpa) {
        this.code = code;
        this.name = name;
        this.yob = yob;
        this.gender = gender;
        this.gpa = gpa;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYob() {
        return yob;
    }

    public void setYob(int yob) {
        this.yob = yob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return code + " - " + name + " - " + yob;
    }
}
