# 🏫 Hệ Thống Quản Lý Trung Tâm Ngoại Ngữ (LanguageCenter)

## 📝 Mô tả dự án
Ứng dụng Android chuyên nghiệp dành cho việc quản lý thông tin Học viên và Giáo viên trong trung tâm ngoại ngữ. Dự án sử dụng cơ sở dữ liệu SQLite để lưu trữ bền vững, giao diện được tối ưu hóa theo phong cách **Material Design** với kỹ thuật **ConstraintLayout bias** và cấu trúc code tuân thủ chuẩn giáo trình.

---

## 🚀 Tính năng chính

### 1. Hệ thống Đăng nhập
*   **Xác thực:** Kiểm tra tài khoản từ cơ sở dữ liệu SQLite.
*   **Tài khoản quản trị mặc định:**
    *   **Username:** `caongochuy`
    *   **Password:** `123456`
*   **Giao diện:** Cân đối với Logo PTIT, tiêu đề và form nhập liệu được căn chỉnh bằng `bias` chuyên nghiệp.

### 2. Quản lý Học viên (StudentManageActivity)
*   **Danh sách:** Hiển thị chi tiết: Mã số, Họ tên, Trình độ, Lớp học.
*   **Bộ lọc thông minh:** 
    *   Lọc theo **Trình độ** qua Spinner (Dữ liệu trình độ được lấy tự động từ DB).
    *   Tìm kiếm nhanh theo **Họ tên** hoặc **Lớp học** ngay khi đang gõ.
*   **Thao tác:** Nút **Sửa** (xanh) và **Xóa** (đỏ) trực tiếp trên từng dòng kèm biểu tượng minh họa.

### 3. Quản lý Giáo viên (TeacherManageActivity)
*   **Danh sách:** Hiển thị: Mã số, Họ tên, Ngôn ngữ giảng dạy.
*   **Thao tác:** Hỗ trợ đầy đủ các chức năng Thêm, Sửa, Xóa.
*   **Điều hướng:** Chuyển đổi nhanh giữa trang Học viên và Giáo viên qua thanh công cụ phía dưới.

### 4. Form Nhập liệu (Add/Edit)
*   **Tiêu đề động:** Tự động hiển thị "Thêm..." hoặc "Sửa..." dựa trên tác vụ.
*   **Kiểm soát mã số:** Cho phép nhập/sửa mã số nhưng hệ thống tự động kiểm tra tính duy nhất (trùng lặp) trong Database.
*   **Tiện dụng:** Các nút **Lưu** và **Hủy** được đặt sát cạnh dưới màn hình.

---

## 💾 Cấu trúc Cơ sở dữ liệu (SQLite)

Tên Database: `LanguageCenter.db` | Phiên bản: `2`

### 1. Bảng `User` (Lưu tài khoản đăng nhập)
| Trường | Kiểu dữ liệu | Thuộc tính | Mô tả |
| :--- | :--- | :--- | :--- |
| `id` | INTEGER | PRIMARY KEY AUTOINCREMENT | Mã định danh tự tăng |
| `username` | TEXT | UNIQUE, NOT NULL | Tên đăng nhập |
| `password` | TEXT | NOT NULL | Mật khẩu đăng nhập |

### 2. Bảng `Student` (Lưu thông tin học viên)
| Trường | Kiểu dữ liệu | Thuộc tính | Mô tả |
| :--- | :--- | :--- | :--- |
| `code` | TEXT | PRIMARY KEY | **Mã số** học viên (duy nhất) |
| `name` | TEXT | NOT NULL | **Họ tên** học viên |
| `level` | TEXT | NOT NULL | **Trình độ** (Basic, Intermediate, v.v.) |
| `class_name` | TEXT | NOT NULL | **Lớp học** |

### 3. Bảng `Teacher` (Lưu thông tin giáo viên)
| Trường | Kiểu dữ liệu | Thuộc tính | Mô tả |
| :--- | :--- | :--- | :--- |
| `code` | TEXT | PRIMARY KEY | **Mã số** giáo viên (duy nhất) |
| `name` | TEXT | NOT NULL | **Họ tên** giáo viên |
| `language` | TEXT | NOT NULL | **Ngôn ngữ** giảng dạy |

---

## 🛠 Thông số Kỹ thuật & Style Code

*   **Layout:** Sử dụng `ConstraintLayout` làm chủ đạo, căn chỉnh bằng `layout_constraintHorizontal_bias` và `layout_constraintVertical_bias` để đảm bảo độ chính xác vị trí.
*   **UI Components:** 
    *   **Buttons:** Kiểu `OutlinedButton` hiện đại.
    *   **Icons:** Sử dụng Vector Assets (ic_add, ic_edit, ic_delete, ic_logout, v.v.).
*   **Java Style:**
    *   Mapping View có ép kiểu tường minh: `edAccount = (EditText) findViewById(R.id.editTextAccount);`.
    *   Xử lý sự kiện bằng **Anonymous Inner Class**: `btn.setOnClickListener(new View.OnClickListener() {...});`.
    *   Cấu trúc phân đoạn rõ ràng: `//Mapping`, `//Event handle`, `//Start Code here`.
*   **Navigation:** Sử dụng `Intent` với `intent.setClass()` và truyền dữ liệu qua `putExtra`.

---

## 📁 Cấu trúc thư mục mã nguồn
```text
java/com/example/language_center/
├── DatabaseHelper.java      # Quản lý SQLite (Tạo bảng, CRUD, Check trùng mã)
├── MainActivity.java        # Xử lý Đăng nhập & Edge-to-Edge
├── StudentManageActivity    # Danh sách học viên & Bộ lọc tích hợp
├── TeacherManageActivity    # Danh sách giáo viên & Điều hướng
├── AddEditStudentActivity   # Form nhập liệu Học viên
├── AddEditTeacherActivity   # Form nhập liệu Giáo viên
├── Student.java             # Lớp đối tượng Học viên
├── Teacher.java             # Lớp đối tượng Giáo viên
├── StudentAdapter.java      # Tùy chỉnh hiển thị dòng Học viên
└── TeacherAdapter.java      # Tùy chỉnh hiển thị dòng Giáo viên
```

## ⚠️ Lưu ý Cập nhật
Nếu bạn thực hiện thay đổi cấu trúc bảng, hãy tăng `DATABASE_VERSION` trong `DatabaseHelper.java` và thực hiện **Build -> Clean Project** để hệ thống cập nhật lại toàn bộ dữ liệu.
