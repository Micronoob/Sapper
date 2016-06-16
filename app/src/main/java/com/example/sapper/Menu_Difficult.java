package com.example.sapper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu_Difficult extends AppCompatActivity implements View.OnClickListener {

    public static int dif;
    Button bEasy;
    Button bNormal;
    Button bHard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__difficult);

        bEasy = (Button) findViewById(R.id.bEasy);
        if (bEasy != null) {
            bEasy.setOnClickListener(this);
        }
        bNormal = (Button) findViewById(R.id.bNormal);
        if (bNormal != null) {
            bNormal.setOnClickListener(this);
        }
        bHard = (Button) findViewById(R.id.bHard);
        if (bHard != null) {
            bHard.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bEasy:
                dif = 1;
                startActivity(new Intent(this, Main_Field.class));
                break;
            case R.id.bNormal:
                dif = 2;
                startActivity(new Intent(this, Main_Field.class));
                break;
            case R.id.bHard:
                dif =3;
                startActivity(new Intent(this, Main_Field.class));
                break;
        }
    }

}
