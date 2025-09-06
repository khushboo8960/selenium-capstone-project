# 🚀 Selenium Capstone Project

This repository contains a **Selenium Test Automation Framework** developed as part of the **Capstone Project during Wipro Training**.  
The project automates end-to-end test scenarios on [SauceDemo](https://www.saucedemo.com/) using **Java, Selenium, TestNG, and Maven** with a **Page Object Model (POM) framework**.

---

## ✨ Features
- ✅ 40 Automated Test Cases
- ✅ Covers **Login, Inventory, Cart, Checkout, Menu & UI** modules
- ✅ Built with **Page Object Model (POM)** for reusability
- ✅ Uses **WebDriverManager** for driver setup (no manual driver download)
- ✅ **Configurable browser support** via `config.properties`
- ✅ **TestNG** for test management and reporting
- ✅ Waits added for **presentation-friendly demo**

---

## 📂 Project Structure
selenium-capstone-project/
│── pom.xml # Maven dependencies
│── testng.xml # TestNG suite configuration
│── config.properties # Browser configuration
│
├── src/
│ ├── main/java
│ │ ├── pages/ # Page Object classes
│ │ └── utils/ # DriverFactory & utilities
│ │
│ └── test/java/tests # Test classes (40 test cases)
│
└── README.md # Project documentation


---

## ⚙️ Tools & Technologies
- **Java 17+**
- **Selenium WebDriver 4**
- **TestNG**
- **Maven**
- **WebDriverManager**
- **GitHub / Jenkins (for CI/CD)**

---

## 🚀 How to Run the Tests

1. **Clone the repository**:
   ```bash
   git clone https://github.com/<your-username>/selenium-capstone-project.git
   cd selenium-capstone-project
Run using Maven:
mvn clean test
