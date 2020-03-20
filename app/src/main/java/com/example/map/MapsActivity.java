package com.example.map;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroupOverlay;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    JSONObject data = null;
    EditText srch;
    LatLng latLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        srch = findViewById(R.id.textview);
srch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId== EditorInfo.IME_ACTION_DONE) {
            getDataUsingString(srch.getText().toString());
            return true;
        }
        return false;
    }
});

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latng) {
                latLng = latng;
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title("your marked location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                getData(latng.latitude,latng.longitude);
            }
        });
    }

    private void getData(double lat, double lon) {
        Call<Model> list = RetroDataRetrieveAPI.getService().getWeatherData(lat, lon);
        list.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Model weatherData = response.body();
                Toast.makeText(MapsActivity.this, "success", Toast.LENGTH_LONG).show();
                Fragment fg=new Display(weatherData);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,fg).commit();
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "error", Toast.LENGTH_LONG).show();
                Log.d("error", t.getMessage());

            }
        });
    }

    private void getDataUsingString(String locQuery) {
        Call<Model> list = RetroDataRetrieveAPI.getService().getWeatherDataUsingString(locQuery);
        list.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Model weatherData = response.body();
                Toast.makeText(MapsActivity.this, "success", Toast.LENGTH_LONG).show();

                Fragment fg=new Display(weatherData);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,fg).commit();

            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "error", Toast.LENGTH_LONG).show();
                Log.d("error", t.getMessage());

            }
        });
    }


}

