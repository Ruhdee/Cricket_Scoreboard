package com.example.cricketscoreboard;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class ScoreActivity extends AppCompatActivity implements View.OnTouchListener {
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_score);
        setlistener();
        printscore();

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ScoreActivity A = new ScoreActivity();
        A.setlistener();
        A.initialize();
        A.start();

    }

    static String[] a = new String[999999]; //a is used for storing event for each ball. z is used for run out data on no ball.
    static int b, target = 999999, sc, wick, O, W, i = 0; /*b is balls. sc is score. wick is wickets fallen.
     O, W are used for 	input of overs and wickets respectively.
      n is used for data of runs on no ball, wide , run out.*/
    static boolean bl = true;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            v.setElevation(0);
            if (v == findViewById(R.id.buttonundo)) {
                a[i] = v.getContentDescription().toString();
            } else {
                a[i] = ((Button) v).getText().toString();
            }
            process();
            printscore();

        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            v.setElevation(DpToPixel(4));


        }
        return true;

    }

    @SuppressLint("ClickableViewAccessibility")
    public void setlistener() {
        Button button0 = (findViewById(R.id.button0));
        Button button1 = (findViewById(R.id.button1));
        Button button2 = (findViewById(R.id.button2));
        Button button3 = (findViewById(R.id.button3));
        Button button4 = (findViewById(R.id.button4));
        Button button6 = (findViewById(R.id.button6));
        Button buttonout = (findViewById(R.id.buttonout));
        Button buttonwide = (findViewById(R.id.buttonwide));
        Button buttonno = (findViewById(R.id.buttonno));
        ImageButton buttonundo = (findViewById(R.id.buttonundo));
        button0.setOnTouchListener(this);
        button1.setOnTouchListener(this);
        button2.setOnTouchListener(this);
        button3.setOnTouchListener(this);
        button4.setOnTouchListener(this);
        button6.setOnTouchListener(this);
        buttonout.setOnTouchListener(this);
        buttonwide.setOnTouchListener(this);
        buttonno.setOnTouchListener(this);
        buttonundo.setOnTouchListener(this);
    }

    public void initialize() {
        Intent i = getIntent();
        String s = i.getStringExtra("FIXED");

        assert s != null;
        if (s.equals("20 Overs")) {
            O = 20;
            W = 10;
        }
        if (s.equals("50 Overs")) {
            O = 50;
            W = 10;
            Log.d("ABCDEFG", "50 Overs");
        }
        if (s.equals("No")) {
            O = i.getIntExtra("Custom1", 20);
            W = i.getIntExtra("Custom2", 10);

        }
        target = 99999;//initialization
        bl = true;
        findViewById(R.id.textView7).setVisibility(View.INVISIBLE);
        findViewById(R.id.textView8).setVisibility(View.INVISIBLE);
        findViewById(R.id.textView12).setVisibility(View.INVISIBLE);
    }


    void start() {
        /*Input of overs and wickets. Also, defining lengths of arrays. O*6 is no of balls.
    Array lengths need to be no of balls*/
        i = 0;
        b = 0;
        sc = 0;
        wick = 0;

    }

    void process() {
        if ((b < O * 6) && (wick < W) && (sc < target)) {
            int convert;
            if ((Objects.equals(a[i], "0")) || (Objects.equals(a[i], "1")) || (Objects.equals(a[i], "2")) || (Objects.equals(a[i], "3")) || (Objects.equals(a[i], "4")) || (Objects.equals(a[i], "6"))) //if 0 to 6 runs are scored.
            {
                b++;
                convert = Integer.parseInt(a[i]);
                sc += convert; //-48 cuz of ASCII (converting char to int)

            } else if (Objects.equals(a[i], "Out")) //if Out
            {
                wick++;
                b++;

            } else if (Objects.equals(a[i], "Wide")) //if wide.
            {
                sc += 1;
            } else if (Objects.equals(a[i], "No Ball")) //if no ball.
            {
                sc += 1;
            } else if (Objects.equals(a[i], "Undo"))//This is for undo.
                undo();

        }
        i++;
        if ((b >= O * 6) || (wick >= W) || (sc >= target))
            end(bl);
    }

    public void printscore() {
        String f = String.valueOf(sc);
        ((TextView) findViewById(R.id.score)).setText(f);
        f = String.valueOf(b / 6);
        String g = ".";
        String h = String.valueOf(b % 6);
        String j = f + g + h;
        ((TextView) findViewById(R.id.overs)).setText(j);
        f = String.valueOf(wick);
        ((TextView) findViewById(R.id.wickets)).setText(f);
        if (!bl) {
            findViewById(R.id.textView7).setVisibility(View.VISIBLE);
            findViewById(R.id.textView8).setVisibility(View.VISIBLE);
            findViewById(R.id.textView12).setVisibility(View.VISIBLE);
            f = String.valueOf(target);
            ((TextView) findViewById(R.id.target)).setText(f);
            f = String.valueOf(Math.max(target - sc, 0));
            ((TextView) findViewById(R.id.towin)).setText(f);
            f = String.valueOf(O * 6 - b);
            ((TextView) findViewById(R.id.ballsleft)).setText(f);
        }
    }

    void undo() {
        if (i >= 1)//if i=0, no inputs have been made yet.
        {
            int convert;
            --i;
            //--i to bring arrays to previous ball.
            if ((Objects.equals(a[i], "0")) || (Objects.equals(a[i], "1")) || (Objects.equals(a[i], "2")) || (Objects.equals(a[i], "3")) || (Objects.equals(a[i], "4")) || (Objects.equals(a[i], "6")))//for 0 to 6 runs
            {
                b--;
                convert = Integer.parseInt(a[i]);
                sc -= convert; //-48 cuz of ASCII (converting char to int)
            } else if (Objects.equals(a[i], "Out")) //for wicket
            {
                wick--;
                b--;
            } else if (Objects.equals(a[i], "Wide")) //for wide
            {
                sc -= 1; //n[i-1]+1 because wide ball extra run is also to be subtracted.
            } else if (Objects.equals(a[i], "No Ball")) //for no ball
            {
                sc -= 1; //n[i-1]+1 because no ball extra run is also to be subtracted.

            }
            --i;//To go 2 elements back in arrays(Current  element is 'U' and previous element was to be undone.)
        } else {
            Toast.makeText(this, "Can't Undo", Toast.LENGTH_LONG).show();
            i--;//to bring arrays and loop back to previous element
        }
    }

    void end(boolean c) {
        if (c) {
            if (b == O * 6) {
                Toast.makeText(this, "Overs Finished, Innings Finished", Toast.LENGTH_LONG).show();
            } else if (wick == W) {
                Toast.makeText(this, "All Out, Innings Finished", Toast.LENGTH_LONG).show();

            }
            target = sc + 1;
            bl = false;
            ScoreActivity B = new ScoreActivity();
            B.start();
        }
        if (!c) {
            if (sc >= target) {
                Toast.makeText(this, "Team B won!", Toast.LENGTH_LONG).show();
            } else if (sc == target - 1) {
                Toast.makeText(this, "Super Over!", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(this, "Team A won!", Toast.LENGTH_LONG).show();
        }
    }

    public int DpToPixel(final float dp) {
        final float scale = this.getResources().getDisplayMetrics().density;

        return Math.round(dp * scale + 0.5f);
    }
}



