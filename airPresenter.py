import time
import requests
import pyautogui

FIREBASE_DB_URL = "https://airpresenter-default-rtdb.firebaseio.com"
COMMAND_PATH = "/airpresenter/command.json"


def fetch_command():
    url = FIREBASE_DB_URL + COMMAND_PATH
    response = requests.get(url, timeout=5)
    response.raise_for_status()
    return response.json()


def clear_command():
    url = FIREBASE_DB_URL + COMMAND_PATH
    response = requests.put(url, data="null", timeout=5)
    response.raise_for_status()


def execute_command(command: str):
    print(f"Executing: {command}")

    if command == "next":
        pyautogui.press("right")
    elif command == "prev":
        pyautogui.press("left")
    


def main():
    print("Listening for Firebase commands...")
    print("Make sure PowerPoint slideshow window is in front before testing.")

    while True:
        try:
            command = fetch_command()

            if command:
                execute_command(command)
                clear_command()

            time.sleep(0.5)

        except Exception as e:
            print("Error:", e)
            time.sleep(1)


if __name__ == "__main__":
    main()