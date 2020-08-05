package com.xoi.navic;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class LightVehicle_fragment extends Fragment {

    View view;
    private ArrayList<String> v_numbers = new ArrayList<>();
    private ArrayList<String> lats = new ArrayList<>();
    private ArrayList<String> longs = new ArrayList<>();
    private ArrayList<String> accs = new ArrayList<>();

    TextView signal_txt;
    TextView chck_txt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.lightvehicle_fragment, container, false);

        signal_txt = (TextView)view.findViewById(R.id.signal_txt);

        signal_txt.setText("NORMAL");

        getVehicles();
        return view;
    }

    private void getVehicles() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbzt3pvUjUcyNEXbjiWrrf5MDuHZhDz_vMmEnaixrImxvBihwauC/exec?action=getVehicles",
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
            JSONArray jarray = jobj.getJSONArray("vehicles");


            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                String links_json = jo.getString("v_number");
                v_numbers.add(links_json);
                String title_json = jo.getString("lat");
                lats.add(title_json);
                String conduct_json = jo.getString("long");
                longs.add(conduct_json);
                String conduct1_json = jo.getString("acc");

                accs.add(conduct1_json);
            }
            checkAccidents();
        } catch (JSONException e) {
            Toast.makeText(getActivity(), ""+e, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void checkAccidents(){
        if(accs.get(1).equals("1")){
            signal_txt.setText("Accident!");
            new AlertDialog.Builder(getActivity())
                    .setTitle("Accident!")
                    .setMessage("Be careful while driving, accident has occured in the route you are travelling")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else
        {
            lats.removeAll(lats);
            longs.removeAll(longs);
            v_numbers.removeAll(v_numbers);
            accs.removeAll(accs);
            getVehicles();
            signal_txt.setText("NORMAL");
        }
    }

    private void checkVehicles(){
        double lt1 = Double.parseDouble(lats.get(0));
        double ll1 = Double.parseDouble(longs.get(0));
        float ltd1 = convertNmea(lt1);
        float lld1 = convertNmea(ll1);
        double lt2 = Double.parseDouble(lats.get(1));
        double ll2 = Double.parseDouble(longs.get(1));
        float ltd2 = convertNmea(lt2);
        float lld2 = convertNmea(ll2);
        float dist = getDistance((float)12.866069,(float)74.925056,(float)12.8662866,(float)74.9251384);
        chck_txt.setText(dist + " ");

    }

    private float convertNmea(double coor){

        int chck = (int)coor;
        int dd = chck/100;
        float mm = (float)(coor - (dd*100));
        float ltd1 = dd + (mm/60);
        return ltd1;
    }

    private float getDistance(float lt1, float ll1, float lt2, float ll2){
        float res = 0;
        if(lt1 == lt2 || ll1 == ll2){
            if(lt1 == lt2 && ll1 == ll2){
                return 0;
            }
        }
        else{
            float a = 90 - lt1;
            float b = 90 - lt2;
            float p = ll1 - ll2;
            double q = Math.acos(Math.cos(p)*Math.sin(a)*Math.sin(b)+Math.cos(a)*Math.cos(b));
            double d = (q * 6371 * 3.14159)/180;
            res = (float)d;
        }
        return res;
    }
}
