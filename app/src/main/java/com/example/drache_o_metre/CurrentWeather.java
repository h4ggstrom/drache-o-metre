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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drache_o_metre.data.api_interact.WeatherDataManager;
import com.example.drache_o_metre.data.forecast_objects.DailyForecast;
import com.example.drache_o_metre.data.forecast_objects.HourlyForecast;
import com.example.drache_o_metre.data.adapters.DailyForecastAdapter;
import com.example.drache_o_metre.data.adapters.HourlyForecastAdapter;
import com.example.drache_o_metre.data.response.DailyWeatherResponse;
import com.example.drache_o_metre.data.response.HourlyWeatherResponse;
import com.example.drache_o_metre.data.response.WeatherCallback;

import java.util.ArrayList;
import java.util.List;

public class CurrentWeather extends AppCompatActivity {

    private RecyclerView hourlyForecastRecyclerView;
    private HourlyForecastAdapter hourlyForecastAdapter;
    private List<HourlyForecast> hourlyForecastList = new ArrayList<>();

    private RecyclerView dailyForecastRecyclerView;
    private DailyForecastAdapter dailyForecastAdapter;
    private List<DailyForecast> dailyForecastList = new ArrayList<>();

    private LocationManager locationManager;
    private LocationListener locationListener;

    private WeatherDataManager weatherDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation de WeatherDataManager pour les appels API
        weatherDataManager = new WeatherDataManager();

        // Initialisation du RecyclerView horizontal pour les prévisions horaires
        hourlyForecastRecyclerView = findViewById(R.id.hourlyForecastRecyclerView);
        hourlyForecastRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        hourlyForecastAdapter = new HourlyForecastAdapter(hourlyForecastList);
        hourlyForecastRecyclerView.setAdapter(hourlyForecastAdapter);

        // Initialisation du RecyclerView vertical pour les prévisions journalières
        dailyForecastRecyclerView = findViewById(R.id.weeklyForecastRecyclerView);
        dailyForecastRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        dailyForecastAdapter = new DailyForecastAdapter(dailyForecastList);
        dailyForecastRecyclerView.setAdapter(dailyForecastAdapter);

        // Récupérer le bouton "Details"
        Button detailsButton = findViewById(R.id.detailsButton);

        // Définir un OnClickListener pour lancer l'activité de prévisions hebdomadaires
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer un Intent pour lancer WeeklyForecastActivity
                Intent intent = new Intent(CurrentWeather.this, Weekly_Forecast.class);
                startActivity(intent);  // Lancer l'activité
            }
        });

        // Initialisation du LocationManager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Vérifier si les permissions sont accordées
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        // Définir un listener pour la localisation
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Lorsque la position change, récupérer la latitude et la longitude
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                // Utiliser la latitude et la longitude pour appeler l'API
                fetchWeatherData(latitude, longitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // N'est pas utilisé mais nécessaire pour la classe LocationListener
            }

            @Override
            public void onProviderEnabled(String provider) {
                // N'est pas utilisé mais nécessaire pour la classe LocationListener
            }

            @Override
            public void onProviderDisabled(String provider) {
                // N'est pas utilisé mais nécessaire pour la classe LocationListener
            }
        };

        // Demander la mise à jour de la localisation
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    private void fetchWeatherData(double latitude, double longitude) {
        // Effacer les anciennes données avant d'ajouter de nouvelles données
        hourlyForecastList.clear();
        dailyForecastList.clear();

        // Appels à l'API pour récupérer les prévisions horaires et journalières
        weatherDataManager.fetchHourlyWeather(latitude, longitude, new WeatherCallback<HourlyWeatherResponse>() {
            @Override
            public void onSuccess(HourlyWeatherResponse response) {
                // Ajouter les données horaires reçues dans la liste
                for (HourlyWeatherResponse.HourlyWeather hourlyWeather : response.getList()) {
                    hourlyForecastList.add(new HourlyForecast(
                            hourlyWeather.getDt_txt(),
                            hourlyWeather.getTemp() + "°C",
                            hourlyWeather.getWeatherIcon()
                    ));
                }
                hourlyForecastAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("zioum");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("CurrentWeather", "Error fetching hourly weather", t);
                Toast.makeText(CurrentWeather.this, "Failed to load hourly data", Toast.LENGTH_SHORT).show();
            }
        });

        weatherDataManager.fetchDailyWeather(latitude, longitude, new WeatherCallback<DailyWeatherResponse>() {
            @Override
            public void onSuccess(DailyWeatherResponse response) {
                // Ajouter les données journalières reçues dans la liste
                for (DailyWeatherResponse.DailyWeather dailyWeather : response.getList()) {
                    dailyForecastList.add(new DailyForecast(
                            dailyWeather.getDate(),
                            dailyWeather.getDayIcon(),
                            dailyWeather.getPoP()
                    ));
                }
                dailyForecastAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("zioum");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("CurrentWeather", "Error fetching daily weather", t);
                Toast.makeText(CurrentWeather.this, "Failed to load daily data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // La permission a été accordée, demander la mise à jour de la localisation
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Arrêter les mises à jour de localisation lorsque l'activité est arrêtée
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }
}
