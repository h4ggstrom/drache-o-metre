package com.example.drache_o_metre;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * WeatherDataManager is responsible for retrieving weather data from the OpenWeatherMap API.
 *
 * @author Robin de Angelis
 * @version 1.0.0
 */
public class WeatherDataManager {

    private static final String TAG = "WeatherDataManager";
    private Context context;
    private LocationManager locationManager;
    private LocationCallback locationCallback;
    private static final String API_KEY = "bf2c3a286ba8ccc81df95e187107f1e6";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    public WeatherDataManager(Context context, LocationCallback locationCallback) {
        this.context = context;
        this.locationCallback = locationCallback;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * Asks the user for permission to access the device's location.
     */
    public void requestLocationUpdates() {
        try {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

        } catch (SecurityException e) {

            Log.e(TAG, "Missing permissions to access location.");
            locationCallback.onError("Missing permissions to access location.");

        }
    }

    /**
     * Listener for location updates.
     */
    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            // call to the API whenever the location is updated
            getWeatherData(location.getLatitude(), location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(@NonNull String provider) {}

        @Override
        public void onProviderDisabled(@NonNull String provider) {
            locationCallback.onError("Le service de localisation est désactivé.");
        }
    };

    /**
     * Retrieves weather data from the OpenWeatherMap API using Retrofit.
     *
     * @param latitude current latitude of the user
     * @param longitude current longitude of the user
     */
    private void getWeatherData(double latitude, double longitude) {

        /*
         * Retrofit configuration
         * this part of code is made by Robin, but greatly inspired by https://square.github.io/retrofit/
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) // JSON to Object conversion
                .build();

        OpenWeatherService service = retrofit.create(OpenWeatherService.class);
        Call<WeatherResponse> call = service.getCurrentWeather(latitude, longitude, API_KEY);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weather = response.body();
                    if (weather != null) {
                        locationCallback.onWeatherDataReceived(weather);
                    } else {
                        locationCallback.onError("Weather forecast error.");
                    }
                } else {
                    locationCallback.onError("Network error.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                locationCallback.onError("weather request failed : " + t.getMessage());
            }
        });
    }

    /**
     * Stops location updates.
     */
    public void stopLocationUpdates() {
        locationManager.removeUpdates(locationListener);
    }

    /**
     * Callback interface for handling weather data and errors.
     */
    public interface LocationCallback {
        void onWeatherDataReceived(WeatherResponse weather);
        void onError(String errorMessage);
    }
}
