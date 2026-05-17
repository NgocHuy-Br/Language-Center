package com.example.language_center;

public class Teacher {
    private String code;     // Mã số giáo viên
    private String name;     // Họ tên
    private String language; // Ngôn ngữ giảng dạy

    public Teacher(String code, String name, String language) {
        this.code = code;
        this.name = name;
        this.language = language;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
}
