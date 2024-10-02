package com.example.cricketscoreboard;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void next(View v) {
        Log.d("HELLOFROM", "next: ");
        Intent i = new Intent(this, ScoreActivity.class);
        String s = ((Button) v).getText().toString();
        i.putExtra("FIXED", s);
        startActivity(i);
    }

    public void custom(View v) {
        EditText t = findViewById(R.id.Overs);
        t.setVisibility(View.VISIBLE);
    }

    static int a = 0, b=0;

    public void overs(View v) {

        String s = ((EditText) v).getText().toString();
        a = Integer.parseInt(s);
        EditText t = findViewById(R.id.Wickets);
        t.setVisibility(View.VISIBLE);


    }

    public void wick(View v) {

        String s = ((EditText) v).getText().toString();
        b = Integer.parseInt(s);
        Intent i = new Intent(this, ScoreActivity.class);
        i.putExtra("FIXED", "No");
        i.putExtra("Custom1", a);
        i.putExtra("Custom2", b);
        startActivity(i);

    }

}