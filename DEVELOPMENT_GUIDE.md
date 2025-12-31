# Android 记忆笔记应用 - 开发和使用指南

## 项目概述

这是一个基于艾宾浩斯记忆曲线的 Android 笔记应用，使用 Kotlin 开发，采用 Material Design 设计规范。

## 开发环境要求

### 必需软件
- **Android Studio**: Hedgehog (2023.1.1) 或更高版本
- **JDK**: 8 或更高版本（推荐 JDK 11 或 17）
- **Android SDK**: API 24 - 34
- **Gradle**: 8.2（通过 Gradle Wrapper 自动下载）

### 系统要求
- **操作系统**: Windows 10/11, macOS 10.14+, 或 Linux
- **内存**: 至少 8GB RAM（推荐 16GB）
- **存储**: 至少 10GB 可用空间

## 项目设置

### 1. 克隆项目

```bash
git clone https://github.com/Ab23021580/Ab23021580-.github.io.git
cd Ab23021580-.github.io
```

### 2. 在 Android Studio 中打开

1. 启动 Android Studio
2. 选择 "Open an Existing Project"
3. 导航到克隆的项目目录
4. 点击 "OK"

### 3. Gradle 同步

Android Studio 会自动开始 Gradle 同步。如果没有：
1. 点击 File → Sync Project with Gradle Files
2. 等待同步完成（首次可能需要几分钟）

### 4. 配置 Android SDK

如果提示 SDK 缺失：
1. 点击 Tools → SDK Manager
2. 确保安装了 API 24-34
3. 点击 "Apply" 安装缺失的组件

## 运行应用

### 使用 Android Studio

1. **连接设备或启动模拟器**
   - 物理设备：启用 USB 调试，通过 USB 连接
   - 虚拟设备：Tools → Device Manager → Create Device

2. **运行应用**
   - 点击工具栏的绿色 Run 按钮 (▶)
   - 或使用快捷键 `Shift + F10` (Windows/Linux) 或 `Control + R` (macOS)

3. **选择设备**
   - 在弹出的对话框中选择目标设备
   - 点击 "OK"

### 使用命令行

```bash
# 编译项目
./gradlew assembleDebug

# 安装到连接的设备
./gradlew installDebug

# 编译并运行
./gradlew build
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 项目架构

### MVVM 架构

```
UI Layer (Activity/Fragment)
    ↓
ViewModel (NoteViewModel)
    ↓
Repository/DAO (NoteDao)
    ↓
Database (Room Database)
```

### 核心组件

#### 1. 数据层 (Data Layer)

**Note.kt** - 笔记实体类
```kotlin
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val createdAt: Long,
    val reviewStage: Int,
    val nextReviewDate: Long
)
```

**NoteDao.kt** - 数据访问对象
- `getAllNotes()`: 获取所有笔记
- `getNotesNeedingReview()`: 获取需要复习的笔记
- `insert()`, `update()`, `delete()`: CRUD 操作

**NoteDatabase.kt** - Room 数据库
- 单例模式
- 数据库版本管理

#### 2. 工具层 (Utility Layer)

**MemoryCurve.kt** - 记忆曲线计算
- `calculateNextReviewDate()`: 计算下次复习时间
- `getNextReviewStage()`: 获取下一复习阶段
- `formatDate()`: 格式化日期显示

复习间隔：
- 第 1 次：1 天
- 第 2 次：2 天
- 第 3 次：4 天
- 第 4 次：7 天
- 第 5 次：15 天

#### 3. ViewModel 层

**NoteViewModel.kt** - 视图模型
- 管理 UI 数据
- 处理业务逻辑
- 在配置更改时保持数据

#### 4. UI 层

**MainActivity.kt** - 主界面
- 显示笔记列表
- 两个标签页：所有笔记 / 待复习
- FAB 按钮添加新笔记

**AddEditNoteActivity.kt** - 添加/编辑界面
- 创建新笔记
- 编辑现有笔记

**NoteAdapter.kt** - RecyclerView 适配器
- 显示笔记列表
- 处理复习和删除操作

## 功能使用指南

### 1. 添加笔记

1. 打开应用
2. 点击右下角的 "+" 按钮
3. 输入标题和内容
4. 点击"保存"按钮

笔记创建后，系统会自动：
- 设置创建时间
- 将复习阶段设为 0
- 计算第一次复习时间（1天后）

### 2. 查看笔记

**所有笔记标签页**
- 显示所有创建的笔记
- 按创建时间倒序排列

**待复习标签页**
- 仅显示需要复习的笔记
- 按复习日期升序排列

### 3. 复习笔记

1. 在笔记列表中找到需要复习的笔记
2. 阅读笔记内容
3. 点击"已复习"按钮

系统会：
- 更新复习阶段
- 计算下次复习时间
- 显示新的复习状态

### 4. 编辑笔记

1. 点击笔记项
2. 修改标题或内容
3. 点击"保存"按钮

注意：编辑不会影响复习进度

### 5. 删除笔记

1. 点击笔记项上的"删除"按钮
2. 在确认对话框中点击"删除"

## 数据持久化

### Room 数据库

- **位置**: `/data/data/com.example.noteapp/databases/note_database`
- **备份**: 支持 Android 备份机制
- **迁移**: 使用 `fallbackToDestructiveMigration()`

### 数据表结构

```sql
CREATE TABLE notes (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    createdAt INTEGER NOT NULL,
    reviewStage INTEGER NOT NULL,
    nextReviewDate INTEGER NOT NULL
);
```

## 自定义和扩展

### 修改复习间隔

编辑 `MemoryCurve.kt`:

```kotlin
private val REVIEW_INTERVALS = intArrayOf(1, 2, 4, 7, 15)
// 改为你想要的间隔，例如：
private val REVIEW_INTERVALS = intArrayOf(1, 3, 7, 14, 30)
```

### 修改主题颜色

编辑 `app/src/main/res/values/colors.xml`:

```xml
<color name="purple_500">#FF6200EE</color>  <!-- 主色 -->
<color name="purple_700">#FF3700B3</color>  <!-- 主色深色变体 -->
<color name="teal_200">#FF03DAC5</color>    <!-- 次要色 -->
```

### 添加新功能

1. **添加笔记分类**
   - 在 `Note.kt` 添加 `category` 字段
   - 更新数据库版本
   - 在 UI 中添加分类选择器

2. **添加搜索功能**
   - 在 `NoteDao.kt` 添加搜索方法
   - 在 `MainActivity.kt` 添加搜索栏
   - 使用 `LIKE` 查询实现搜索

3. **添加笔记标签**
   - 创建 `Tag` 实体
   - 使用多对多关系
   - 添加标签管理界面

## 常见问题

### 构建错误

**问题**: Gradle sync failed
**解决**: 
1. 清理项目：Build → Clean Project
2. 重新构建：Build → Rebuild Project
3. 检查网络连接（Gradle 需要下载依赖）

**问题**: SDK not found
**解决**:
1. 打开 SDK Manager
2. 安装缺失的 SDK 版本
3. 更新 local.properties 文件

### 运行时错误

**问题**: 应用崩溃
**解决**:
1. 查看 Logcat 错误信息
2. 检查数据库是否正确初始化
3. 确保所有权限已授予

### 性能问题

**问题**: 列表滚动卡顿
**解决**:
1. 使用 DiffUtil（已实现）
2. 优化 ViewHolder 绑定
3. 使用分页加载（如果数据量大）

## 测试

### 单元测试

创建测试文件在 `app/src/test/java/`:

```kotlin
class MemoryCurveTest {
    @Test
    fun testCalculateNextReviewDate() {
        val createdAt = System.currentTimeMillis()
        val nextReview = MemoryCurve.calculateNextReviewDate(createdAt, 0)
        // 断言逻辑
    }
}
```

### UI 测试

创建测试文件在 `app/src/androidTest/java/`:

```kotlin
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Test
    fun testAddNote() {
        // Espresso UI 测试
    }
}
```

## 发布

### 生成签名密钥

```bash
keytool -genkey -v -keystore my-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias my-alias
```

### 配置签名

在 `app/build.gradle.kts` 添加：

```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file("my-release-key.jks")
            storePassword = "password"
            keyAlias = "my-alias"
            keyPassword = "password"
        }
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

### 生成 Release APK

```bash
./gradlew assembleRelease
```

APK 位置: `app/build/outputs/apk/release/app-release.apk`

## 贡献指南

1. Fork 项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 许可证

本项目采用 MIT 许可证 - 详见 LICENSE 文件

## 联系方式

- GitHub: [@Ab23021580](https://github.com/Ab23021580)
- 项目链接: [https://github.com/Ab23021580/Ab23021580-.github.io](https://github.com/Ab23021580/Ab23021580-.github.io)

## 致谢

- [Android Jetpack](https://developer.android.com/jetpack)
- [Material Design](https://material.io/design)
- [Room Persistence Library](https://developer.android.com/training/data-storage/room)
- 艾宾浩斯记忆曲线理论
