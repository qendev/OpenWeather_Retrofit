package com.example.openweather_retrofit.Interface;

import com.example.openweather_retrofit.models.WResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApis {
    /*
    Get request to fetch city weather.Takes in two parameter-city name and API key.
    */
    @GET("/data/2.5/weather")
    Call<WResponse> getWeatherByCity(@Query("q") String city, @Query("appid") String apiKey);

}
