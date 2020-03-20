package com.example.map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Display extends Fragment {
    Model obj;
    ImageView img;
    TextView ed1;

    Display(Model obj) {
        this.obj = obj;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.displaylayout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        img=getView().findViewById(R.id.image);
        ed1=getView().findViewById(R.id.editText);
        Button b=getView().findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MapsActivity.class));
            }
        });
        fillData();
    }

    public void fillData() {
        Weather w= obj.getWeather().get(0);
        String str="http://openweathermap.org/img/wn/"+w.getIcon()+"@2x.png";
         Picasso.get().load(str).into(img);
         ed1.setText("Country:"+obj.getSys().getCountry()+"\nLatitude:"+obj.getCoord().getLat()+"\nLongitude:"+obj.getCoord().getLon()+"\nWeather desc:"+obj.getWeather().get(0).getDescription());
        ed1.append("\nTemperature:"+obj.getMain().getTemp());
        ed1.append("\nPressure:"+obj.getMain().getPressure());
        ed1.append("\nHumidity:"+obj.getMain().getHumidity());
        ed1.append("\nWind speed:"+obj.getWind().getSpeed());
        ed1.append("\nTime zone:"+obj.getTimezone());
    }


}


