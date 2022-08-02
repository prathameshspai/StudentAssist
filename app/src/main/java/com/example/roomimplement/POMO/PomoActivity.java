package com.example.roomimplement.POMO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.roomimplement.GPA.CgpaActivity;
import com.example.roomimplement.GPA.CgpaManActivity;
import com.example.roomimplement.GPA.MainActivity;
import com.example.roomimplement.R;
import com.example.roomimplement.SelectActivity;
import com.example.roomimplement.TaskActivity;

import java.util.Locale;

public class PomoActivity extends AppCompatActivity {

    MediaPlayer player;

    private static long START_TIME = 1500000;
    private TextView mTextViewCount;
    private EditText manual;
    private Button mButtonStart, mButtonReset, break_btn, task_btn;

    private CountDownTimer mcountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftinmillis;

    private long mEndTime;

     LinearLayout expand3;
     Button btn3,task;
     CardView card3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomo);

        mTextViewCount=findViewById(R.id.text_view_countdown);
        mButtonStart=findViewById(R.id.button_start_pause);
        mButtonReset=findViewById(R.id.button_reset);


        task=findViewById(R.id.task_mgr);
        expand3 = findViewById(R.id.expanded_lay3);
        btn3 = findViewById(R.id.but_q_exp_lay3);
        card3 = findViewById(R.id.faq_3);
        break_btn=findViewById(R.id.break_btn);

        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PomoActivity.this, TaskActivity.class);
                startActivity(intent);
            }
        });

        break_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    pauseTimer();
                    resetTimer();
                }
                catch (Exception e){

                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expand3.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(card3, new AutoTransition());
                    expand3.setVisibility(View.VISIBLE);
                    task.setVisibility(View.VISIBLE);
                    btn3.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                }else{
                    TransitionManager.beginDelayedTransition(card3, new AutoTransition());
                    expand3.setVisibility(View.GONE);
                    task.setVisibility(View.GONE);

                    btn3.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                }
            }
        });

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg));
        getSupportActionBar().setTitle(Html.fromHtml("<small>Pomodoro Timer</small>"));
        getSupportActionBar().setSubtitle(Html.fromHtml("<small>Stay focused with the Pomodoro technique</small>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        android.graphics.drawable.Drawable background = PomoActivity.this.getResources().getDrawable(R.drawable.bg);
        getWindow().setBackgroundDrawable(background);

        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTimerRunning){
                    pauseTimer();
                }
                else{
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }

    private void startTimer(){
        mEndTime = System.currentTimeMillis() + mTimeLeftinmillis;
        mcountDownTimer=new CountDownTimer(mTimeLeftinmillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftinmillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateButton();
                play();
            }
        }.start();

        mTimerRunning = true;
        updateButton();
    }

    private void pauseTimer(){
        mcountDownTimer.cancel();
        mTimerRunning = false;
        updateButton();
    }
    private void resetTimer(){
        mTimeLeftinmillis = START_TIME;
        updateCountDownText();
        updateButton();
    }

    private void updateCountDownText(){
        int minutes = (int) (mTimeLeftinmillis/1000)/60;
        int seconds = (int) (mTimeLeftinmillis/1000)%60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        mTextViewCount.setText(timeLeftFormatted);
    }


    private void updateButton(){
        if(mTimerRunning){
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStart.setText("Pause");
        }
        else{
            mButtonStart.setText("Start");

            if(mTimeLeftinmillis<1000){
                mButtonStart.setVisibility(View.INVISIBLE);
            }else{
                mButtonStart.setVisibility(View.VISIBLE);
            }

            if(mTimeLeftinmillis<START_TIME){
                mButtonReset.setVisibility(View.VISIBLE);
            }else{
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }









    public void play(){
        if(player==null){
            player=MediaPlayer.create(this,R.raw.beep);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }
        player.start();
    }

    public void stop(){
        stopPlayer();
    }

    private void stopPlayer(){
        if(player!=null){
            player.release();
            player=null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mTimeLeftinmillis=prefs.getLong("millisLeft", START_TIME);
        mTimerRunning=prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateButton();

        if(mTimerRunning){
            mEndTime=prefs.getLong("endTime",0);
            mTimeLeftinmillis=mEndTime-System.currentTimeMillis();

            if(mTimeLeftinmillis<0){
                mTimeLeftinmillis = 0;
                mTimerRunning=false;
                updateCountDownText();
                updateButton();
            }else{
                startTimer();
            }
        }

    }

    @Override
    protected void onStop(){
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("millisLeft", mTimeLeftinmillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime",mEndTime);

        editor.apply();

        stopPlayer();
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}