# Documentation

## 📄 Overview
This application is designed to help users **manage their weight** through personalized meal planning.  
At the start, users input their **gender**, **height**, **age**, and **goal** (e.g., lose, maintain, or gain weight). Based on this information, the app displays a **daily meal plan** tailored to the user’s needs.

It also includes a **search screen** where users can explore and select foods to add to their meals.

The app is built using a **multi-module architecture** to promote separation of concerns and scalability.

All nutritional data is fetched from an **open API** using the **Retrofit** library.

It follows the **Model-View-Intent (MVI)** architecture, adheres to **SOLID principles**, and applies **Clean Code** practices for maintainability and extensibility.

## 🛠️ Technologies Used

- **Retrofit**  
  Used for HTTP requests to consume data from a nutritional open API.

- **Hilt**  
  Used for dependency injection to simplify module management and testing.

- **Jetpack Compose**  
  Used to create a modern, declarative UI with reactive updates.

- **Kotlin Flow & Coroutines**  
  Used for asynchronous programming and real-time state updates.

- **Room**  
  Used for local data persistence and caching.

- **Multi-Module Architecture**  
  Organizes code for better separation of responsibilities and team scalability.

- **Unit & UI Testing**  
  Includes unit tests for logic validation and UI tests for interface behavior.

## 🎯 Architecture Highlights

- MVI pattern enables a clear and consistent state management approach.
- Multi-module design improves code modularity and maintainability.
- SOLID and Clean Code principles ensure high-quality, extensible code.
- Personalized meal plans are dynamically generated based on user data.
- Seamless food search and selection experience via Compose UI.

---

Feel free to contribute, report issues, or suggest enhancements to support the app's evolution!


https://github.com/user-attachments/assets/005701df-9ffd-433c-b036-9bc9d33537cf

