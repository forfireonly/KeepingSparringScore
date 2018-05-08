package com.example.annascott.keepingsparringscore;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {

    int competitorBlueScore;
    int competitorRedScore;
    int competitorBlueStrike;
    int competitorRedStrike;

    @BindView(R.id.score_blue_competitor)
    TextView competitorBlueScoreView;
    @BindView(R.id.score_red_competitor)
    TextView competitorRedScoreView;
    @BindView(R.id.strike_blue_competitor)
    TextView competitorBlueStrikeView;
    @BindView(R.id.strike_red_competitor)
    TextView competitorRedStrikeView;

    @BindView(R.id.name_blue_competitor)
    EditText competitorBlueName;
    @BindView(R.id.name_red_competitor)
    EditText competitorRedName;

    //@BindView(R.id.name_blue)
    TextView competitorBluePopUp;
    //@BindView(R.id.name_red)
    TextView competitorRedPopUp;

    @BindView(R.id.count_down)
    TextView tView;
    //@BindView(R.id.start)
    Button btnStart;
    //@BindView(R.id.pause)
    Button btnPause;
    // @BindView(R.id.resume)
    Button btnResume;
    //@BindView(R.id.cancel)
    Button btnCancel;


    //Declare a variable to hold count down timer's paused status
    boolean isPaused = false;
    //Declare a variable to hold count down timer's paused status
    boolean isCanceled = false;

    //Declare a variable to hold CountDownTimer remaining time
    long timeRemaining = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        initializeViews();
    }

    private void initializeViews() {

        // competitorBluePopUp = findViewById(R.id.name_blue);
        // competitorRedPopUp = findViewById(R.id.name_red);

        //Get reference of the XML layout's widgets
        //tView = (TextView) findViewById(R.id.count_down);

        btnStart = (Button) findViewById(R.id.start);
        btnPause = (Button) findViewById(R.id.pause);
        btnResume = (Button) findViewById(R.id.resume);
        btnCancel = (Button) findViewById(R.id.cancel);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save away the original text, so we still have it if the activity
        // needs to be killed while paused.
        //  outState.putString("my_text", timeRemaining);
        super.onSaveInstanceState(outState);
        outState.putLong("my_int", timeRemaining);
        outState.putInt("bluescore", competitorBlueScore);
        outState.putInt("redscore", competitorRedScore);
        outState.putInt("bluestrike", competitorBlueStrike);
        outState.putInt("redstrike", competitorRedStrike);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // restore saved values

        timeRemaining = savedInstanceState.getLong("my_int");
        tView.setText(String.valueOf(timeRemaining / 1000));

        competitorBlueScore = savedInstanceState.getInt("bluescore");
        competitorBlueScoreView.setText(String.valueOf(competitorBlueScore));
        competitorRedScore = savedInstanceState.getInt("redscore");
        competitorRedScoreView.setText(String.valueOf(competitorRedScore));
        competitorBlueStrike = savedInstanceState.getInt("bluestrike");
        competitorBlueStrikeView.setText(String.valueOf(competitorBlueStrike));
        competitorRedStrike = savedInstanceState.getInt("redstrike");
        competitorRedStrikeView.setText(String.valueOf(competitorRedStrike));
        new CountDownTimer(timeRemaining, 1000) {
            public void onTick(long millisUntilFinished) {
                //do something in every tick
                if (isPaused || isCanceled) {
                    //If the user request to cancel or paused the
                    //CountDownTimer we will cancel the current instance
                    cancel();
                } else {
                    //Display the remaining seconds to app interface
                    //1 second = 1000 milliseconds
                    tView.setText("" + millisUntilFinished / 1000);
                    //Put count down timer remaining time in a variable
                    timeRemaining = millisUntilFinished;
                }
            }

            public void onFinish() {
                //Do something when count down finished
                tView.setText(R.string._300);
                //Enable the start button
                btnStart.setEnabled(true);
                //Disable the pause, resume and cancel button
                btnPause.setEnabled(false);
                btnResume.setEnabled(false);
                btnCancel.setEnabled(false);
            }
        }.start();
    }

    //      @Override
    public void timerStart(View v) {

        isPaused = false;
        isCanceled = false;

        //Disable the start and pause button
        btnStart.setEnabled(false);
        btnResume.setEnabled(false);
        //Enabled the pause and cancel button
        btnPause.setEnabled(true);
        btnCancel.setEnabled(true);

        CountDownTimer timer;
        long millisInFuture = 300000; //300 seconds
        long countDownInterval = 1000; //1 second


        //Initialize a new CountDownTimer instance
        timer = new CountDownTimer(millisInFuture, countDownInterval) {
            public void onTick(long millisUntilFinished) {
                //do something in every tick
                if (isPaused || isCanceled) {
                    //If the user request to cancel or paused the
                    //CountDownTimer we will cancel the current instance
                    cancel();
                } else {
                    //Display the remaining seconds to app interface
                    //1 second = 1000 milliseconds
                    tView.setText("" + millisUntilFinished / 1000);
                    //Put count down timer remaining time in a variable
                    timeRemaining = millisUntilFinished;
                }
            }

            public void onFinish() {
                //Do something when count down finished
                tView.setText(R.string._300);

                //Enable the start button
                btnStart.setEnabled(true);
                //Disable the pause, resume and cancel button
                btnPause.setEnabled(false);
                btnResume.setEnabled(false);
                btnCancel.setEnabled(false);
            }
        }.start();
    }

    //      @Override
    public void timerPause(View v) {
        //When user request to pause the CountDownTimer
        isPaused = true;

        //Enable the resume and cancel button
        btnResume.setEnabled(true);
        btnCancel.setEnabled(true);
        //Disable the start and pause button
        btnStart.setEnabled(false);
        btnPause.setEnabled(false);
    }

    //    @Override
    public void timerResume(View v) {
        //Disable the start and resume button
        btnStart.setEnabled(false);
        btnResume.setEnabled(false);
        //Enable the pause and cancel button
        btnPause.setEnabled(true);
        btnCancel.setEnabled(true);

        //Specify the current state is not paused and canceled.
        isPaused = false;
        isCanceled = false;

        //Initialize a new CountDownTimer instance
        long millisInFuture = timeRemaining;
        long countDownInterval = 1000;
        new CountDownTimer(millisInFuture, countDownInterval) {
            public void onTick(long millisUntilFinished) {
                //Do something in every tick
                if (isPaused || isCanceled) {
                    //If user requested to pause or cancel the count down timer
                    cancel();
                } else {
                    tView.setText("" + millisUntilFinished / 1000);
                    //Put count down timer remaining time in a variable
                    timeRemaining = millisUntilFinished;
                }
            }

            public void onFinish() {
                //Do something when count down finished
                {
                    tView.setText(R.string._300);
                    if (competitorBlueScore > competitorRedScore) showWinnerBlue();
                    if (competitorRedScore > competitorBlueScore) showWinnerRed();
                    if (competitorRedScore == competitorBlueScore)
                        Toast.makeText(getApplicationContext(), R.string.next_point, Toast.LENGTH_LONG).show();
                }
                //Disable the pause, resume and cancel button
                btnPause.setEnabled(false);
                btnResume.setEnabled(false);
                btnCancel.setEnabled(false);
                //Enable the start button
                btnStart.setEnabled(true);
            }
        }.start();
    }

    //       @Override
    public void timerStop(View v) {
        //When user request to cancel the CountDownTimer
        isCanceled = true;

        //Disable the cancel, pause and resume button
        btnPause.setEnabled(false);
        btnResume.setEnabled(false);
        btnCancel.setEnabled(false);
        //Enable the start button
        btnStart.setEnabled(true);

        //Notify the user that CountDownTimer is canceled/stopped
        tView.setText(R.string._300);
    }

    public void blueScoreCountOne(View v) {
        competitorBlueScore = competitorBlueScore + 1;
        competitorBlueScoreView.setText(String.valueOf(competitorBlueScore));
        if (competitorBlueScore >= 5) {
            showWinnerBlue();
        }
    }

    public void blueScoreCountTwo(View v) {
        competitorBlueScore = competitorBlueScore + 2;
        competitorBlueScoreView.setText(String.valueOf(competitorBlueScore));
        if (competitorBlueScore >= 5) {
            showWinnerBlue();
        }
    }

    public void blueStrike(View v) {
        competitorBlueStrike = competitorBlueStrike + 1;
        competitorBlueStrikeView.setText(String.valueOf(competitorBlueStrike));
        if (competitorBlueStrike == 2) {
            competitorRedScore = competitorRedScore + 1;
            if (competitorRedScore >= 5) {
                showWinnerRed();
            }
        }
        competitorRedScoreView.setText(String.valueOf(competitorRedScore));
        if (competitorBlueStrike >= 3) {
            showWinnerRed();
        }
    }

    public void redScoreCountOne(View v) {
        competitorRedScore = competitorRedScore + 1;
        competitorRedScoreView.setText(String.valueOf(competitorRedScore));
        if (competitorRedScore >= 5) {
            showWinnerRed();
        }
    }

    public void redScoreCountTwo(View v) {
        competitorRedScore = competitorRedScore + 2;
        competitorRedScoreView.setText(String.valueOf(competitorRedScore));
        if (competitorRedScore >= 5) {
            showWinnerRed();
        }
    }

    public void redStrike(View view) {
        competitorRedStrike = competitorRedStrike + 1;
        competitorRedStrikeView.setText(String.valueOf(competitorRedStrike));
        if (competitorRedStrike == 2) {
            competitorBlueScore = competitorBlueScore + 1;
            if (competitorBlueScore >= 5) {
                showWinnerBlue();
            }
        }
        competitorBlueScoreView.setText(String.valueOf(competitorBlueScore));
        if (competitorRedStrike >= 3) {
            showWinnerBlue();
        }
    }

    public void showWinnerBlue() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.bluewinner);
        String name = competitorBlueName.getText().toString();
        TextView text = (TextView) dialog.findViewById(R.id.name_blue);
        text.setText(name);
        dialog.setTitle(R.string.winner);
        dialog.show();
        Button PopUpClose = (Button) dialog.findViewById(R.id.dialogButtonOK);
        PopUpClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), R.string.my_toast, Toast.LENGTH_LONG).show();

            }
        });
    }

    public void showWinnerRed() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.redwinner);
        String name = competitorRedName.getText().toString();
        TextView text = (TextView) dialog.findViewById(R.id.name_red);
        text.setText(name);
        dialog.setTitle(R.string.winner);
        dialog.show();
        Button PopUpClose = (Button) dialog.findViewById(R.id.dialogButtonOK);
        PopUpClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), R.string.my_toast, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void reset(View view) {
        competitorBlueScore = 0;
        competitorRedScore = 0;
        competitorBlueStrike = 0;
        competitorRedStrike = 0;
        competitorRedScoreView.setText(String.valueOf(competitorRedScore));
        competitorBlueScoreView.setText(String.valueOf(competitorBlueScore));
        competitorBlueStrikeView.setText(String.valueOf(competitorBlueStrike));
        competitorRedStrikeView.setText(String.valueOf(competitorRedStrike));
    }
}