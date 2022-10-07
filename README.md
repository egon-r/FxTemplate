# FxTemplate

A template for a modern JavaFx cross-platform desktop application.

## Core
- Gradle 7.5.1, Kotlin DSL
- Java 17, modular
- Kotlin 1.6.20
- JavaFX 17

## Features
- [ZGC](https://openjdk.org/projects/zgc/) enabled
  - **sub millisecond** pause times (G1 and parallel often pause for over 100ms)
  - able to free unused memory
  - one downside is that `-Xmx` has to be set
- Logging using [Flogger](https://google.github.io/flogger/)
  - RollingFileHandler with ZIP compression and size limiting
- Asynchronous programming with [kotlinx-coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- GUI Testing using [TestFX](https://github.com/TestFX/TestFX)
- Documentation using [Dokka](https://github.com/Kotlin/dokka)
- Simple MVVM example
