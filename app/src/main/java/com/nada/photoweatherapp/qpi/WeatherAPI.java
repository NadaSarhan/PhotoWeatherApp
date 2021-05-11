package com.nada.photoweatherapp.qpi;

import com.nada.photoweatherapp.model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {
    @GET("weather")
    Call<Weather> getWeather(@Query("q") String countryName, @Query("appid") String apiKey);
}
