package com.example.sodoko;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SudokuGenerator {

    private static final int SIZE = 9;
    private static final int BOX = 3;

    public int[][] generateSolvedGrid() {
        int[][] grid = new int[SIZE][SIZE];
        fill(grid);
        return grid;
    }

    private boolean fill(int[][] grid) {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (grid[r][c] == 0) {

                    List<Integer> nums = new ArrayList<>();
                    for (int i = 1; i <= 9; i++) nums.add(i);
                    Collections.shuffle(nums);

                    for (int n : nums) {
                        if (isValid(grid, r, c, n)) {
                            grid[r][c] = n;
                            if (fill(grid)) return true;
                            grid[r][c] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int[][] g, int r, int c, int n) {
        for (int i = 0; i < 9; i++)
            if (g[r][i] == n || g[i][c] == n) return false;

        int br = (r / 3) * 3;
        int bc = (c / 3) * 3;

        for (int i = br; i < br + 3; i++)
            for (int j = bc; j < bc + 3; j++)
                if (g[i][j] == n) return false;

        return true;
    }
}
