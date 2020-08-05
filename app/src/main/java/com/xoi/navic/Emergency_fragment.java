package com.xoi.navic;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Emergency_fragment extends Fragment {

    View view;
    Button fire, police, hospital, vet, accident, women;
    String action;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.emergency_fragment, container, false);

        fire = (Button)view.findViewById(R.id.fire_but);
        police = (Button)view.findViewById(R.id.crime_but);
        hospital = (Button)view.findViewById(R.id.hospital_but);
        vet = (Button)view.findViewById(R.id.vet_but);
        accident = (Button)view.findViewById(R.id.accident_but);
        women = (Button)view.findViewById(R.id.women_but);


        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action = "fire";
                putFeedback();
            }
        });

        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action = "police";
                putFeedback();
            }
        });

        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action = "hospital";
                putFeedback();
            }
        });

        vet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action = "vet";
                putFeedback();
            }
        });

        accident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action = "accident";
                putFeedback();
            }
        });

        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action = "women";
                putFeedback();
            }
        });



        return view;
    }

    private void putFeedback(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzHg1JnBlXBx4rHkg1ybD2Z077ndf4N8uyCJBP3vOzTjcfCO0w/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Success")) {
                            Toast.makeText(getActivity(), "Your complaint has been registered. Help will be on the way.", Toast.LENGTH_LONG).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                parmas.put("action", action);
                parmas.put("lat", "12.866349");
                parmas.put("long", "74.925095");
                return parmas;
            }
        };
        int socketTimeOut = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);
    }


}