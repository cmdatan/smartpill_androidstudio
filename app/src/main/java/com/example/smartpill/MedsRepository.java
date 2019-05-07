package com.example.smartpill;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

public class MedsRepository {
    
    public MedsRoomDatabase db;
    public MedsDao mMedsDao;
    public LiveData<List<Meds>> mAllMeds;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public MedsRepository(Application application) {
        db = MedsRoomDatabase.getDatabase(application);
        mMedsDao = db.medsDao();
        mAllMeds = mMedsDao.getAlphabetizedMeds();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Meds>> getAllMeds() {
        return mAllMeds;
    }

    public void deleteMeds(Meds meds)  {
        new deleteMedsAsyncTask(mMedsDao).execute(meds);
    }

    private static class deleteMedsAsyncTask extends AsyncTask<Meds, Void, Void> {
        private MedsDao mAsyncTaskDao;

        deleteMedsAsyncTask(MedsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Meds... params) {
            mAsyncTaskDao.deleteMeds(params[0]);
            return null;
        }
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insert (Meds meds) {
        new insertAsyncTask(mMedsDao).execute(meds);
    }

    private static class insertAsyncTask extends AsyncTask<Meds, Void, Void> {

        private MedsDao mAsyncTaskDao;

        insertAsyncTask(MedsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Meds... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void update(Meds meds)  {
        new updateMedsAsyncTask(mMedsDao).execute(meds);
    }

    private static class updateMedsAsyncTask extends AsyncTask<Meds, Void, Void> {
        private MedsDao mAsyncTaskDao;

        updateMedsAsyncTask(MedsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Meds... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }


}