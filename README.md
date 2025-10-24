# YASSIR - API Automation Test (Java + RestAssured)

This project contains automated tests for public APIs using **Java**, **Maven**, **TestNG**, and **RestAssured**.

## Project Overview
## How to Run the Project

### 🧰 Prerequisites
Before running the tests, make sure you have installed:
- Java 17 or higher
- Maven
- Internet connection (for API calls)

### ▶️ Run the tests

To execute all automated tests, use the following command:

```bash
mvn test
When you run this command, you will see messages from Maven in your terminal.
If everything works correctly, you will see this line at the end:
[INFO] BUILD SUCCESS
That means all tests passed successfully.
## 🧪 API Tests Included
### 1. Ipstack API Test
This test validates an **authenticated API** available at [https://ipstack.com](https://ipstack.com).

- Sends a GET request to:  
  `http://api.ipstack.com/134.201.250.155?access_key=YOUR_ACCESS_KEY`
- Uses your personal API key for authentication
- Checks that the response status is **200**
- Verifies that the IP in the response matches the requested one
- Ensures that the country name and city are present in the JSON response

### 🧾 Example of successful output

ip: 134.201.250.155
country_name: United States
city: Huntington Beach
