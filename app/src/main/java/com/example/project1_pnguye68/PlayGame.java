package com.example.project1_pnguye68;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Collections;

public class PlayGame extends AppCompatActivity {
    private GridLayout gridLayout;
    private TextView tvMatches, tvMisses;
    private int[] images = {
            R.drawable.image1, R.drawable.image2, R.drawable.image3,
            R.drawable.image4, R.drawable.image5, R.drawable.image6
    };
    private int[] gameImages;
    private ImageButton[] buttons;
    private int firstCard, secondCard, firstIndex, secondIndex;
    private boolean isSecondCard = false;
    private int matches = 0, misses = 0;
    private int totalPairs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gridLayout = findViewById(R.id.gridLayout);
        tvMatches = findViewById(R.id.tvMatches);
        tvMisses = findViewById(R.id.tvMisses);

        int rows = getIntent().getIntExtra("ROWS", 4);
        int cols = getIntent().getIntExtra("COLS", 3);
        gridLayout.setColumnCount(cols);
        totalPairs = (rows * cols) / 2;

        setupGame(rows, cols);
    }

    private void setupGames(int rows, int cols) {
        int numCards = rows * cols;
        gameImages = new int[numCards];

        System.arraycopy(images, 0, gameImages, 0, totalPairs);
        System.arraycopy(images, 0, gameImages, totalPairs, totalPairs);

        Collections.shuffle(Arrays.asList(gameImages));

        buttons = new ImageButton[numCards];
        for (int i = 0; i < numCards; i++) {
            final int index = i;
            buttons[i] = new ImageButton(this);
            buttons[i].setBackgroundResource(R.drawable.card_back);
            buttons[i].setOnClickListener(v -> flipCard(index));
            gridLayout.addView(buttons[i]);
        }
    }

    private void flipCard(int index) {
        buttons[index].setBackgroundResource(gameImages[index]);
        // If not yet flipped the second card
        if (!isSecondCard) {
            firstCard = gameImages[index];
            firstIndex = index;
            isSecondCard = true;
        }
        // If already flipped the second card
        else {
            secondCard = gameImages[index];
            secondIndex = index;
            if (firstCard == secondCard) {
                buttons[firstCard].setVisibility(View.INVISIBLE);
                buttons[secondIndex].setVisibility(View.INVISIBLE);
                matches++;
                tvMatches.setText("Matches: " + matches);
                if (matches == totalPairs) {
                    showGameOver();
                }
            } else {
                new Handler().postDelayed(() -> {
                    buttons[firstIndex].setBackgroundResource(R.drawable.card_back);
                    buttons[secondIndex].setBackgroundResource(R.drawable.card_back);
                }, 1000);
                misses++;
                tvMisses.setText("Misses: " + misses);
            }
            isSecondCard = false;
        }
    }

    private void showGameOver() {
        new AlertDialog.Builder(this)
                .setTitle("Game Over!")
                .setMessage("Play Again?")
                .setPositiveButton("Yes", (dialog, which) -> recreate())
                .setNegativeButton("No", (dialog, which) -> finish())
                .show();
    }
}
