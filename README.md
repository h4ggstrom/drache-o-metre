# Drache-o-metre

## Overview
Drache-o-metre provides accurate and up-to-date weather forecasts based on the user's current GPS location. Built with Android Studio, it uses the OpenWeather API to deliver detailed weather information and customizable settings. Drache-o-metre is a project within our mobile computing minor of our 3rd year of informatic licence.

## Activities
1. **Current Weather (MainActivity)**
   - Displays the current weather for the user's location.
   - Includes daily and hourly forecasts for the current week.

2. **Detailed Weather**
   - Provides detailed weather predictions for the upcoming days.
   - Displays minimum and maximum temperatures, humidity percentage, and other essential data.

3. **Settings**
   - Manage notification preferences.
   - Access additional information about the application.

## Technologies Used
- **Android Studio**: Primary development environment.
- **OpenWeather API**: Fetches real-time weather data.
- **GPS Integration**: Retrieves the user's current location for accurate forecasts.

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/h4ggstrom/drache-o-metre
   ```

2. Open the project in Android Studio.

3. Add your OpenWeather API key:
   - Navigate to `app\src\main\java\com\example\drache_o_metre\data\interact\responses`.
   - Create `ApiKey.java`
   - Add theses information, replacing `"YOUR APIKEY"`:
     ```java
     package com.example.drache_o_metre.data.interact.responses;
     public class ApiKey {
         private String apiKey = "YOUR APIKEY";
     
         public String getApiKey() {
            return apiKey;
         }
      }
     ```

4. Build and run the project on an Android device or emulator.

## How It Works
- The app uses GPS to determine the user's location.
- The OpenWeather API fetches weather data based on the coordinates.

## Dependencies
- OpenWeather API
- Android SDK
- Java

## License
This project is licensed under the MIT License. See the LICENSE file for more details.

## Authors
- [Robin DA](https://github.com/h4ggstrom) : <robin.de-angelis@etu.cyu.fr>
- [Killian T](https://github.com/mrktttt) : <killian.treuil@etu.cyu.fr>

## Additional Notes
- Ensure your device has GPS enabled for accurate location-based forecasts.
- For support or feature requests, contact us via GitHub or email.

[Check out the GitHub repository](https://github.com/h4ggstrom/drache-o-metre).

