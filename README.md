# Study Sync App

## Overview

The Study Sync App is an Android application designed to facilitate collaboration and resource sharing among students and educators. The primary goal of the project is to provide a seamless and efficient platform for uploading, storing, and sharing educational resources within a classroom or study group.

### Goals
- Streamline the process of sharing educational materials.
- Enhance communication and collaboration within study groups and classrooms.
- Provide a user-friendly interface for managing assignments, schedules, and classroom activities.

### Primary Features
- **File Sharing:** Upload and share files with classmates and educators.
- **Assignment Management:** Create, view, and manage assignments with due dates and descriptions.
- **Schedule Management:** View and manage class schedules and events.
- **Classroom Management:** Create and join classrooms, and manage classroom details.
- **User Authentication:** Secure login and registration for users.

### Target Audience
- **Students:** To help them manage their assignments, schedules, and classroom activities efficiently.
- **Study Groups:** To enhance collaboration and resource sharing among group members.

### Problem Addressed
The Study Sync App addresses the need for a centralized platform where educational resources can be easily shared and accessed. It reduces the dependency on physical storage devices and simplifies the process of resource distribution, making it easier for students and educators to collaborate and stay organized.

## Keywords and Their Definitions

- **Android Studio:** An integrated development environment (IDE) for Android development.
- **Gradle:** A build automation tool used for managing dependencies and building the project.
- **Kotlin:** A modern programming language used for Android development.
- **Supabase:** An open-source backend as a service (BaaS) that provides storage and authentication services.
- **ProGuard:** A tool for code optimization and obfuscation in Android applications.
- **MVVM (Model-View-ViewModel):** An architectural pattern used to separate the user interface logic from the business logic.
- **Compose:** A modern toolkit for building native UI in Android applications.

## Libraries Used and Their Usages

### Supabase
- **Purpose:** Provides backend services such as storage and authentication.
- **Key Functionalities:** File upload, user authentication, database operations.
- **Version:** `3.0.2`

### Compose
- **Purpose:** Modern toolkit for building native UI in Android applications.
- **Key Functionalities:** Declarative UI components, state management.
- **Version:** `2024.04.01`

### Arrow
- **Purpose:** Functional programming library for Kotlin.
- **Key Functionalities:** Provides functional data types and abstractions.
- **Version:** `1.2.0`

### Ktor
- **Purpose:** HTTP client for making network requests.
- **Key Functionalities:** Provides a flexible and asynchronous HTTP client.
- **Version:** `3.0.1`

### AndroidX Libraries
- **Purpose:** Provides essential components for Android development.
- **Key Functionalities:** Core KTX, Lifecycle, Navigation, Activity Compose, etc.
- **Versions:**
  - Core KTX: `1.10.1`
  - Lifecycle Runtime KTX: `2.6.1`
  - Activity Compose: `1.8.0`
  - Navigation Compose: `2.8.9`
  - Core Splashscreen: `1.0.1`
  - Lifecycle ViewModel Compose: `2.8.7`
  - Compose Material Dialogs Datetime: `0.8.1-rc`
  - Compose BOM: `2024.04.01`

### Material3
- **Purpose:** Provides Material Design components.
- **Key Functionalities:** Modern UI components following Material Design guidelines.
- **Version:** Latest version from Compose BOM.

### Desugar JDK Libs
- **Purpose:** Provides support for Java 8+ APIs on older Android versions.
- **Key Functionalities:** Enables the use of newer Java APIs.
- **Version:** `1.1.6`

## Architecture

The project follows the MVVM (Model-View-ViewModel) architecture pattern, which promotes a clear separation of concerns and enhances testability and maintainability.

### Separation of Concerns

- **Model:** Represents the data layer, including data sources, repositories, and business logic.
- **View:** Represents the UI layer, displaying data and handling user interactions.
- **ViewModel:** Acts as a bridge between the Model and View, managing UI-related data and handling business logic.

### Role of Each Layer/Component

1. **Model Layer:**
   - **Data Models:** Define the structure of data used in the application.
   - **Repositories:** Handle data operations and business logic, providing a clean API for data access.
   - **Remote Data Sources:** Interact with external services or APIs to fetch or send data.

2. **View Layer:**
   - **UI Components:** Compose-based UI components that render the user interface and handle user interactions.
   - **Navigation:** Manages navigation between different screens.

3. **ViewModel Layer:**
   - **ViewModels:** Manage UI-related data and handle business logic, exposing data to the View through LiveData or StateFlow.
   - **State Management:** Maintain the state of the UI and handle events triggered by the user.

### Interaction Between Layers/Components

- **ViewModel to Model:** The ViewModel interacts with the Model layer through repositories to fetch or update data.
- **View to ViewModel:** The View observes changes in the ViewModel and updates the UI accordingly.
- **ViewModel to View:** The ViewModel exposes data to the View through LiveData or StateFlow, ensuring that the UI is updated with the latest data.

### Custom Architectural Decisions

- **Use of Arrow Library:** The project uses the Arrow library for functional programming, providing functional data types and abstractions to handle errors and data transformations more effectively.
- **Compose for UI:** The project leverages Jetpack Compose for building the UI, providing a modern and declarative approach to UI development.
- **Supabase Integration:** The project integrates Supabase for backend services, including storage and authentication, providing a scalable and efficient backend solution.

## Directory Structure

### Root Directory

- **.gitignore:** Specifies files and directories to be ignored by Git.
- **.gradle/:** Contains Gradle-specific files and caches.
- **.idea/:** Contains project-specific settings for Android Studio.
- **.kotlin/:** Contains Kotlin-specific files and caches.
- **app/:** Contains the main application code and resources.
- **build/:** Contains build outputs.
- **build.gradle.kts:** Configures the build script using Kotlin DSL.
- **gradle/:** Contains Gradle wrapper files.
- **gradle.properties:** Contains project-wide Gradle settings.
- **gradlew:** Gradle wrapper script for Unix-based systems.
- **gradlew.bat:** Gradle wrapper script for Windows.
- **local.properties:** Contains local configuration properties.
- **settings.gradle.kts:** Configures the settings for the Gradle build.

### app Directory

- **src/main/:** Contains the main source code and resources for the application.
  - **java/com/example/studysyncapp/:** Contains the Kotlin source code for the application.
    - **core/:** Contains core utilities and configurations.
    - **data/remote/:** Contains classes for remote data access.
    - **domain/model/:** Contains data models used in the application.
    - **domain/repository/:** Contains repository interfaces for data operations.
    - **presentation/:** Contains UI components and view models.
    - **utils/:** Contains utility functions and common UI components.
  - **res/:** Contains application resources.
    - **drawable/:** Contains drawable resources.
    - **values/:** Contains value resources.
    - **xml/:** Contains XML configuration files.
  - **AndroidManifest.xml:** Defines essential information about the application.

## Technical Highlights

### Unique or Advanced Features

1. **Supabase Integration:**
   - **Feature:** The app integrates with Supabase for backend services, including storage and authentication.
   - **Details:** The `FilesApi` class handles file uploads to Supabase storage, providing progress updates and handling errors.
   - **Configuration:** Configured with the Supabase URL and API key in the core module.

2. **Jetpack Compose for UI:**
   - **Feature:** The app leverages Jetpack Compose for building the UI, providing a modern and declarative approach to UI development.
   - **Details:** Compose components are used extensively throughout the app, such as in the `SignUpScreen` and `CoursesScreen`.
   - **Configuration:** Enabled in the `buildFeatures` section of the 

build.gradle.kts

 file.

5. **Arrow for Functional Programming:**
   - **Feature:** The app uses the Arrow library for functional programming, providing functional data types and abstractions.
   - **Details:** Used in various parts of the app, such as in the `CoursesViewModel` for handling data operations.
   - **Configuration:** Added as a dependency in the 

build.gradle.kts

 file.

6. **Custom UI Components:**
   - **Feature:** The app includes custom UI components for a consistent and reusable design.
   - **Details:** Components like `FormDateTimePicker`, `FormFilePicker`, and `DefaultButton` are defined in the `CommonUi` file.
   - **Configuration:** These components are used throughout the app to maintain a consistent UI.

### Known Challenges and Resolutions

1. **Challenge:** Handling File Upload Progress.
   - **Issue:** Providing real-time progress updates during file uploads.
   - **Resolution:** Implemented the `uploadAsFlow` method in the `FilesApi` class to collect upload progress and update the UI accordingly.

2. **Challenge:** Managing State in Compose
   - **Issue:** Ensuring that the UI updates correctly in response to state changes.
   - **Resolution:** Used `StateFlow` and `collectAsState` to manage and observe state in view models and UI components, as seen in the `ClassroomDetailsScreen`.

4. **Challenge:** Ensuring Compatibility with Different Android Versions
   - **Issue:** Supporting a wide range of Android versions while using modern libraries and features.
   - **Resolution:** Enabled core library desugaring in the 

build.gradle.kts

 file to use Java 8+ APIs on older Android versions, ensuring compatibility across devices.

## How to Build and Run the Project

### Prerequisites
- Android Studio installed.
- Java Development Kit (JDK) 11 or higher.
- Internet connection for downloading dependencies.

### Steps

1. **Clone the Repository:**
   ```sh
   git clone <repository-url>
   cd <repository-directory>
   ```

2. **Open the Project in Android Studio:**
   - Open Android Studio.
   - Select "Open an existing project" and navigate to the project directory.

3. **Sync Gradle:**
   - Android Studio will automatically sync the Gradle files. If not, click on "Sync Project with Gradle Files" in the toolbar.

4. **Build the Project:**
   - Click on "Build" in the toolbar and select "Make Project"

5. **Run the Project:**
   - Connect an Android device or start an emulator.
   - Click on "Run" in the toolbar and select "Run 'app'".

## References

- [Supabase Documentation](https://supabase.io/docs)
- [Hilt Documentation](https://dagger.dev/hilt/)
- [Firebase Documentation](https://firebase.google.com/docs)
- [JUnit Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Android Developer Guide](https://developer.android.com/guide)
- [Gradle Documentation](https://docs.gradle.org/current/userguide/userguide.html)
