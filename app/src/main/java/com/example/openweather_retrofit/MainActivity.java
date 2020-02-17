package com.example.openweather_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.openweather_retrofit.Interface.WeatherApis;
import com.example.openweather_retrofit.models.WResponse;
import com.example.openweather_retrofit.network.NetworkClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private TextView responseText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init() {
        editText = findViewById(R.id.city_name);
        button = findViewById(R.id.city_click);
        responseText = findViewById(R.id.response_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchWeatherDetails();
            }
        });
    }
    private void fetchWeatherDetails() {
        //Obtain an instance of Retrofit by calling the static method.
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        /*
        The main purpose of Retrofit is to create HTTP calls from the Java interface based on the annotation associated with each method. This is achieved by just passing the interface class as parameter to the create method
        */
        WeatherApis weatherAPIs = retrofit.create(WeatherApis.class);
        /*
        Invoke the method corresponding to the HTTP request which will return a Call object. This Call object will used to send the actual network request with the specified parameters
        */
        Call <WResponse>  wResponseCall= weatherAPIs.getWeatherByCity(editText.getText().toString(), "235bef5a99d6bc6193525182c409602c");
        wResponseCall.enqueue(new Callback<WResponse>() {
            @Override
            public void onResponse(Call<WResponse> call, Response<WResponse> response) {

                if (response.raw().code()==200){
                    WResponse wResponse = response.body();
                    responseText.setText("Temp: " + wResponse.getMain().getTemp() + "\n " +
                            "Humidity: " + wResponse.getMain().getHumidity() + "\n" +
                            "Pressure: " + wResponse.getMain().getPressure());

                }

            }

            @Override
            public void onFailure(Call<WResponse> call, Throwable t) {

            }
        });
        /*
        This is the line which actually sends a network request. Calling enqueue() executes a call asynchronously. It has two callback listeners which will invoked on the main thread
        */




}
}
