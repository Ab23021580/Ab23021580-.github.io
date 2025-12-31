# 记忆笔记 (Memory Note App)

基于艾宾浩斯记忆曲线的 Android 笔记应用，帮助用户有效地复习和记忆笔记内容。

## 功能特性

### 1. 笔记管理
- ✅ 添加新笔记（标题 + 内容）
- ✅ 编辑现有笔记
- ✅ 删除笔记
- ✅ 查看笔记列表

### 2. 艾宾浩斯记忆曲线
- ✅ 基于创建时间自动计算复习时间点：
  - 第 1 次复习：1 天后
  - 第 2 次复习：2 天后
  - 第 3 次复习：4 天后
  - 第 4 次复习：7 天后
  - 第 5 次复习：15 天后
- ✅ 显示每个笔记的下次复习日期
- ✅ 标记笔记为"已复习"，自动更新到下一个复习阶段
- ✅ 显示需要今天复习的笔记列表

### 3. 本地存储
- ✅ 使用 Room 数据库存储笔记
- ✅ 存储笔记内容、创建时间、复习阶段、下次复习日期

### 4. UI 设计
- ✅ Material Design 风格
- ✅ 两个标签页：所有笔记 / 待复习
- ✅ 支持中文界面

## 技术栈

- **语言**: Kotlin
- **最低 SDK**: 24 (Android 7.0)
- **目标 SDK**: 34 (Android 14)
- **架构组件**:
  - Room Database (本地数据持久化)
  - ViewModel (UI 数据管理)
  - LiveData (响应式数据观察)
  - Coroutines (异步操作)
- **UI**: Material Design Components

## 项目结构

```
app/
├── src/main/
│   ├── java/com/example/noteapp/
│   │   ├── data/
│   │   │   ├── Note.kt              # 笔记数据实体
│   │   │   ├── NoteDao.kt           # 数据访问对象
│   │   │   └── NoteDatabase.kt      # Room 数据库
│   │   ├── ui/
│   │   │   ├── MainActivity.kt      # 主界面
│   │   │   ├── NoteAdapter.kt       # RecyclerView 适配器
│   │   │   └── AddEditNoteActivity.kt # 添加/编辑笔记界面
│   │   ├── viewmodel/
│   │   │   └── NoteViewModel.kt     # 视图模型
│   │   └── util/
│   │       └── MemoryCurve.kt       # 记忆曲线计算工具
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml
│   │   │   ├── activity_add_edit_note.xml
│   │   │   └── item_note.xml
│   │   ├── values/
│   │   │   ├── strings.xml
│   │   │   ├── colors.xml
│   │   │   └── themes.xml
│   │   └── xml/
│   └── AndroidManifest.xml
├── build.gradle.kts
└── proguard-rules.pro
```

## 如何使用

### 环境要求

- Android Studio Hedgehog (2023.1.1) 或更高版本
- JDK 8 或更高版本
- Android SDK 34

### 编译步骤

1. 克隆仓库：
```bash
git clone https://github.com/Ab23021580/Ab23021580-.github.io.git
cd Ab23021580-.github.io
```

2. 在 Android Studio 中打开项目

3. 等待 Gradle 同步完成

4. 点击 Run 按钮或使用快捷键 `Shift + F10` 运行应用

### 使用说明

1. **添加笔记**
   - 点击右下角的 "+" 按钮
   - 输入标题和内容
   - 点击"保存"

2. **查看笔记**
   - 在"所有笔记"标签页查看全部笔记
   - 在"待复习"标签页查看需要复习的笔记

3. **复习笔记**
   - 点击笔记项上的"已复习"按钮
   - 系统会自动更新到下一个复习阶段

4. **编辑笔记**
   - 点击笔记项
   - 修改内容后点击"保存"

5. **删除笔记**
   - 点击笔记项上的"删除"按钮
   - 确认删除操作

## 艾宾浩斯记忆曲线说明

艾宾浩斯记忆曲线（Ebbinghaus Forgetting Curve）揭示了人类大脑对新事物遗忘的规律。根据这个曲线，本应用设计了以下复习间隔：

- **第 1 次复习**: 1 天后
- **第 2 次复习**: 2 天后（距离第 1 次复习）
- **第 3 次复习**: 4 天后（距离第 2 次复习）
- **第 4 次复习**: 7 天后（距离第 3 次复习）
- **第 5 次复习**: 15 天后（距离第 4 次复习）

完成 5 次复习后，笔记将被标记为"已完成所有复习"，不再需要定期复习。

## 依赖库

```kotlin
// AndroidX Core
implementation("androidx.core:core-ktx:1.12.0")
implementation("androidx.appcompat:appcompat:1.6.1")
implementation("com.google.android.material:material:1.11.0")
implementation("androidx.constraintlayout:constraintlayout:2.1.4")

// Room Database
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
ksp("androidx.room:room-compiler:2.6.1")

// Lifecycle Components
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

// Activity and Fragment KTX
implementation("androidx.activity:activity-ktx:1.8.2")
implementation("androidx.fragment:fragment-ktx:1.6.2")
```

## 许可证

此项目为开源项目，仅供学习和研究使用。

## 贡献

欢迎提交 Issue 和 Pull Request！

## 作者

Ab23021580

## 更新日志

### v1.0.0 (2024)
- 初始版本发布
- 实现基本的笔记管理功能
- 集成艾宾浩斯记忆曲线
- Material Design UI
