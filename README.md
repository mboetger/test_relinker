3.  **Open in Android Studio:**
    *   Launch Android Studio.
    *   Select "Open an existing Android Studio project."
    *   Navigate to the directory where you cloned the repository and select it.

4.  **Build and Run:**
    *   Click on the "Sync Project with Gradle Files" button (the elephant icon with a green arrow).
    *   Select a target device (emulator or physical device).
    *   Click the "Run" button (the green triangle).

## Building the App

The project uses Gradle for building. Here are the common Gradle tasks:

*   **`./gradlew assembleDebug`:** Builds the debug version of the APK.
    *   You can run this from the terminal inside the project's root folder.
    *   The output APK will be in: `app/build/outputs/apk/debug/app-debug.apk`
*   **`./gradlew assembleRelease`:** Builds the release version of the APK.
*   **`./gradlew build`:** Builds all outputs (debug and release).
*   **`./gradlew clean`:** Deletes the `build` directories to start with a clean build.
* **`./gradlew tasks`**: Lists all the available gradle tasks.

## Code Overview

*   **`app/src/main/java/.../MainActivity.kt`:** The main Kotlin class. It demonstrates:
    *   Using Jetpack Compose to create a UI.
    *   Loading the native library with `System.loadLibrary()`.
    *   Declaring and calling an `external` JNI method (`stringFromJNI`).
    * Calling the `doSomethingInNativeApp` function inside the native app.
*   **`app/src/main/cpp/native-lib.cpp`:** The main C++ source file. It demonstrates:
    *   Implementing the `stringFromJNI` function using JNI.
    * Creating the proper JNI function.
* **`native-app/native-app.cpp`**: A simple C++ program that implements a function called `doSomethingInNativeApp`.
*   **`app/src/main/cpp/CMakeLists.txt`:** The CMake build script for building the C++ library. It shows:
    *   How to define a library (`add_library`).
    *   How to include source files.
    *   How to link with system libraries (e.g., `log`).
    * How to add other CMake scripts using `add_subdirectory`.
*  **`native-app/CMakeLists.txt`**: The CMake build script for building the C++ app.
*   **`app/build.gradle.kts`:** The Gradle build script for the app module. It demonstrates:
    *   Configuring the Android build.
    *   Telling the Android Gradle Plugin to use CMake (`externalNativeBuild`).
    *   Specifying the C++ build file.
    * Configuring the NDK and its `abiFilters`.

## Learning Resources

*   **Android Developers Website:** [https://developer.android.com/](https://developer.android.com/)
*   **Jetpack Compose:** [https://developer.android.com/jetpack/compose](https://developer.android.com/jetpack/compose)
*   **Kotlin:** [https://kotlinlang.org/](https://kotlinlang.org/)
*   **Android NDK:** [https://developer.android.com/ndk](https://developer.android.com/ndk)
*   **CMake:** [https://cmake.org/](https://cmake.org/)
* **JNI**: [https://docs.oracle.com/javase/8/docs/technotes/guides/jni/](https://docs.oracle.com/javase/8/docs/technotes/guides/jni/)

## License

This project is licensed under the [License Name] License - see the [LICENSE.md](LICENSE.md) file for details.

## Contact

If you have any questions or suggestions, feel free to open an issue or contact the project maintainers.

## Contributing

Contributions are welcome! Please read the [CONTRIBUTING.md](CONTRIBUTING.md) file for details on how to submit pull requests.