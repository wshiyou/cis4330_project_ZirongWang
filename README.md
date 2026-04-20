# AirPresenter

AirPresenter is a gesture-based presentation controller using smartphone sensors.  
It allows users to control slides by moving their phone.

---

## Features

- Swipe right → Next slide
- Swipe left → Previous slide
- Gesture ON/OFF toggle
- Vibration feedback
- Manual control buttons

---

## Screenshots

### Gesture ON
<img width="427" height="914" alt="image" src="https://github.com/user-attachments/assets/4addecdf-01e9-4912-b408-748013824d31" />



### Gesture OFF
<img width="430" height="916" alt="image" src="https://github.com/user-attachments/assets/ed4d1454-7474-4cea-9d3a-fbe1c9bbee8e" />



---

## Tech Stack

- Android (Kotlin)
- Firebase Realtime Database
- Python (desktop controller)

---

## How to Run

### 1. Android App

1. Open the project in Android Studio
2. Connect a real Android device (recommended)
3. Click **Run**
4. Enable sensors and vibration if needed

---

### 2. Python Script (PC Side)

1. Make sure Python 3 is installed
2. Install required libraries:

```bash
pip install pynput firebase-admin


Run the script:
python airPresenter.py


### 3.How It Works
The Android app detects gestures using the accelerometer
It sends commands ("next", "prev") to Firebase
The Python script listens for commands
The Python script simulates keyboard input to control slides
Notes
Requires Android 7.0 or above
Emulator may not support sensors or vibration
Gesture OFF disables motion control


Author
Zirong Wang
