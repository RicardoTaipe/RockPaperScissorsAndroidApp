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

## 🖼 Screenshots
Here are some screenshots of the app:
| Play Screen      | Gameplay Screen | Rules Screen     |
| ---        |    ----    |          --- |
| ![image](https://github.com/user-attachments/assets/42c264e4-86f3-4688-9ceb-765d2cd78a23)| ![image](https://github.com/user-attachments/assets/8a656b74-ba48-4cd7-a85b-a5b529f62728)| ![image](https://github.com/user-attachments/assets/03812753-01c6-4c44-b2b2-0322f8ac6cb0)|

---

## 🎨 UI Design
The UI design for this app is based on designs provided by [Frontend Mentor](https://www.frontendmentor.io/challenges/rock-paper-scissors-game-pTgwgvgH) . Their resources were instrumental in creating a clean and user-friendly interface for the game.

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
├── countdown/         # Countdown Timer logic
│   ├── model/         # Game-related models
├── data/
│   ├── repository/    # Handles model and bussiness logic
├── di/                # Manual dependency injection setup
├── feature/
│   ├── view/          # Activities/Fragments (UI)
│   ├── viewmodel/     # ViewModels for UI logic
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
