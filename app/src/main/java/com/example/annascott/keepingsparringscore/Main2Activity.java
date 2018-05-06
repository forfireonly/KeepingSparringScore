package com.example.annascott.keepingsparringscore;

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

public class Main2Activity extends AppCompatActivity {

    int competitorBlueScore;
    int competitorRedScore;
    int competitorBlueStrike;
    int competitorRedStrike;

    TextView competitorBlueScoreView;
    TextView competitorRedScoreView;
    TextView competitorBlueStrikeView;
    TextView competitorRedStrikeView;

    EditText competitorBlueName;
    EditText competitorRedName;

    TextView competitorBluePopUp;
    TextView competitorRedPopUp;

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

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        initializeViews();
    }

    private void initializeViews() {
        competitorBlueScoreView = findViewById(R.id.score_blue_competitor);
        competitorRedScoreView = findViewById(R.id.score_red_competitor);
        competitorBlueStrikeView = findViewById(R.id.strike_blue_competitor);
        competitorRedStrikeView = findViewById(R.id.strike_red_competitor);

        competitorBlueName = (EditText) findViewById(R.id.name_blue_competitor);
        competitorRedName = (EditText) findViewById(R.id.name_red_competitor);

        competitorBluePopUp = findViewById(R.id.name_blue);
        competitorRedPopUp = findViewById(R.id.name_red);

        //Get reference of the XML layout's widgets
        final TextView tView = (TextView) findViewById(R.id.count_down);
        final Button btnStart = (Button) findViewById(R.id.start);
        final Button btnPause = (Button) findViewById(R.id.pause);
        final Button btnResume = (Button) findViewById(R.id.resume);
        final Button btnCancel = (Button) findViewById(R.id.cancel);


        //Initially disabled the pause, resume and cancel button
        btnPause.setEnabled(false);
        btnResume.setEnabled(false);
        btnCancel.setEnabled(false);


        //Set a Click Listener for start button
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                        tView.setText(R.string.done);

                        //Enable the start button
                        btnStart.setEnabled(true);
                        //Disable the pause, resume and cancel button
                        btnPause.setEnabled(false);
                        btnResume.setEnabled(false);
                        btnCancel.setEnabled(false);
                    }
                }.start();
            }
        });


        //Set a Click Listener for pause button
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //When user request to pause the CountDownTimer
                isPaused = true;

                //Enable the resume and cancel button
                btnResume.setEnabled(true);
                btnCancel.setEnabled(true);
                //Disable the start and pause button
                btnStart.setEnabled(false);
                btnPause.setEnabled(false);
            }


        });

        //Set a Click Listener for resume button
        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                //Set a Click Listener for cancel/stop button
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                });
            }
        });

        //Set a Click Listener for cancel/stop button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

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