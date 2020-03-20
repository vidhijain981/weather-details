package com.example.map;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public class RetroDataRetrieveAPI {

    private static final String key="9023bbda7cfbeef3a3b495dc1ecfb45a";
    private static final String url="http://api.openweathermap.org/data/2.5/";
    public static WeatherService weatherService=null;
    public static WeatherService getService()
    {
        if(weatherService==null)
        {
            Retrofit retrofit=new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
            weatherService=retrofit.create(WeatherService.class);
        }
        return weatherService;
    }

    public interface WeatherService
{   @GET("weather?APPID="+key)
    Call<Model> getWeatherData(@Query("lat") double lat, @Query("lon") double lon);
    @GET("weather?APPID="+key)
    Call<Model> getWeatherDataUsingString(@Query("q") String str);
}
}
