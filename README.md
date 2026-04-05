# 记了么（Remember Anything）

一款轻量级的 **Android 日常习惯打卡** 应用：用 **月历** 分别记录 **戒色 / 抽烟 / 拉屎** 三件事，数据保存在本机（Room）。

> 自律的人命最好，规范自己从我做起，哦耶

## 功能概览

- **三个独立 Tab**：戒了么、抽了么、拉了么 —— 每个模块自带 **月历视图**，点选日期后可在下方 **当日记录** 中勾选/取消。
- **本月统计**：显示当前习惯在本月已记录天数。

## 技术栈

| 类别 | 选型 |
|------|------|
| 语言 | Kotlin |
| UI | Jetpack Compose、Material 3 |
| 架构 | 单 Activity + `Navigation Compose` |
| DI | Hilt |
| 本地库 | Room（KSP） |
| 异步 | Kotlin Coroutines、Flow |
| 最低 SDK | 24 |
| 目标 / 编译 SDK | 34 |

## 环境要求

- **Android Studio** Hedgehog (2023.1.1) 或更高（建议自带较新的 AGP / Kotlin）
- **JDK 17**
- Android SDK 34

## 如何运行

1. 使用 Android Studio **Open** 本仓库根目录（包含 `settings.gradle.kts`、`app` 模块）。
2. 等待 Gradle 同步完成。
3. 连接真机或启动模拟器，点击 **Run** 运行 `app` 模块。

若仓库中未包含 `gradlew` / `gradlew.bat`，可在 Android Studio 中执行 **File → Settings → Build → Gradle** 使用默认 Gradle 分发版，或通过 **Build → Generate Signed Bundle / APK** 前的同步自动生成 Wrapper（视 IDE 版本而定）。

命令行构建（在已配置好 `gradlew` 时）：

```bash
./gradlew :app:assembleDebug
```

Windows：

```bat
gradlew.bat :app:assembleDebug
```

## 工程结构（简要）

```text
app/
├── src/main/java/com/example/jileme/
│   ├── MainActivity.kt
│   ├── JilemeApplication.kt
│   ├── data/           # Room、Repository、Mapper
│   ├── domain/         # 领域模型
│   ├── di/             # Hilt DatabaseModule
│   └── presentation/
│       ├── ui/habit/   # 习惯月历页
│       ├── ui/nav/     # 导航与玻璃底栏
│       ├── ui/profile/ # 我的
│       ├── ui/theme/   # 主题、颜色、字体、形状
│       └── viewmodel/
├── src/main/res/
└── build.gradle.kts
build.gradle.kts
settings.gradle.kts
```

## 包名与应用 ID

- `applicationId`：`com.example.jileme`
- 发布前请按需修改为自有域名反写，并同步 `namespace`、包路径与签名配置。

## 开源协议

本项目采用 **[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)**，全文见仓库根目录 [`LICENSE`](./LICENSE)。

版权行可按需在 `LICENSE` 附录处改为你的姓名或组织（当前为 `Copyright 2026 Jileme contributors`）。

## 贡献

欢迎 Issue / Pull Request。提交前请确保项目能 **Sync** 并通过本地编译。

---

**记了么** — 简单记录每一天。
