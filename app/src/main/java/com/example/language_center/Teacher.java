package com.example.language_center;

public class Teacher {
    private String code;
    private String name;
    private String language;

    public Teacher(String code, String name, String language) {
        this.code = code;
        this.name = name;
        this.language = language;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return code + " - " + name + " - " + language;
    }
}
