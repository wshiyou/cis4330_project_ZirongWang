# AirPresenter

AirPresenter is a real-time gesture-based presentation control system using smartphone sensors and machine learning.

It allows users to control presentation slides by moving their phone, with cross-device communication between Android and a desktop system.

---

## ✨ Features

- Swipe right → Next slide  
- Swipe left → Previous slide  
- Gesture ON/OFF toggle  
- Manual control buttons (alternative to gesture control)  
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
- A machine learning model predicts gestures (left, right, idle)  
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

## ⚙️ How to Use

### 1. Download the Release

Download the latest version from the **Releases** page.

Included files:
- Android APK  
- Windows desktop executable  
- ML model file (`gesture_model.pkl`)  

---

### 2. Install the Android App

Install the APK on your Android phone.

---

### 3. Start the Desktop App

- Place the `.exe` file and `gesture_model.pkl` in the same folder  
- Run the `.exe` on your computer  

The server will start and display an IP address.

---

### 4. Connect the Phone

- Make sure both devices are on the same network  
- Enter the IP address shown on the computer into the Android app  

---

### 5. Control Slides

Open your presentation in slideshow mode.

- Swipe right → Next slide  
- Swipe left → Previous slide  
- Use buttons if gesture control is turned off  

---

## 🔍 How It Works
 
- The app continuously collects sensor data  
- Motion is filtered using threshold detection  
- Data is processed within a motion window  
- The model predicts gestures using KNN  
- Repeated predictions are required to trigger actions  
- A cooldown mechanism prevents repeated triggers  
- Users can switch between gesture control and manual button control  

---

## 🧑‍💻 Developer Setup 

### Android App

- Open the project in Android Studio  
- Connect a real Android device  
- Click **Run**  

---

### Python Server

Install dependencies:

```bash
pip install pynput firebase-admin flask joblib

Run:

python airPresenter.py

Notes
Requires Android 7.0+
Emulator may not support sensors
Gesture OFF disables motion control
Performance may vary depending on device sensitivity

Author
Zirong Wang
