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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayGame extends AppCompatActivity {
    private GridLayout gridLayout;
    private TextView tvMatches, tvMisses;
    private int[] images = {
            R.drawable.academy_brush_color_paint_pallete_icon,
            R.drawable.animal_marine_prawn_seafood_shrimp_icon,
            R.drawable.app_brand_brands_logo_logos_icon,
            R.drawable.brain_divide_inkcontober_sains_icon,
            R.drawable.cat_icon,
            R.drawable.christmas_tree_xmas_icon,
            R.drawable.dessert_donut_food_sweet_icon,
            R.drawable.eye_glasses_office_vision_icon,
            R.drawable.floor_laughing_on_rolling_the_icon,
            R.drawable.food_pizza_icon
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

    private void setupGame(int rows, int cols) {
        int numCards = rows * cols;
        gameImages = new int[numCards];

        System.arraycopy(images, 0, gameImages, 0, totalPairs);
        System.arraycopy(images, 0, gameImages, totalPairs, totalPairs);

        List<Integer> tempList = new ArrayList<>();
        for (int i : gameImages) tempList.add(i);
        Collections.shuffle(tempList);
        for (int i = 0; i < gameImages.length; i++) gameImages[i] = tempList.get(i);

        buttons = new ImageButton[numCards];
        for (int i = 0; i < numCards; i++) {
            final int index = i;
            buttons[i] = new ImageButton(this);
            buttons[i].setBackgroundResource(R.drawable.ic_launcher_background);
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
                // Show both cards for 1 second before making them disappear
                new Handler().postDelayed(() -> {
                    buttons[firstIndex].setVisibility(View.INVISIBLE);
                    buttons[secondIndex].setVisibility(View.INVISIBLE);
                }, 1000);

                matches++;
                tvMatches.setText("Matches: " + matches);
                if (matches == totalPairs) {
                    showGameOver();
                }
            } else {
                // If no match, flip them back after 1 second
                new Handler().postDelayed(() -> {
                    buttons[firstIndex].setBackgroundResource(R.drawable.ic_launcher_background);
                    buttons[secondIndex].setBackgroundResource(R.drawable.ic_launcher_background);
                }, 1000);

                // Increase the misses count
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
