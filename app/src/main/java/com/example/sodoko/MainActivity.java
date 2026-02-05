package com.example.sodoko;



package com.example.sudokugame;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GridLayout sudokuGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sudokuGrid = findViewById(R.id.sudokuGrid);

        SudokuGenerator generator = new SudokuGenerator();
        int[][] grid = generator.generateSolvedGrid();

        drawGrid(grid);
    }

    private void drawGrid(int[][] grid) {
        sudokuGrid.removeAllViews();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {

                TextView tv = new TextView(this);
                tv.setText(String.valueOf(grid[r][c]));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(18);
                tv.setBackgroundResource(android.R.drawable.alert_light_frame);

                GridLayout.LayoutParams p = new GridLayout.LayoutParams();
                p.width = 0;
                p.height = 0;
                p.rowSpec = GridLayout.spec(r, 1f);
                p.columnSpec = GridLayout.spec(c, 1f);

                tv.setLayoutParams(p);
                sudokuGrid.addView(tv);
            }
        }
    }
}
