package com.xoi.navic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class Utility_fragment extends Fragment {

    View view;
    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<String> lats = new ArrayList<>();
    private ArrayList<String> longs = new ArrayList<>();
    TextView atruck, ftruck, gtruck;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.utility_fragment, container, false);

        atruck = (TextView)view.findViewById(R.id.atruck_txt);
        gtruck = (TextView)view.findViewById(R.id.gtruck_txt);
        ftruck = (TextView)view.findViewById(R.id.ftruck_txt);

        getUtils();

        return view;
    }

    private void getUtils() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbxE0XhSsMCs2xOWvijFJDcEw-DaD-ddpGCA98RQ2khDhTmIvUIH/exec?action=utilities",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);
    }

    private void parseItems(String jsonResposnce) {
        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("util");

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                String title_json = jo.getString("lat");
                lats.add(title_json);
                String conduct_json = jo.getString("long");
                longs.add(conduct_json);
            }
            setText();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setText(){
        atruck.setText(convertNmea(Double.parseDouble(lats.get(0))) + " , " + convertNmea(Double.parseDouble(longs.get(0))) );
        ftruck.setText(convertNmea(Double.parseDouble(lats.get(1))) + " , " + convertNmea(Double.parseDouble(longs.get(1))));
        gtruck.setText(convertNmea(Double.parseDouble(lats.get(2))) + " , " + convertNmea(Double.parseDouble(longs.get(2))));
    }

    private float convertNmea(double coor){

        int chck = (int)coor;
        int dd = chck/100;
        float mm = (float)(coor - (dd*100));
        float ltd1 = dd + (mm/60);
        return ltd1;
    }
}
