package com.techexchange.mobileapps.assignment1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static String gameBoard[];
    private static int COLUMNS=3;
    private static Grid adapter;
    private static GridView gView;
    private Context context;
    private Bundle resumeState;
    private int whitespace;
    private static int DIMENSIONS = COLUMNS*COLUMNS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize, scramble, then display board
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gView = findViewById(R.id.gView);
        context = getApplicationContext();
        initialize();
        display(context);
        scrambleBoard();

        gView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //swap on a click and also check if solved
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                swapEmptySpace(getApplicationContext(), position);
                if (isSolved()) gView.setEnabled(false);
            }
        });
        //if game was saved, then restore state
        if (savedInstanceState != null) {
            gameBoard = savedInstanceState.getStringArray("board");
            whitespace = savedInstanceState.getInt("whitespaceIndex");
            resumeState = savedInstanceState;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //if paused, then resume
        if (resumeState != null) {
            display(context);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //store positions on board when paused
        outState.putStringArray("board", gameBoard);
        outState.putInt("whitespaceIndex", whitespace);
    }

    private void display(Context context) {
        ArrayList<ImageView> imageArray = new ArrayList<>();
        ImageView currentView;
        //if solved: display green board w/ toast message. else: display blue board
        if (isSolved()) {
            for (int i = 0; i < gameBoard.length; i++) {
                currentView = new ImageView(this);
                if (gameBoard[i].equals("0")) currentView.setBackgroundResource(R.drawable.f002);
                else if (gameBoard[i].equals("1")) currentView.setBackgroundResource(R.drawable.f003);
                else if (gameBoard[i].equals("2")) currentView.setBackgroundResource(R.drawable.f004);
                else if (gameBoard[i].equals("3")) currentView.setBackgroundResource(R.drawable.f005);
                else if (gameBoard[i].equals("4")) currentView.setBackgroundResource(R.drawable.f006);
                else if (gameBoard[i].equals("5")) currentView.setBackgroundResource(R.drawable.f007);
                else if (gameBoard[i].equals("6")) currentView.setBackgroundResource(R.drawable.f008);
                else if (gameBoard[i].equals("7")) currentView.setBackgroundResource(R.drawable.f009);
                else if (gameBoard[i].equals("8")) currentView.setBackgroundColor(0);
                imageArray.add(currentView);
                Toast.makeText(context, "YOU WON!", Toast.LENGTH_SHORT).show();
            }
        } else {
            for (int i = 0; i < gameBoard.length; i++) {
            currentView = new ImageView(this);
            if (gameBoard[i].equals("0")) currentView.setBackgroundResource(R.drawable.a002);
            else if (gameBoard[i].equals("1")) currentView.setBackgroundResource(R.drawable.a003);
            else if (gameBoard[i].equals("2")) currentView.setBackgroundResource(R.drawable.a004);
            else if (gameBoard[i].equals("3")) currentView.setBackgroundResource(R.drawable.a005);
            else if (gameBoard[i].equals("4")) currentView.setBackgroundResource(R.drawable.a006);
            else if (gameBoard[i].equals("5")) currentView.setBackgroundResource(R.drawable.a007);
            else if (gameBoard[i].equals("6")) currentView.setBackgroundResource(R.drawable.a008);
            else if (gameBoard[i].equals("7")) currentView.setBackgroundResource(R.drawable.a009);
            else if (gameBoard[i].equals("8")) currentView.setBackgroundColor(0);
            imageArray.add(currentView);
        }}
        adapter = new Grid(imageArray, 350, 350, context);
        gView.setAdapter(adapter);
    }

    private void initialize() {
        //initialize gameboard and whitespace index
        gameBoard = new String[DIMENSIONS];
        for (int i = 0; i < DIMENSIONS; i++) {
            gameBoard[i] = String.valueOf(i);
        } whitespace = 8;
    }
    private void swapEmptySpace(Context context, int position) {
        //if move of whitespace is valid, then swap position
        String temp;
        ArrayList<Integer> possibleMoves = validMoves(whitespace);
        if (possibleMoves.contains(position)) {
            temp = gameBoard[whitespace];
            gameBoard[whitespace] = gameBoard[position];
            gameBoard[position] = temp;
            whitespace = position;
        } display(context);
    }

    private ArrayList<Integer> validMoves(int position) {
        //determines valid moves from each board position and returns in int arraylist
        ArrayList<Integer> validArray = new ArrayList<>();
        switch (position) {
            case 0:
                validArray.add(position+1);
                validArray.add(position+COLUMNS);
                break;
            case 1:
                validArray.add(position-1);
                validArray.add(position+1);
                validArray.add(position+COLUMNS);
                break;
            case 2:
                validArray.add(position-1);
                validArray.add(position+COLUMNS);
                break;
            case 3:
                validArray.add(position-COLUMNS);
                validArray.add(position+1);
                validArray.add(position+COLUMNS);
                break;
            case 4:
                validArray.add(position-1);
                validArray.add(position+1);
                validArray.add(position+COLUMNS);
                validArray.add(position-COLUMNS);
                break;
            case 5:
                validArray.add(position-1);
                validArray.add(position+COLUMNS);
                validArray.add(position-COLUMNS);
                break;
            case 6:
                validArray.add(position-COLUMNS);
                validArray.add(position+1);
                break;
            case 7:
                validArray.add(position-1);
                validArray.add(position+1);
                validArray.add(position-COLUMNS);
                break;
            case 8:
                validArray.add(position-1);
                validArray.add(position-COLUMNS);
                break;
        } return validArray;
    }

    private void scrambleBoard() {
        //scrambles board by randomizing which possible move to make 120 times
        String temp;
        Random randomizer = new Random();
        for (int i = 120; i > 0; i--) {
            ArrayList<Integer> possibleMoves = validMoves(whitespace);
            int randomIndex = randomizer.nextInt(possibleMoves.size());
            int chosenSwap = possibleMoves.get(randomIndex);
            temp = gameBoard[chosenSwap];
            gameBoard[chosenSwap] = gameBoard[whitespace];
            gameBoard[whitespace] = temp;
            whitespace = chosenSwap;
        } display(context);
    }

    private static boolean isSolved(){
        //checks if gameboard is solved
        boolean solved = false;
        for (int i = 0; i < gameBoard.length; i++) {
            if (gameBoard[i].equals(String.valueOf(i))) {
                solved = true;
            } else {
                solved = false;
                break;
            }
        }return solved;
    }
}