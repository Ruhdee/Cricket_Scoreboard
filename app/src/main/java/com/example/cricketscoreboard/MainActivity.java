package com.example.cricketscoreboard;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
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

        Button buttoncustom = findViewById(R.id.custom);
        buttoncustom.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setElevation(0);
                    EditText t = findViewById(R.id.Overs);
                    t.setVisibility(View.VISIBLE);
                    t.setText("");
                    t = findViewById(R.id.Wickets);
                    t.setVisibility(View.INVISIBLE);
                    t.setText("");


                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setElevation(DpToPixel(4));


                }
                return true;
            }

        });
        Button button20 = findViewById(R.id.button20);
        button20.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setElevation(0);

                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setElevation(DpToPixel(4));
                    next(findViewById(R.id.button20));
                }
                return true;

            }
        });
        Button button50 = findViewById(R.id.button50);
        button50.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setElevation(0);

                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setElevation(DpToPixel(4));
                    next(findViewById(R.id.button50));
                }
                return true;

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        EditText t = findViewById(R.id.Overs);
        t.setVisibility(View.INVISIBLE);
        t.setText("");
        t = findViewById(R.id.Wickets);
        t.setVisibility(View.INVISIBLE);
        t.setText("");
    }

    public void next(View v) {
        Intent i = new Intent(this, ScoreActivity.class);
        String s = ((Button) v).getText().toString();
        i.putExtra("FIXED", s);
        startActivity(i);
    }


    static int a = 0, b = 0;

    public void overs(View v) {
        String s = ((EditText) v).getText().toString();
        if (!s.isEmpty()) {
            a = Integer.parseInt(s);
        } else {
            Toast.makeText(this, "Wrong Input", Toast.LENGTH_LONG).show();
        }
        if (a != 0) {
            EditText t = findViewById(R.id.Wickets);
            t.setVisibility(View.VISIBLE);
            v.setVisibility(View.INVISIBLE);
        } else {
            Toast.makeText(this, "Wrong Input", Toast.LENGTH_LONG).show();
        }


    }

    public void wick(View v) {

        String s = ((EditText) v).getText().toString();
        if (!s.isEmpty()) {
            b = Integer.parseInt(s);
        } else {
            Toast.makeText(this, "Wrong Input", Toast.LENGTH_LONG).show();
        }
        if (b != 0) {
            Intent i = new Intent(this, ScoreActivity.class);
            i.putExtra("FIXED", "No");
            i.putExtra("Custom1", a);
            i.putExtra("Custom2", b);
            startActivity(i);
        } else {
            Toast.makeText(this, "Wrong Input", Toast.LENGTH_LONG).show();
        }

    }


    public int DpToPixel(final float dp) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return Math.round(dp * scale + 0.5f);
    }
}