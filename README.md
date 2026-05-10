# Hệ Thống Quản Lý Trung Tâm Ngoại Ngữ

## Mô tả dự án
Ứng dụng Android quản lý trung tâm ngoại ngữ với các chức năng quản lý học viên và giáo viên.

## Tính năng chính

### 1. Đăng nhập
- Đăng nhập bằng tài khoản được lưu trong SQLite
- Tài khoản mặc định: **admin** / **123456**
- Xác thực từ database trước khi vào hệ thống

### 2. Màn hình quản lý (ManagementActivity)
- **RadioButton** để chuyển đổi giữa danh sách học viên và giáo viên
- **ListView** hiển thị danh sách theo lựa chọn
- **Nút Thêm**: Thêm học viên/giáo viên mới
- **Nút Thống kê**: Chuyển đến màn hình thống kê
- **Click vào item**: Sửa thông tin
- **Long click vào item**: Xóa với xác nhận

### 3. Thêm/Sửa học viên (AddEditStudentActivity)
Quản lý thông tin học viên với các trường:
- **Mã SV**: Mã sinh viên (không thể sửa khi edit)
- **Họ tên**: Tên đầy đủ của học viên
- **Trình độ**: Level (Basic, Intermediate, Advanced)
- **Lớp**: Tên lớp học

### 4. Thêm/Sửa giáo viên (AddEditTeacherActivity)
Quản lý thông tin giáo viên với các trường:
- **Mã GV**: Mã giáo viên (không thể sửa khi edit)
- **Họ tên**: Tên đầy đủ của giáo viên
- **Ngôn ngữ**: Ngôn ngữ giảng dạy

### 5. Thống kê học viên (StatisticsActivity)
- **RadioButton Level**: Chọn trình độ (Basic/Intermediate/Advanced)
- **Spinner Class**: Chọn lớp học theo level đã chọn
- **ListView**: Hiển thị danh sách học viên của lớp đó

## Cấu trúc Database (SQLite)

### Bảng User
- `id`: INTEGER PRIMARY KEY AUTOINCREMENT
- `username`: TEXT UNIQUE NOT NULL
- `password`: TEXT NOT NULL

### Bảng Student
- `code`: TEXT PRIMARY KEY
- `name`: TEXT NOT NULL
- `level`: TEXT NOT NULL (Basic/Intermediate/Advanced)
- `class_name`: TEXT NOT NULL

### Bảng Teacher
- `code`: TEXT PRIMARY KEY
- `name`: TEXT NOT NULL
- `language`: TEXT NOT NULL

## Cấu trúc code

```
app/src/main/java/com/example/language_center/
├── DatabaseHelper.java          # SQLite database helper
├── MainActivity.java            # Màn hình đăng nhập
├── ManagementActivity.java      # Màn hình quản lý chính
├── AddEditStudentActivity.java  # Thêm/sửa học viên
├── AddEditTeacherActivity.java  # Thêm/sửa giáo viên
├── StatisticsActivity.java      # Thống kê học viên
├── Student.java                 # Model học viên
├── Teacher.java                 # Model giáo viên
├── StudentAdapter.java          # Adapter cho ListView học viên
└── TeacherAdapter.java          # Adapter cho ListView giáo viên
```

## Hướng dẫn sử dụng

1. **Đăng nhập**: 
   - Mở ứng dụng
   - Nhập username: `admin`, password: `123456`
   - Click "Đăng nhập"

2. **Quản lý học viên**:
   - Chọn RadioButton "Học viên"
   - Click "Thêm" để thêm học viên mới
   - Click vào học viên để sửa
   - Long click để xóa

3. **Quản lý giáo viên**:
   - Chọn RadioButton "Giáo viên"
   - Click "Thêm" để thêm giáo viên mới
   - Click vào giáo viên để sửa
   - Long click để xóa

4. **Xem thống kê**:
   - Click nút "Thống kê"
   - Chọn Level (Basic/Intermediate/Advanced)
   - Chọn lớp từ Spinner
   - Xem danh sách học viên

## Patterns được áp dụng

- **Model-View**: Student, Teacher models với full getter/setter
- **Custom Adapter**: StudentAdapter, TeacherAdapter extends ArrayAdapter
- **SQLite Database**: DatabaseHelper với CRUD operations
- **Intent Navigation**: Truyền data giữa các activities
- **Event Handling**: OnClickListener, OnItemClickListener, OnItemLongClickListener
- **AlertDialog**: Xác nhận trước khi xóa

## Lưu ý

- Mã học viên/giáo viên là duy nhất (PRIMARY KEY)
- Khi sửa không thể thay đổi mã
- Database tự động tạo tài khoản admin khi lần đầu chạy
- Sử dụng Vietnamese labels trong UI
