package com.example.bojan.projekatpma.splash;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bojan.projekatpma.MapsActivity;
import com.example.bojan.projekatpma.R;

/**
 * Created by Bojan on 1/6/2017.
 */

public class Splash extends Activity {
    ProgressBar progressBar;
    int progressStatus = 0;
    TextView textView1, textView2;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        textView2 = (TextView) findViewById(R.id.load_per);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            textView2.setText(progressStatus + "%");
                        }
                    });
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (progressStatus == 100) {
                    Intent i = new Intent(Splash.this, MapsActivity.class);
                    startActivity(i);
                }
            }
        }).start();
    }
}
