package com.example.sodoko;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GridLayout sudokuGrid;
    private int[][] puzzle = new int[9][9];
    private SudokuDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sudokuGrid = findViewById(R.id.sudokuGrid);
        db = SudokuDatabase.getInstance(this);

        loadOrCreateGame();
        drawGrid();
    }



    private void loadOrCreateGame() {
        SudokuEntity last = db.sudokuDao().loadLast();

        if (last != null) {
            puzzle = stringToGrid(last.grid);
        } else {
            SudokuGenerator generator = new SudokuGenerator();
            puzzle = generator.generatePuzzle(45); // 1درجه متوسط
            saveGame();
        }
    }

    private void saveGame() {
        SudokuEntity e = new SudokuEntity();
        e.grid = gridToString(puzzle);
        db.sudokuDao().insert(e);
    }



    private void drawGrid() {
        sudokuGrid.removeAllViews();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {

                EditText cell = new EditText(this);
                cell.setGravity(Gravity.CENTER);
                cell.setTextSize(18);

                GridLayout.LayoutParams p = new GridLayout.LayoutParams();
                p.width = 0;
                p.height = 0;
                p.rowSpec = GridLayout.spec(r, 1f);
                p.columnSpec = GridLayout.spec(c, 1f);
                cell.setLayoutParams(p);

                if (puzzle[r][c] != 0) {
                    cell.setText(String.valueOf(puzzle[r][c]));
                    cell.setEnabled(false);
                    cell.setBackgroundColor(Color.LTGRAY);
                } else {
                    cell.setInputType(InputType.TYPE_CLASS_NUMBER);
                    cell.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});

                    final int row = r;
                    final int col = c;

                    cell.setOnFocusChangeListener((v, hasFocus) -> {
                        if (!hasFocus) {
                            String t = cell.getText().toString();
                            if (!t.isEmpty()) {
                                int num = Integer.parseInt(t);
                                if (isValid(row, col, num)) {
                                    puzzle[row][col] = num;
                                    cell.setTextColor(Color.BLACK);
                                    saveGame();
                                } else {
                                    cell.setTextColor(Color.RED);
                                }
                            }
                        }
                    });
                }

                sudokuGrid.addView(cell);
            }
        }
    }



    private boolean isValid(int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (puzzle[row][i] == num && i != col) return false;
            if (puzzle[i][col] == num && i != row) return false;
        }

        int br = (row / 3) * 3;
        int bc = (col / 3) * 3;

        for (int r = br; r < br + 3; r++)
            for (int c = bc; c < bc + 3; c++)
                if (puzzle[r][c] == num && (r != row || c != col))
                    return false;

        return true;
    }



    private String gridToString(int[][] g) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : g)
            for (int v : row)
                sb.append(v);
        return sb.toString();
    }

    private int[][] stringToGrid(String s) {
        int[][] g = new int[9][9];
        for (int i = 0; i < 81; i++)
            g[i / 9][i % 9] = Character.getNumericValue(s.charAt(i));
        return g;
    }
}
