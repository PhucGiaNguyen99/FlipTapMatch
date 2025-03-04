package com.example.project1_pnguye68;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button  btnSmallGame, btnLargeGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSmallGame = findViewById(R.id.btnSmallGame);
        btnLargeGame = findViewById(R.id.btnLargeGame);

        btnSmallGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startGame(4, 3); }   // Start the 4x3 game

        });

        btnLargeGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startGame(5, 4); }   // Start the 5x4 game
        });
    }

    // Navigate from MainActivity (the home screen) to PlayGame (the game screen)
    private void startGame(int rows, int cols) {
        Intent intent = new Intent(this, PlayGame.class);
        intent.putExtra("ROWS", rows);
        intent.putExtra("COLS", cols);
        startActivity(intent);
    }
}