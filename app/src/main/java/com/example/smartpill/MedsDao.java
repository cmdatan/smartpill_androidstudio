package com.example.smartpill;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MedsDao {

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * from meds_table")
    LiveData<List<Meds>> getAlphabetizedMeds();


    @Query("DELETE FROM meds_table")
    void deleteAll();

    // We do not need a conflict strategy, because the word is our primary key, and you cannot
    // add two items with the same primary key to the database. If the table has more than one
    // column, you can use @Insert(onConflict = OnConflictStrategy.REPLACE) to update a row.
    @Insert
    void insert(Meds meds);

    @Delete
    void deleteMeds(Meds meds);

    @Update
    void update(Meds... meds);

    @Query("SELECT * from meds_table LIMIT 1")
    Meds[] getAnyMeds();
}
