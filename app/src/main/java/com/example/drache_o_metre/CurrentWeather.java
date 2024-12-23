package com.example.drache_o_metre;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drache_o_metre.data.adapters.DailyForecastAdapter;
import com.example.drache_o_metre.data.adapters.HourlyForecastAdapter;
import com.example.drache_o_metre.data.forecast_objects.DailyForecast;
import com.example.drache_o_metre.data.forecast_objects.HourlyForecast;
import com.example.drache_o_metre.data.interact.callback.CurrentWeatherCallback;
import com.example.drache_o_metre.data.interact.callback.DailyWeatherCallback;
import com.example.drache_o_metre.data.interact.callback.HourlyWeatherCallback;
import com.example.drache_o_metre.data.interact.WeatherDataManager;

import java.util.ArrayList;
import java.util.List;

public class CurrentWeather extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    private RecyclerView hourlyForecastRecyclerView;
    private HourlyForecastAdapter hourlyForecastAdapter;
    private List<HourlyForecast> hourlyForecastList = new ArrayList<>();

    private RecyclerView dailyForecastRecyclerView;
    private DailyForecastAdapter dailyForecastAdapter;
    private List<DailyForecast> dailyForecastList = new ArrayList<>();

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation du LocationManager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Initialisation des RecyclerView
        hourlyForecastRecyclerView = findViewById(R.id.hourlyForecastRecyclerView);
        hourlyForecastRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        hourlyForecastAdapter = new HourlyForecastAdapter(hourlyForecastList);
        hourlyForecastRecyclerView.setAdapter(hourlyForecastAdapter);

        dailyForecastRecyclerView = findViewById(R.id.weeklyForecastRecyclerView);
        dailyForecastRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        dailyForecastAdapter = new DailyForecastAdapter(dailyForecastList);
        dailyForecastRecyclerView.setAdapter(dailyForecastAdapter);


        Button detailsButton = findViewById(R.id.detailsButton);
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vérifier si les coordonnées sont disponibles
                if (locationManager != null && locationListener != null) {
                    // Obtenez la dernière localisation
                    try {
                        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (lastKnownLocation != null) {
                            double latitude = lastKnownLocation.getLatitude();
                            double longitude = lastKnownLocation.getLongitude();

                            // Crée l'intent pour démarrer l'activité Detailed_Forecast
                            Intent intent = new Intent(CurrentWeather.this, Detailed_Forecast.class);

                            // Passe la latitude et la longitude dans l'intent
                            intent.putExtra("LATITUDE", latitude);
                            intent.putExtra("LONGITUDE", longitude);

                            // Lance l'activité
                            startActivity(intent);
                        } else {
                            // Gérer le cas où la localisation n'est pas disponible
                            Toast.makeText(CurrentWeather.this, "Localisation non disponible", Toast.LENGTH_SHORT).show();
                        }
                    }catch (SecurityException e){
                        return;
                    }
                }
            }
        });


        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurrentWeather.this, Settings.class);
                startActivity(intent);
            }
        });

        // Demander la position de l'utilisateur
        requestLocationPermission();
    }

    // Demander la permission d'accès à la localisation
    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            fetchUserLocation();
        }
    }

    // Gérer la réponse de la demande de permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchUserLocation();
            } else {
                Toast.makeText(this, "Permission d'accès à la localisation refusée", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Récupérer la position de l'utilisateur via le LocationManager
    private void fetchUserLocation() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                fetchHourlyForecastData(latitude, longitude);
                fetchCurrentWeatherData(latitude, longitude);
                fetchDailyForecastData(latitude, longitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(@NonNull String provider) {}

            @Override
            public void onProviderDisabled(@NonNull String provider) {}
        };

        // Demander la localisation toutes les 5 secondes (c'est un exemple)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        }
    }

    // Modifié pour utiliser l'API avec la localisation
    private void fetchHourlyForecastData(double latitude, double longitude) {
        WeatherDataManager weatherDataManager = new WeatherDataManager();
        weatherDataManager.getHourlyForecast(latitude, longitude, new HourlyWeatherCallback() {
            @Override
            public void onSuccess(List<HourlyForecast> hourlyForecasts) {
                hourlyForecastList.clear();
                hourlyForecastList.addAll(hourlyForecasts);
                hourlyForecastAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(CurrentWeather.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchCurrentWeatherData(double latitude, double longitude) {
        WeatherDataManager weatherDataManager = new WeatherDataManager();
        weatherDataManager.getCurrentWeather(latitude, longitude, new CurrentWeatherCallback() {
            @Override
            public void onSuccess(String cityName, String temperature, String icon) {
                TextView appTitle = findViewById(R.id.appTitle);
                TextView currentTemperature = findViewById(R.id.currentTemperature);
                ImageView currentConditionIcon = findViewById(R.id.currentConditionIcon);
                appTitle.setText(cityName);
                currentTemperature.setText(temperature);
                currentConditionIcon.setImageResource(getResources().getIdentifier(icon, "drawable", getPackageName()));
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(CurrentWeather.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Exemple de données fictives pour les prévisions journalières
    private void fetchDailyForecastData(double latitude, double longitude) {
        WeatherDataManager weatherDataManager = new WeatherDataManager();
        weatherDataManager.getDailyWeather(latitude, longitude, 7, new DailyWeatherCallback() {
            @Override
            public void onSuccess(List<DailyForecast> dailyForecasts) {
                dailyForecastList.clear();
                dailyForecastList.addAll(dailyForecasts);
                dailyForecastAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(CurrentWeather.this, errorMessage, Toast.LENGTH_SHORT).show();
                Log.e("DailyWeatherCallback", "onFailure: " + errorMessage);
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);  // Ne pas oublier de désabonner quand l'activité est en pause
        }
    }
}
