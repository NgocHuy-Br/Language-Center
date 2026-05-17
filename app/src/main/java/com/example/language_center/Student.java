package com.example.language_center;

public class Student {
    private String code;
    private String name;
    private String level;
    private String className;

    // Định nghĩa Enum ngay trong lớp Student cho gọn
    public enum Level {
        A1, A2, B1, B2, C1, C2;

        // Hàm kiểm tra một chuỗi có khớp với Enum không
        public static boolean isValid(String val) {
            if (val == null) return false;
            for (Level l : Level.values()) {
                if (l.name().equalsIgnoreCase(val.trim())) {
                    return true;
                }
            }
            return false;
        }

        // Trả về mảng chuỗi tên các Level để hiển thị
        public static String[] getStrings() {
            return new String[]{"A1", "A2", "B1", "B2", "C1", "C2"};
        }
    }

    public Student(String code, String name, String level, String className) {
        this.code = code;
        this.name = name;
        this.level = level;
        this.className = className;
    }

    // Getter và Setter
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    @Override
    public String toString() {
        return code + " - " + name + " - " + level + " - " + className;
    }
}
