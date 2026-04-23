# AirPresenter

AirPresenter is a real-time gesture-based presentation control system using smartphone sensors and machine learning.

It allows users to control presentation slides by moving their phone, with cross-device communication between Android and a desktop system.

---

## ✨ Features

- Swipe right → Next slide  
- Swipe left → Previous slide  
- Gesture ON/OFF toggle  
- Manual control buttons  
- Real-time gesture recognition  
- Vibration feedback  
- Stability optimization (threshold, motion window, cooldown)
- Supports both gesture-based interaction and manual button control

<p align="center">
  <img src="https://github.com/user-attachments/assets/40612013-83ac-449d-9091-edc19d768c7b" width="250"/>
  <img src="https://github.com/user-attachments/assets/21982cd2-da87-45f6-adaa-9c68e8a2b0e3" width="250"/>
</p>

---

## 🧠 System Overview

AirPresenter is an end-to-end system:

- Android app collects sensor data (accelerometer + gyroscope)  
- Data is sent to a Python server  
- A machine learning model predicts the gesture (left, right, idle)  
- Commands are sent to the desktop controller  
- Desktop simulates keyboard input to control slides  

---

## 🛠️ Tech Stack

- Android (Kotlin)
- Python (Flask)
- Machine Learning (KNN)
- Firebase Realtime Database
- pynput (keyboard control)

---

## ⚙️ How to Run

### 1. Android App

- Open the project in Android Studio  
- Connect a real Android device  
- Click **Run**  
- Enable sensors and vibration  

---

### 2. Desktop Controller

- Make sure Python 3 is installed  
- Install dependencies:

```bash
pip install pynput firebase-admin flask joblib

Place the .exe and .pkl model file in the same folder
Run: python airPresenter.py

3. Connect Devices
Make sure both devices are on the same network
Start the server and note the IP address
Enter the IP address in the Android app


 How It Works
The app continuously collects sensor data
Motion is filtered using threshold detection
Data is processed within a motion window
The model predicts gestures using KNN
Repeated predictions are required to trigger actions
A cooldown mechanism prevents repeated triggers
Users can either control slides using gestures or switch to manual button control for stability.


Notes
Requires Android 7.0+
Emulator may not support sensors
Gesture OFF disables motion control
Performance may vary depending on device sensitivity


Author
Zirong Wang
