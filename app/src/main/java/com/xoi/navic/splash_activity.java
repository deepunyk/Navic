package com.xoi.navic;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;

import org.w3c.dom.Text;

public class splash_activity extends AppCompatActivity {

    ImageView imgview;
    int i = 0;
    TextView splash_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);

        imgview = (ImageView)findViewById(R.id.imageView2);
        splash_txt = (TextView)findViewById(R.id.splash_txt);

        splash_txt.animate().alpha(1f).setDuration(1500);

        final int []imageArray={R.drawable.splash1,R.drawable.splash2,R.drawable.splash4};

        StatusBarUtil.setColor(splash_activity.this,getColor(R.color.colorPrimary));

        final Handler handler1 = new Handler();
        Runnable runnable = new Runnable() {
            int i=0;
            public void run() {
                imgview.setImageResource(imageArray[i]);
                imgview.animate().rotationBy(360f).setDuration(200);
                i++;
                if(i>imageArray.length-1)
                {
                    i=0;
                }
                handler1.postDelayed(this, 600);
            }
        };

        handler1.postDelayed(runnable, 10);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splash_ac = new Intent(splash_activity.this, MainActivity.class);
                startActivity(splash_ac);
                finish();
            }
        }, 3500);
    }
}
