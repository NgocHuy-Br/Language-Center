# Hệ Thống Quản Lý Trung Tâm Ngoại Ngữ (LanguageCenter)

## 📝 Mô tả dự án
Ứng dụng Android quản lý Học viên và Giáo viên cho trung tâm ngoại ngữ. Giao diện được thiết kế hiện đại theo phong cách **Material Design (Outlined)** và cấu trúc code tuân thủ yêu cầu giáo trình của thầy giáo.

## 🚀 Tính năng chính

### 1. Đăng nhập Hệ thống
- Xác thực người dùng qua SQLite.
- Tài khoản quản trị mới: 
  - **Username:** `caongochuy`
  - **Password:** `123456`
- Giao diện đăng nhập sử dụng **ConstraintLayout bias** cân đối với Logo PTIT và Tiêu đề lớn.

### 2. Quản lý Học viên (StudentManageActivity)
- **Danh sách thông minh:** Hiển thị Mã số, Họ tên, Trình độ và Lớp học.
- **Bộ lọc đa năng:**
  - Lọc theo **Trình độ** qua Spinner (tự động lấy dữ liệu từ DB).
  - Tìm kiếm thời gian thực theo **Tên** hoặc **Lớp học**.
- **Thêm/Sửa/Xóa:** Có nút bấm riêng biệt kèm icon Material cho từng dòng.
- **Linh hoạt Mã số:** Cho phép sửa Mã số và tự động kiểm tra trùng lặp trong Database.

### 3. Quản lý Giáo viên (TeacherManageActivity)
- Hiển thị danh sách: Mã số, Họ tên và Ngôn ngữ giảng dạy.
- Thao tác nhanh chóng qua các nút bấm Outlined có biểu tượng minh họa.
- Chuyển đổi qua lại dễ dàng giữa trang Học viên và Giáo viên ở thanh điều hướng dưới cùng.

### 4. Thêm/Sửa thông tin (AddEdit)
- Tiêu đề động tự đổi theo tác vụ (ví dụ: "Thêm Học Viên" hoặc "Sửa Giáo Viên").
- Các nút hành động **Lưu** và **Hủy** được đặt ở **dưới cùng màn hình** (Bottom layout) cực kỳ tiện lợi.

## 🛠 Thông số Kỹ thuật

- **Bố cục (Layout):** 100% `ConstraintLayout` với kỹ thuật `bias` để định vị vị trí chuẩn xác.
- **Mã nguồn (Java):** 
  - Mapping View có ép kiểu tường minh: `(Button) findViewById(...)`.
  - Xử lý sự kiện bằng Anonymous Inner Class truyền thống.
  - Phân đoạn code rõ ràng với: `//Mapping`, `//Event handle`, `//Start Code here`.
- **Database:** SQLiteOpenHelper quản lý các bảng `User`, `Student`, `Teacher`.

## 📁 Cấu trúc tệp tin chính
```text
app/src/main/
├── java/com/example/language_center/
│   ├── DatabaseHelper.java      # Xử lý SQLite (CRUD & Check mã)
│   ├── MainActivity.java        # Màn hình Đăng nhập (Style mẫu)
│   ├── StudentManageActivity    # Quản lý Học viên & Bộ lọc
│   ├── TeacherManageActivity    # Quản lý Giáo viên
│   ├── AddEditStudentActivity   # Form Học viên (Bottom buttons)
│   └── AddEditTeacherActivity   # Form Giáo viên (Bottom buttons)
└── res/layout/
    ├── activity_main.xml        # Giao diện Login (bias style)
    ├── student_item_layout.xml  # Custom row Học viên (Edit/Delete buttons)
    └── teacher_item_layout.xml  # Custom row Giáo viên (Edit/Delete buttons)
```

## ⚠️ Hướng dẫn cập nhật
Để đảm bảo ứng dụng hiển thị đúng sau khi xóa các tệp cũ, bạn nên thực hiện:
1. Vào menu **Build** -> **Clean Project**.
2. Chọn **Build** -> **Rebuild Project**.
3. Chạy ứng dụng và đăng nhập bằng tài khoản `caongochuy`.
