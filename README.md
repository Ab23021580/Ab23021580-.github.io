# 记忆笔记 Android 应用

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://www.android.com/)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org/)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg)](https://android-arsenal.com/api?level=24)

基于艾宾浩斯记忆曲线的 Android 笔记应用，帮助用户有效地复习和记忆笔记内容。

## ✨ 功能特性

- 📝 **笔记管理**: 创建、编辑、删除笔记
- 🧠 **记忆曲线**: 基于艾宾浩斯曲线自动安排复习计划
- 📅 **复习提醒**: 显示需要复习的笔记
- 💾 **本地存储**: 使用 Room 数据库持久化数据
- 🎨 **Material Design**: 现代化的 UI 设计
- 🌏 **中文支持**: 完整的中文界面

## 📱 截图预览

应用包含以下主要界面：
- 主界面：所有笔记列表和待复习列表
- 添加/编辑界面：创建和修改笔记
- 笔记卡片：显示标题、内容和复习状态

## 🚀 快速开始

### 环境要求

- Android Studio Hedgehog (2023.1.1) 或更高版本
- JDK 8+
- Android SDK API 24-34

### 安装步骤

1. **克隆项目**
   ```bash
   git clone https://github.com/Ab23021580/Ab23021580-.github.io.git
   cd Ab23021580-.github.io
   ```

2. **打开项目**
   - 启动 Android Studio
   - 选择 "Open an Existing Project"
   - 选择项目目录

3. **运行应用**
   - 连接 Android 设备或启动模拟器
   - 点击运行按钮 (▶) 或按 `Shift + F10`

## 📖 文档

- [项目说明](README_APP.md) - 详细功能说明和技术栈
- [开发指南](DEVELOPMENT_GUIDE.md) - 开发和使用指南
- [项目总结](PROJECT_SUMMARY.md) - 完整实现总结

## 🧠 艾宾浩斯记忆曲线

本应用基于艾宾浩斯记忆曲线设计复习计划：

| 复习次数 | 间隔时间 | 说明 |
|---------|---------|------|
| 第 1 次 | 1 天后 | 首次复习 |
| 第 2 次 | 2 天后 | 第 1 次复习后 2 天 |
| 第 3 次 | 4 天后 | 第 2 次复习后 4 天 |
| 第 4 次 | 7 天后 | 第 3 次复习后 7 天 |
| 第 5 次 | 15 天后 | 第 4 次复习后 15 天 |

完成 5 次复习后，知识已经巩固到长期记忆中。

## 🏗️ 技术架构

### 架构模式
- **MVVM** (Model-View-ViewModel)

### 核心技术
- **Kotlin**: 主要开发语言
- **Room**: 本地数据库
- **LiveData**: 响应式数据
- **ViewModel**: UI 数据管理
- **Coroutines**: 异步处理
- **Material Components**: UI 组件

### 项目结构
```
app/
├── data/           # 数据层（Entity, DAO, Database）
├── ui/             # UI 层（Activity, Adapter）
├── viewmodel/      # ViewModel 层
└── util/           # 工具类（记忆曲线计算）
```

## 🎯 核心功能实现

### 1. 笔记管理
```kotlin
// 添加笔记
viewModel.insert(title, content)

// 更新笔记
viewModel.update(note)

// 删除笔记
viewModel.delete(note)
```

### 2. 复习管理
```kotlin
// 标记为已复习
viewModel.markAsReviewed(note)

// 获取待复习笔记
viewModel.getNotesNeedingReview()
```

### 3. 记忆曲线计算
```kotlin
// 计算下次复习时间
val nextReviewDate = MemoryCurve.calculateNextReviewDate(
    createdAt = note.createdAt,
    currentStage = note.reviewStage
)
```

## 📦 依赖库

```kotlin
// AndroidX Core
implementation("androidx.core:core-ktx:1.12.0")
implementation("androidx.appcompat:appcompat:1.6.1")

// Material Design
implementation("com.google.android.material:material:1.11.0")

// Room Database
implementation("androidx.room:room-runtime:2.6.1")
ksp("androidx.room:room-compiler:2.6.1")

// Lifecycle Components
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
```

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

## 📄 许可证

本项目仅供学习和研究使用。

## 👤 作者

**Ab23021580**
- GitHub: [@Ab23021580](https://github.com/Ab23021580)

## 🌟 Star History

如果这个项目对你有帮助，请给一个 ⭐️！

---

**注意**: 这是一个学习项目，展示了 Android 应用开发的完整流程和艾宾浩斯记忆曲线的实际应用。