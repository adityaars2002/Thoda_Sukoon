# 🌿 Thoda Sukoon – Mental Wellness Android App

**Thoda Sukoon** is a mental wellness Android application designed to support **college students** dealing with **anxiety, depression, and suicidal thoughts**.  
It offers **anonymous mental health support**, **AI-driven multilingual chatbot assistance**, and **location-based counselor recommendations** — all in one simple and secure app.

---

## 🧠 Features

- 🔒 **Anonymous Authentication**  
  Secure and private login system powered by a Node.js backend.

- 💬 **AI-Powered Multilingual Chatbot**  
  Provides emotional guidance and wellness advice in **3 languages** using REST API integration with Retrofit.

- 🚨 **Red Flag Detection System**  
  Detects distress keywords (like *“suicide”* or *“kill”*) and automatically connects users to the **TeleMANAS helpline** or nearby counselors for immediate help.

- 📍 **Nearby Counselor & Doctor Locator**  
  Uses the **Google Maps API** to fetch details of mental health professionals based on the user’s current location.

- 🎧 **Stress Relief Resources**  
  Includes curated **audio, video, and text** content to help users reduce stress and anxiety.

---

## ⚙️ Tech Stack

| Component | Technology |
|------------|-------------|
| **Frontend** | Java, XML |
| **Backend** | Node.js, Express.js |
| **API Integration** | Retrofit (REST API) |
| **Maps & Location** | Google Maps API |
| **Database** | MongoDB (via Node.js backend) |
| **Tools & IDE** | Android Studio |

---

## 🧩 Architecture

- **Client (Android App)**: Handles UI/UX, user input, and displays responses from the backend using Retrofit.  
- **Server (Node.js + Express)**: Manages user authentication, chatbot logic, counselor data, and API endpoints.  
- **Database (MongoDB)**: Stores counselor information and chat logs for analysis and personalization.  

---

## 🚀 How It Works

1. User opens the app and logs in **anonymously**.  
2. The app fetches the **nearest available counselors/doctors** using current GPS coordinates.  
3. The **chatbot** interacts in the user’s preferred language, offering emotional support.  
4. If the chatbot detects **suicidal or self-harm keywords**, it triggers a red flag and suggests immediate professional help.  
5. Users can also access **stress-relief content** to relax and calm their mind.  

---

## 📱 Screenshots (Optional)

> *(Add screenshots here once your UI is finalized)*  
Example:



yaml
Copy code

---

## 🧑‍💻 Installation & Setup

1. Clone the repository  
   ```bash
   git clone https://github.com/your-username/thoda-sukoon.git
Open the project in Android Studio.

Update your BASE_URL in ApiClient.java to match your backend server URL.

java
Copy code
private static final String BASE_URL = "https://your-backend-url/";
Build and run the app on an emulator or Android device.

🧭 Future Enhancements
Integration with TensorFlow Lite for mood detection from text.

Expansion to more Indian regional languages.

Addition of daily wellness challenges and user emotion tracking.

Dark mode and improved UI animations.

💖 Contribution
Contributions are welcome!
If you’d like to fix a bug or add a feature, feel free to fork this repository and open a pull request.

👨‍💻 Developer
Aditya Raj
📍 B.Tech ECE, Netaji Subhash Engineering College, Kolkata
🌐 LinkedIn
✉️ Email

🕊️ License
This project is licensed under the MIT License – feel free to use and modify it.

“Thoda Sukoon – because every mind deserves a little peace.” 🌱

yaml
Copy code

---

Would you like me to make a **shorter, minimalist version** (for lightweight GitHub repos) or keep this **detailed showcase version** (best for portfolio presentation)?






