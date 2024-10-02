package com.example.cricketscoreboard;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener {

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
        Intent i = getIntent();
        String s = i.getStringExtra("FIXED");

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
            O = i.getIntExtra("Custom1", 0);
            W = i.getIntExtra("Custom2", 0);

        }

        Button button0 = (findViewById(R.id.button0));
        Button button1 = (findViewById(R.id.button1));
        Button button2 = (findViewById(R.id.button2));
        Button button3 = (findViewById(R.id.button3));
        Button button4 = (findViewById(R.id.button4));
        Button button6 = (findViewById(R.id.button6));
        Button buttonout = (findViewById(R.id.buttonout));
        Button buttonwide = (findViewById(R.id.buttonwide));
        Button buttonno = (findViewById(R.id.buttonno));
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button6.setOnClickListener(this);
        buttonout.setOnClickListener(this);
        buttonwide.setOnClickListener(this);
        buttonno.setOnClickListener(this);

        ScoreActivity A = new ScoreActivity();
        target=999999999;//initialization
        bl=true;
        findViewById(R.id.textView7).setVisibility(View.INVISIBLE);
        findViewById(R.id.textView8).setVisibility(View.INVISIBLE);

        A.start();

    }

    static char[] a, z; //a is used for storing event for each ball. z is used for run out data on no ball.
    static int b, target = 999999999, sc, wick, O, W, i = 0; /*b is balls. sc is score. wick is wickets fallen.
     O, W are used for 	input of overs and wickets respectively.
      n is used for data of runs on no ball, wide , run out.*/
    static int[] n;
    static String str;
    static boolean bl = true;

    @Override
    public void onClick(View v) {

        str = ((Button) v).getText().toString();
        process();
        i++;
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
            f = String.valueOf(target - sc);
            ((TextView) findViewById(R.id.towin)).setText(f);
        }

    }


    void start() {
        /*Input of overs and wickets. Also, defining lengths of arrays. O*6 is no of balls.
    Array lengths need to be no of balls*/
        i = 0;
        b = 0;
        sc = 0;
        wick = 0;
        n = new int[O * 6];
        z = new char[O * 6];
        a = new char[O * 6];
    }

    void process() {
        if ((b < O * 6) && (wick < W) && (sc < target)) {
            a[i] = str.charAt(0);
            if ((a[i] == '0') || (a[i] == '1') || (a[i] == '2') || (a[i] == '3') || (a[i] == '4') || (a[i] == '5') || (a[i] == '6')) //if 0 to 6 runs are scored.
            {
                b++;
                sc += a[i] - 48; //-48 cuz of ASCII (converting char to int)

            } else if (a[i] == 'O') //if Out
            {
                wick++;
                b++;

            } else if (a[i] == 'W') //if wide.
            {
                sc += 1;
            } else if (a[i] == 'N') //if no ball.
            {
                sc += 1;
            } else if (a[i] == 'U')//This is for undo.
                undo();

        } else {
            end(bl);
        }
    }

    void undo() {
        if (i >= 1)//if i=0, no inputs have been made yet.
        {
            --i;
            //--i to bring arrays to previous ball.
            if ((a[i] == '0') || (a[i] == '1') || (a[i] == '2') || (a[i] == '3') || (a[i] == '4') || (a[i] == '5') || (a[i] == '6'))//for 0 to 6 runs
            {
                b--;
                sc -= a[i] - 48; //-48 cuz of ASCII (converting char to int)
            } else if (a[i] == 'O') //for wicket
            {
                wick--;
                b--;
            } else if (a[i] == 'W') //for wide
            {
                sc -= n[i] + 1; //n[i-1]+1 because wide ball extra run is also to be subtracted.
            } else if (a[i] == 'N') //for no ball
            {
                sc -= n[i] + 1; //n[i-1]+1 because no ball extra run is also to be subtracted.
                if ((z[i] == 'R') || (z[i] == 'r')) //if run out on no ball
                {
                    wick--;
                }
            } else if (a[i] == 'R') //for run out
            {
                sc -= n[i];
                b--;
                wick--;
            }
            --i;//To go 2 elements back in arrays(Current  element is 'U' and previous element was to be undone.)
        } else {
            Toast.makeText(this, "Can't Undo", Toast.LENGTH_LONG).show();
            i--;//to bring arrays and loop back to previous element
        }
    }

    public void und(View v) {
        str = "U";
        process();
        i++;
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
            f = String.valueOf(target - sc);
            ((TextView) findViewById(R.id.towin)).setText(f);
        }
    }

    void end(boolean c) {
        if (c) {
            if (b == O * 6) {
                Toast.makeText(this, "Overs Finished, Innings Finished", Toast.LENGTH_LONG).show();
            } else if (wick == W) {
                Toast.makeText(this, "All Out, Innings Finished", Toast.LENGTH_LONG).show();
                ;
            }
            findViewById(R.id.textView7).setVisibility(View.VISIBLE);
            findViewById(R.id.textView8).setVisibility(View.VISIBLE);
            target = sc + 1;
            String f = String.valueOf(target);
            ((TextView) findViewById(R.id.target)).setText(f);
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
}



