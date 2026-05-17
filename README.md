# 🏫 Hệ Thống Quản Lý Trung Tâm Ngoại Ngữ (Language Center)

## 📖 Giới thiệu
Dự án **LanguageCenter** là một ứng dụng Android hoàn chỉnh giúp quản lý quy trình vận hành cơ bản của một trung tâm ngoại ngữ, bao gồm quản lý nhân sự (Giáo viên) và khách hàng (Học viên). Ứng dụng được xây dựng theo kiến trúc **MVC (Model-View-Controller)** đơn giản, tuân thủ phong cách lập trình và giao diện theo tiêu chuẩn giáo trình Android hiện đại.

---

## 🚀 Tính năng nổi bật

### 1. Hệ thống Đăng nhập (Security)
*   **Xác thực:** Kiểm tra tài khoản từ bảng `User` trong SQLite.
*   **Thông tin đăng nhập mặc định:**
    *   **Tài khoản:** `caongochuy`
    *   **Mật khẩu:** `123456`
*   **Giao diện:** Sử dụng `ConstraintLayout` với kỹ thuật `bias` để căn chỉnh Logo PTIT và Form nhập liệu cân đối.

### 2. Quản lý Học viên (Student Management)
*   **Tích hợp Thống kê:** Ngay tại màn hình chính của học viên, có thể lọc danh sách học viên theo trình độ thông qua Spinner.
*   **Chuẩn trình độ (Enum):** Trình độ học viên được chuẩn hóa theo 6 cấp độ chuẩn Châu Âu: `A1, A2, B1, B2, C1, C2`.
*   **Tìm kiếm thời gian thực:** Lọc danh sách ngay lập tức khi người dùng gõ tên học viên hoặc tên lớp học vào ô tìm kiếm.
*   **Hiển thị:** Danh sách bao gồm các trường thông tin: Mã số, Họ tên, Trình độ, Lớp học.

### 3. Quản lý Giáo viên (Teacher Management)
*   **Quản lý chuyên môn:** Lưu trữ thông tin giáo viên bao gồm Mã số, Họ tên và ngôn ngữ giảng dạy chính.
*   **Điều hướng nhanh:** Hệ thống nút bấm chuyển trang "Trang Học viên" và "Trang Giáo viên" nằm ở dưới cùng màn hình giúp di chuyển linh hoạt.

### 4. Biểu mẫu Thêm/Sửa (Linh hoạt)
*   **Tiêu đề động:** Tự động hiển thị "Thêm..." hoặc "Sửa..." dựa trên tác vụ người dùng chọn.
*   **Mã số linh hoạt:** Cho phép sửa mã số, hệ thống sẽ tự động kiểm tra trùng lặp trong Database và cảnh báo nếu mã đã tồn tại.
*   **Kiểm tra dữ liệu:** Ô nhập trình độ có gợi ý mờ (Hint) và sẽ báo lỗi nếu người dùng nhập sai 6 cấp độ chuẩn (A1-C2).

---

## 💾 Thiết kế Cơ sở dữ liệu (SQLite)

**Database Name:** `LanguageCenter.db` | **Version:** `2`

### 1. Bảng `User`
| Cột | Kiểu | Mô tả |
| :--- | :--- | :--- |
| `username` | TEXT | Tên đăng nhập (Khóa chính) |
| `password` | TEXT | Mật khẩu |

### 2. Bảng `Student`
| Cột | Kiểu | Mô tả |
| :--- | :--- | :--- |
| `code` | TEXT | Mã số học viên (Khóa chính) |
| `name` | TEXT | Họ và tên |
| `level` | TEXT | Trình độ (A1, A2, B1, B2, C1, C2) |
| `class_name` | TEXT | Tên lớp học |

### 3. Bảng `Teacher`
| Cột | Kiểu | Mô tả |
| :--- | :--- | :--- |
| `code` | TEXT | Mã số giáo viên (Khóa chính) |
| `name` | TEXT | Họ và tên |
| `language` | TEXT | Ngôn ngữ giảng dạy |

---

## 📂 Phân tích cấu trúc thư mục & Chức năng file

### 🔹 Thư mục Java (`/java/com/example/language_center/`)
*   **`MainActivity.java`**: Xử lý logic đăng nhập và thiết lập giao diện tràn viền.
*   **`StudentManageActivity.java`**: Màn hình quản lý học viên, chứa Spinner lọc và SearchBar.
*   **`TeacherManageActivity.java`**: Màn hình quản lý giáo viên.
*   **`AddEditStudentActivity.java`**: Form nhập liệu cho học viên (kiểm tra chuẩn Enum Level).
*   **`AddEditTeacherActivity.java`**: Form nhập liệu cho giáo viên.
*   **`DatabaseHelper.java`**: Quản lý SQLite, thực hiện các lệnh CRUD (Thêm, Đọc, Sửa, Xóa).
*   **`Student.java`**: Lớp đối tượng Học viên (chứa `enum Level` nội bộ).
*   **`Teacher.java`**: Lớp đối tượng Giáo viên.
*   **`StudentAdapter.java` & `TeacherAdapter.java`**: Custom Adapter để hiển thị danh sách với nút Sửa/Xóa.

### 🔹 Thư mục Giao diện (`/res/layout/`)
*   **`activity_main.xml`**: Layout Đăng nhập (ConstraintLayout bias style).
*   **`activity_student_manage.xml`**: Layout quản lý với bộ lọc tích hợp.
*   **`student_item_layout.xml`**: Thiết kế dòng học viên với 2 nút hành động riêng biệt.

---

## 🛠 Kỹ thuật và Style Code áp dụng
1.  **ConstraintLayout Bias:** Định vị trí thành phần theo tỉ lệ %, giúp giao diện cân đối trên mọi màn hình.
2.  **Explicit Casting:** Mapping View theo style truyền thống: `(Button) findViewById(...)`.
3.  **Anonymous Inner Class:** Xử lý sự kiện OnClick chuẩn giáo trình.
4.  **Vector Icons:** Sử dụng chuẩn XML Material Icons (ic_add, ic_edit, ic_delete...) giúp app nhẹ và nét.
5.  **Outlined Buttons:** Sử dụng kiểu nút hiện đại có viền và icon sát chữ (`iconGravity="textStart"`).

---
*Dự án thực hiện theo yêu cầu quản lý trung tâm ngoại ngữ chuẩn hóa.*
