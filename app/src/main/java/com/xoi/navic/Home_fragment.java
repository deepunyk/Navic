package com.xoi.navic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home_fragment extends Fragment {

    View view;
    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<String> v_numbers = new ArrayList<>();
    private ArrayList<String> lats = new ArrayList<>();
    private ArrayList<String> longs = new ArrayList<>();
    ImageView add_but;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);

        add_but = (ImageView)view.findViewById(R.id.add_but);

        add_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                add_but.animate().alpha(0f).setDuration(100);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        add_but.animate().alpha(1f).setDuration(100);
                        Toast.makeText(getActivity(), "Please try again later", Toast.LENGTH_SHORT).show();
                    }
                }, 100);
            }
        });

        return view;
    }
}
