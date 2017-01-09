package com.example.bojan.projekatpma.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bojan.projekatpma.MapsActivity;
import com.example.bojan.projekatpma.R;

/**
 * Created by Bojan on 1/9/2017.
 */

public class MainMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        View mapBtn = (Button) findViewById(R.id.mapButton);
        View locationsBtn = (Button) findViewById(R.id.locationsButton);
        View categoriesBtn = (Button) findViewById(R.id.categoriesButton);
        View exitButton = (Button)findViewById(R.id.exitButton);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent map = new Intent(MainMenu.this, MapsActivity.class);
                startActivity(map);
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });

    }
}
