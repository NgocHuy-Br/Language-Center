package com.example.language_center;

public class Student {
    private String code;
    private String name;
    private String level;
    private String className;

    public Student(String code, String name, String level, String className) {
        this.code = code;
        this.name = name;
        this.level = level;
        this.className = className;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return code + " - " + name + " - " + level + " - " + className;
    }
}
