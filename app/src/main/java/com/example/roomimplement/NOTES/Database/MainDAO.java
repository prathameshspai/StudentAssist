package com.example.roomimplement.NOTES.Database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roomimplement.NOTES.models.Notes;

import java.util.List;

@Dao
public interface MainDAO {
    @Insert(onConflict = REPLACE)
    void insert (Notes notes);

    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Notes> getAll(); //To get all the data

    //To update items:
    @Query("UPDATE notes SET title = :title, notes = :notes WHERE ID = :id")
    void update(int id, String title, String notes);

    @Delete
    void delete(Notes notes);

    @Query("UPDATE notes SET pinned = :pin WHERE ID = :id")
    void pin(int id, boolean pin);

}
