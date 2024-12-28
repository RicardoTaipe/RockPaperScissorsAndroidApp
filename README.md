# Rock-Paper-Scissors App

## 📱 Overview
This is a simple Android application that implements the classic Rock-Paper-Scissors game. The app is built with the following features:

- **MVVM Architecture**: Ensures a clear separation of concerns and improves testability.
- **Manual Dependency Injection**: Achieved without using DI frameworks like Dagger or Hilt, promoting simplicity and control.
- **Unit and Instrumented Tests**: Provides comprehensive testing for both logic and UI components.

---

## 🔨 Features
- Play the Rock-Paper-Scissors game against the computer.
- Simple and intuitive UI using Android Views.
- Clear and modular code structure with MVVM.
- Test coverage for core functionalities.

---

## 🏗 Architecture
The app follows the **MVVM (Model-View-ViewModel)** architecture pattern for better maintainability and scalability:

- **Model**: Handles game logic (e.g., determining the winner) and provides data for the ViewModel.
- **ViewModel**: Acts as a mediator between the Model and the View. It exposes observable data to the View and contains no reference to the UI.
- **View**: Displays the UI and interacts with the ViewModel through data binding.

---

## 🛠️ Manual Dependency Injection
Instead of using a DI framework, dependencies are manually managed and passed through constructors or setters. This approach ensures:
- Lightweight and simple DI implementation.
- Full control over object lifecycle and instantiation.

---

## 🧪 Testing
The app includes the following tests:
- **Unit Tests**: Validates the core logic (e.g., game winner determination, input validation).
- **Instrumented Tests**: Tests the UI and interactions using the Android testing framework.

### Testing Tools
- **JUnit**: For unit testing.
- **Espresso**: For instrumented UI testing.

---

## 📂 Project Structure
```
app/
├── data/
│   ├── model/         # Game-related models
│   ├── repository/    # Data handling logic
├── ui/
│   ├── view/          # Activities/Fragments (UI)
│   ├── viewmodel/     # ViewModels for UI logic
├── di/                # Manual dependency injection setup
├── util/              # Utility classes
├── test/              # Unit tests
├── androidTest/       # Instrumented tests
```

---

## 🚀 Getting Started

### Running the App
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/rock-paper-scissors-app.git
   ```
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.

---

## 📋 Tests
### Running Unit Tests
1. Open the terminal in Android Studio.
2. Run the following command:
   ```bash
   ./gradlew test
   ```

### Running Instrumented Tests
1. Connect an emulator or physical device.
2. Run the following command:
   ```bash
   ./gradlew connectedAndroidTest
   ```

---

## 💡 Future Enhancements
- Add multiplayer support.
- Introduce animations for enhanced gameplay experience.
- Refactor DI to use Hilt for larger projects.

---

## 🛡 License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---
