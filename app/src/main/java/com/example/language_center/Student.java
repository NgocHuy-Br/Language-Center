package com.example.language_center;

public class Student {
    private String code;
    private String name;
    private int yob;

    public Student(String code, String name, int yob) {
        this.code = code;
        this.name = name;
        this.yob = yob;
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

    @Override
    public String toString() {
        return code + " - " + name + " - " + yob;
    }
}
