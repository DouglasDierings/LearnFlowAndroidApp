# Learn Flow

Learn Flow is an Android application for managing employee training records across multiple work sites. It helps supervisors track employee assignments, site-specific onboarding requirements, and recurring monthly training completion in one mobile workflow.

## Features

- Firebase email/password authentication with login, registration, and password recovery screens.
- Employee management for adding, listing, searching, and removing employee records.
- Site-based organization for grouping employees by assigned location.
- Training completion tracking for required site documents and onboarding courses.
- Monthly Toolbox Talks tracking with month-by-month completion status.
- Firebase Realtime Database integration for storing user, employee, site, and training data.

## Tech Stack

- Java
- Android SDK
- Gradle Kotlin DSL
- Firebase Authentication
- Firebase Realtime Database
- AndroidX Navigation
- Android ViewBinding
- Material Components

## Project Structure

```text
.
+-- app/
|   +-- src/main/java/com/example/projecteve/
|   |   +-- activity/      # Authentication and account screens
|   |   +-- adapters/      # List adapters for employees, sites, and training checks
|   |   +-- fragments/     # Main app screens and navigation destinations
|   |   +-- models/        # Employee, site, course, and user data models
|   |   +-- utils/         # Firebase helper logic
|   +-- src/main/res/      # Layouts, drawables, navigation, menus, and values
+-- gradle/
|   +-- libs.versions.toml # Dependency and plugin versions
|   +-- wrapper/           # Gradle wrapper
+-- build.gradle.kts       # Root Gradle configuration
+-- settings.gradle.kts    # Project/module configuration
```

## Prerequisites

- Android Studio
- JDK 17 or compatible Android Studio bundled JDK
- Android SDK with API 34 installed
- A Firebase project with Authentication and Realtime Database enabled

## Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/DouglasDierings/LearnFlowAndroidApp.git
   cd LearnFlowAndroidApp
   ```

2. Open the project in Android Studio.

3. Add your Firebase Android configuration file:

   ```text
   app/google-services.json
   ```

4. Sync Gradle and run the app on an emulator or Android device.

## Firebase Setup

This repository does not track `app/google-services.json` because it is environment-specific Firebase configuration.

To configure the app:

1. Create or open a Firebase project.
2. Register an Android app using the application ID from `app/build.gradle.kts`:

   ```text
   com.example.projecteve
   ```

3. Download `google-services.json` from Firebase.
4. Place it at `app/google-services.json`.
5. Enable Email/Password sign-in under Firebase Authentication.
6. Enable Firebase Realtime Database and configure rules appropriate for your environment.

## Build and Test

Run unit tests:

```bash
./gradlew test
```

Build a debug APK:

```bash
./gradlew assembleDebug
```

Run instrumented tests with a connected device or emulator:

```bash
./gradlew connectedAndroidTest
```

On Windows PowerShell, use `.\gradlew.bat` instead of `./gradlew`.

## Roadmap

- Reporting dashboards for training progress and compliance visibility.
- Advanced filtering for employees, sites, and training status.
- Export options for audit or management reporting.
- More granular Firebase security rules and role-based access.

## Contributing

Contributions are welcome. Please open an issue or submit a pull request with a clear description of the change, why it is needed, and how it was tested.

## Credits

Design contributions by [Kelvin Dumas](https://github.com/kelvindumas).
