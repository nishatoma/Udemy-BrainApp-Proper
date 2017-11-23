package com.example.league95.brainapp2;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    //Answers
    List<Integer> answers = new ArrayList<>();
    // location of answer
    int locationOfAnswer;
    //score
    int userScore = 0;
    int numOfQuestions = 0;
    //Correct text view
    TextView correctTextView;
    TextView scoreTextView;
    TextView sumTextView;
    TextView timerTextView;
    ConstraintLayout constraintLayout;
    //Buttons
    Button button0, button1, button2, button3, playAgainButton, goButton;
    //Is user still playing?
    boolean stillPlaying = true;
    public void start(View v)
    {
        startButton.setVisibility(View.INVISIBLE);
        constraintLayout.setVisibility(View.VISIBLE);
        //We need this here because the timer should start
        //When we press Go, not on create!!!!
        playAgain(findViewById(R.id.playAgainButton));
    }

    public void generateQuestion()
    {
        //We're gonna have two letters that represent our
        //Two numbers
        Random random = new Random();
        int a = random.nextInt(21) + 1;
        int b = random.nextInt(21) + 1;

        //Update sum text view
        sumTextView.setText(a + " + " + b);
        //We need to define
        locationOfAnswer = random.nextInt(4);
        //To avoid generating 'wrong' answer that matches the
        //Correct one,
        int incorrectAnswer;
        //Note that our array list keeps adding new answers
        //Each time we call this method. What we need to do
        //is clear the answers array
        answers.clear();
        //Loop thru each of the values for the buttons
        //And sign them either the correct answer or
        //Wrong answer
        for (int i = 0; i < 4; i++)
        {
            if (i == locationOfAnswer)
            {
                answers.add(a + b);
            } else
            {
                incorrectAnswer = random.nextInt(41);
                while (incorrectAnswer == a + b)
                {
                    // if thats the case simply try another
                    // Incorrect answer!
                    incorrectAnswer = random.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }

        //Now update the buttons according to their answer!
        //Update each using their ids
        button0.setText(String.valueOf(answers.get(0)));
        button1.setText(String.valueOf(answers.get(1)));
        button2.setText(String.valueOf(answers.get(2)));
        button3.setText(String.valueOf(answers.get(3)));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = (ConstraintLayout) findViewById(R.id.appLayout);
        //Our text views
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        correctTextView = (TextView) findViewById(R.id.correctTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        //At first we don't display correct or not
        correctTextView.setVisibility(View.INVISIBLE);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        //Our Go button
        startButton = (Button) findViewById(R.id.button);
        //Buttons
        //Create pur 4 buttons here
        button0 = (Button) findViewById(R.id.button2);
        button1 = (Button) findViewById(R.id.button3);
        button2 = (Button) findViewById(R.id.button4);
        button3 = (Button) findViewById(R.id.button5);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        goButton = (Button) findViewById(R.id.button);
        //Generate numbers
        //generateQuestion();
    }

    public void chooseAnswer(View view)
    {
        if (stillPlaying)
        {
            correctTextView.setVisibility(View.VISIBLE);
            //Using tags, we can tell which button was tapped!
            if (view.getTag().toString().equals(String.valueOf(locationOfAnswer)))
            {
                // Add 1 to users score
                userScore++;
                correctTextView.setText("Correct!");
            } else
            {
                correctTextView.setText("Wrong!");
            }
            numOfQuestions++;
            scoreTextView.setText(String.valueOf(userScore)+"/"+String.valueOf(numOfQuestions));
            //generate new numbers
            generateQuestion();
        }

    }

    public void playAgain(View view)
    {
        stillPlaying = true;
        //We also need to generate questions whenever we star a new game!
        generateQuestion();
        userScore = 0;
        numOfQuestions = 0;
        timerTextView.setText("30s");
        scoreTextView.setText("0/0");
        correctTextView.setText("");
        //playAgainButton.setEnabled(false);
        playAgainButton.setVisibility(View.INVISIBLE);
        //We need a count down timer
        new CountDownTimer(30100, 1000){
            @Override
            public void onTick(long millisUntilFinish) {
                timerTextView.setText(String.valueOf(millisUntilFinish/1000)+"s");
            }

            @Override
            public void onFinish() {
                correctTextView.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                correctTextView.setText("Score: "+ userScore + "/"+numOfQuestions);
                playAgainButton.setVisibility(View.VISIBLE);
                //cancel();
                stillPlaying = false;
            }
        }.start();
    }



}
