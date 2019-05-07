package com.example.smartpill;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */

@Database(entities = {Meds.class}, version = 2)
public abstract class MedsRoomDatabase extends RoomDatabase {

    public abstract MedsDao medsDao();

    private static MedsRoomDatabase INSTANCE;

    static MedsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MedsRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MedsRoomDatabase.class, "meds_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     */
    private static Callback sRoomDatabaseCallback = new Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final MedsDao mDao;

        // Initial data set
        private static String [] words = {"Paracetamol", "Cefalexin", "Amoxicillin", "Abacavir"};
        private static String [] boxNo = {"1", "2", "3", "4"};

        PopulateDbAsync(MedsRoomDatabase db) {
            mDao = db.medsDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // If we have no words, then create the initial list of words.

            //mDao.deleteAll();

            if (mDao.getAnyMeds().length < 1) {
                for (int i = 0; i <= 3; i++) {
                    Meds meds = new Meds(boxNo[i], words[i]);
                    mDao.insert(meds);
                }
            }

            return null;

        }
    }

}
