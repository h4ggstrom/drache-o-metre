package com.example.drache_o_metre.data.interact;

public interface CurrentWeatherCallback {

    // Méthode appelée en cas de succès
    void onSuccess(String cityName, String temperature, String icon);

    // Méthode appelée en cas d'échec
    void onFailure(String errorMessage);
}
