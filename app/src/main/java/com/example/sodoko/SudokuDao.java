package com.example.sodoko;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SudokuDao {

    @Insert
    void insert(SudokuEntity sudoku);

    @Query("SELECT * FROM sudoku ORDER BY id DESC LIMIT 1")
    SudokuEntity loadLast();
}
