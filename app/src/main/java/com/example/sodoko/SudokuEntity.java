package com.example.sodoko;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sudoku")
public class SudokuEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;


    public String grid;
}
