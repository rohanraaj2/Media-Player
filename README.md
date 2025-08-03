
# Media Player

This repository contains the source code and resources for a media player developed as part of a practical course at Technische Hochschule Ingolstadt. The project is structured into multiple tasks, each building upon the previous, culminating in a fully functional media player with a JavaFX-based GUI and automated JUnit acceptance tests.

## Project Structure

The repository is organized into subfolders for each task:

- **MediaPlayer_Task06** to **MediaPlayer_Task10**: Each folder represents a milestone in the development process, with incremental features and complexity.
- Each task folder contains:
  - `src/`: Java source code for that task.
  - `test/`: Test classes for unit and integration testing.
  - `cert/`: Certification/acceptance test classes.
  - `audiofiles/`: Sample audio files for testing (from Task 07 onwards).
  - `lib/`: Required JAR libraries (e.g., JavaFX, audio codecs).
  - `playlists/`: (Task 10) Example playlist files.
  - Task description PDFs and example solutions.

## Main Features

- **Audio File Handling**: Classes for parsing and managing different audio file types (MP3, OGG, WAV).
- **Playlist Management**: Support for creating and manipulating playlists.
- **JavaFX GUI**: A user interface for interacting with the media player (finalized in Task 10).
- **Automated Testing**: JUnit-based tests for all core functionalities.
- **Extensible Design**: Modular structure for easy extension and maintenance.

## How to Build and Run

1. **Dependencies**: Ensure you have Java (JDK 8 or higher) installed. All required libraries are in the `lib/` folders.
2. **Compiling**: Use your preferred IDE (e.g., Eclipse, IntelliJ) or the command line to compile the source code in the `src/` directories.
3. **Running**: Launch the main application class (typically in `src/studiplayer/ui/Player.java` for Task 10).
4. **Testing**: Run the JUnit test classes in the `test/` and `cert/` directories.

## Folder Overview (Task 10 Example)

- `src/studiplayer/audio/`: Core audio file classes and comparators.
- `src/studiplayer/ui/`: GUI components and player logic.
- `test/studiplayer/test/`: Unit tests for player features.
- `cert/studiplayer/cert/`: Acceptance and certification tests.
- `audiofiles/`: Example audio files for playback and testing.
- `lib/`: External libraries required for audio processing and playback.
- `playlists/`: Example playlist files.

## Notes

- Each task folder is self-contained and can be built and tested independently.
- The project demonstrates best practices in Java OOP, modularization, and test-driven development.
- For more details on each task, refer to the respective `TaskXX.pdf` files.
