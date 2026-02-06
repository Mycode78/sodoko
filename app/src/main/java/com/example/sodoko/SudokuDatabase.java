package com.example.sodoko;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {SudokuEntity.class}, version = 1)
public abstract class SudokuDatabase extends RoomDatabase {

    private static SudokuDatabase INSTANCE;

    public abstract SudokuDao sudokuDao();

    public static synchronized SudokuDatabase getInstance(Context ctx) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    ctx.getApplicationContext(),
                    SudokuDatabase.class,
                    "sudoku_db"
            ).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
