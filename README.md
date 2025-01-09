# Fitness Tracker App

A Kotlin-based Android application designed to help users track their fitness achievements and calorie intake effectively. The app integrates Google Firebase for real-time data storage and user authentication, ensuring a seamless and secure user experience.

## Features

- **User Authentication**: Secure login and signup functionality using Firebase Authentication.
- **Calorie Tracking**: Log daily meals and calculate calorie intake.
- **Fitness Achievements**: Monitor and track workout progress and milestones.
- **Data Synchronization**: Real-time storage and synchronization using Firebase Firestore.
- **User-Friendly UI**: Intuitive interface for logging meals and workouts.
- **Analytics Dashboard**: View graphical summaries of calorie consumption and fitness progress.

## Tech Stack

- **Kotlin**: Programming language for building Android applications.
- **Firebase**:
  - Authentication: Secure user login and registration.
  - Firestore: Real-time NoSQL database for storing user data.
  - Cloud Storage: For storing profile images and other media.
- **MVVM Architecture**: For a clean and maintainable codebase.


## Installation

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/emirardagn/project_part_1.git
    cd project_part_1
    ```

2. **Setup Firebase**:
    - Create a Firebase project [here](https://console.firebase.google.com/).
    - Enable Authentication (Email/Password).
    - Create a Firestore database in test mode.
    - Add the `google-services.json` file to the `app` directory.

3. **Build the Project**:
    - Open the project in Android Studio.
    - Sync Gradle files.
    - Build and run the application on an emulator or physical device.

## Usage

1. **Sign Up or Log In**: Start by creating an account or logging in.
2. **Log Meals**: Input meals with calorie information for daily tracking.
3. **Track Workouts**: Add and view fitness achievements.
4. **Analyze Data**: Use the dashboard to view weekly or monthly progress.

## Project Structure

- **Activities/Fragments**:
  - `MainActivity`: Hosts navigation.
  - `LoginActivity`: Handles user login.
  - `DashboardFragment`: Displays fitness and calorie summaries.
- **ViewModel**: Manages app logic and communicates with Firebase.
- **Repository**: Handles data operations with Firestore.
- **Utils**: Contains helper functions and classes.

## Contribution

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch: `git checkout -b feature/your-feature-name`.
3. Commit your changes: `git commit -m 'Add some feature'`.
4. Push to the branch: `git push origin feature/your-feature-name`.
5. Submit a pull request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## Contact

For any questions or support, feel free to reach out:

- **Email**: emirardagn@example.com
- **GitHub**: [emirardagn](https://github.com/emirardagn)
