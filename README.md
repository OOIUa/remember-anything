<div align="center">

# 📝 记了么 · Jileme

**一款轻量、清新的 Android 日常习惯打卡应用**

[![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)](https://developer.android.com)
[![Language](https://img.shields.io/badge/Language-Kotlin-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![UI](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Min SDK](https://img.shields.io/badge/Min%20SDK-24-brightgreen)](https://developer.android.com/studio/releases/platforms)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](./LICENSE)

<br/>

> 用 **月历** 分别记录 **戒色 · 抽烟 · 拉屎** 三件事，数据保存在本机，简单干净。
>
> *A minimal habit tracker with per-habit calendars, built with Jetpack Compose + Material 3.*

</div>

---

## ✨ 功能特性

<table>
<tr>
<td width="50%">

**🗓️ 月历打卡**
- 三个独立 Tab，各有专属颜色
- 月历视图，点选日期勾选 / 取消记录
- 当日记录一目了然

</td>
<td width="50%">

**📊 习惯统计**
- 本月已打卡天数实时统计
- 每个习惯独立追踪
- 历史数据永久保存在本机

</td>
</tr>
<tr>
<td width="50%">

**🎨 精致 UI**
- iOS 液态玻璃风格设计
- 全面屏沉浸式体验
- 流畅动画与过渡效果

</td>
<td width="50%">

**🔧 实用工具**
- 内置转盘工具，帮你做选择
- 更多工具持续上线中
- 数据本地存储，无需网络

</td>
</tr>
</table>

---

## 🖥️ 界面预览

<div align="center">

| 戒了么 | 抽了么 | 拉了么 | 工具页 |
|:------:|:------:|:------:|:------:|
| 🟢 | 🟠 | 🔵 | 🩵 |
| 森绿主题 | 琥珀橙主题 | 海洋蓝主题 | 青色主题 |

> *截图将在首个正式版本发布后更新*

</div>

---

## 🗂️ 功能模块

| Tab | 路由 | 主题色 | 说明 |
|-----|------|--------|------|
| 🟢 **戒了么** | `jie_se` | 森绿 `#2E7D32` | 戒色习惯记录 |
| 🟠 **抽了么** | `chou_yan` | 琥珀橙 `#E65100` | 抽烟习惯记录 |
| 🔵 **拉了么** | `la_shi` | 海洋蓝 `#0277BD` | 如厕习惯记录 |
| 🩵 **工具箱** | `tools` | 青色 `#00ACC1` | 转盘 & 更多工具 |

---

## 🛠️ 技术栈

<div align="center">

| 类别 | 技术选型 |
|------|---------|
| 🔤 语言 | ![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?logo=kotlin&logoColor=white) |
| 🎨 UI 框架 | Jetpack Compose · Material 3 |
| 🏗️ 架构 | MVVM · 单 Activity · Navigation Compose |
| 💉 依赖注入 | Hilt |
| 🗄️ 本地数据库 | Room + KSP |
| ⚡ 异步 | Kotlin Coroutines · Flow |
| 🎭 动画 | Compose Animation · AnimateFloatAsState |
| 📦 最低 SDK | 24 (Android 7.0) |
| 🎯 目标 SDK | 34 (Android 14) |

</div>

---

## 🚀 快速开始

### 环境要求

- **Android Studio** Hedgehog (2023.1.1) 或更高版本
- **JDK 17**
- **Android SDK 34**

### 运行步骤

```bash
# 1. 克隆仓库
git clone https://github.com/your-username/Jileme.git

# 2. 用 Android Studio 打开项目根目录
# File → Open → 选择包含 settings.gradle.kts 的目录

# 3. 等待 Gradle 同步完成，运行即可
```

**命令行构建：**

```bash
# macOS / Linux
./gradlew :app:assembleDebug

# Windows
gradlew.bat :app:assembleDebug
```

> 若仓库中未包含 `gradlew`，在 Android Studio 中先执行一次 Gradle Sync 会自动生成 Wrapper。

---

## 📁 项目结构

```
Jileme/
├── app/
│   └── src/main/java/com/example/jileme/
│       ├── 📄 MainActivity.kt
│       ├── 📄 JilemeApplication.kt
│       ├── 📦 data/              # Room Entity · DAO · Repository · Mapper
│       ├── 📦 domain/            # 领域模型
│       ├── 📦 di/                # Hilt 模块（DatabaseModule）
│       └── 📦 presentation/
│           ├── ui/detail/        # 习惯详情页（月历打卡）
│           ├── ui/habit/         # 习惯日历页
│           ├── ui/tools/         # 工具箱（转盘等）
│           ├── ui/nav/           # 导航 + 玻璃底部导航栏
│           ├── ui/profile/       # 我的
│           ├── ui/theme/         # 主题 · 颜色 · GlassTheme
│           └── viewmodel/        # ViewModel 层
├── 📄 build.gradle.kts
└── 📄 settings.gradle.kts
```

---

## 📦 包名与发布

```
applicationId: com.example.jileme
```

> ⚠️ 正式发布前，请将 `applicationId` 改为自有域名反写，并同步：
> - `app/build.gradle.kts` 中的 `namespace` 与 `applicationId`
> - 所有 Kotlin 包路径
> - 签名配置（`key/` 目录下的 keystore）

---

## 📄 开源协议

本项目基于 **[Apache License 2.0](./LICENSE)** 开源。

版权归属：`Copyright 2026 Jileme contributors`

---

## 🤝 参与贡献

欢迎提交 Issue 或 Pull Request！

提交前请确保：
- [ ] 项目能正常 Gradle Sync
- [ ] 通过本地编译（无 lint 错误）
- [ ] 保持代码风格一致（Kotlin + Compose 最佳实践）

---

<div align="center">

**记了么** · 简单记录，坚持每一天 ✨

</div>
