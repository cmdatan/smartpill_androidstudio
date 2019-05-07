package com.example.smartpill;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MedsViewModel extends AndroidViewModel {

    public MedsRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    public LiveData<List<Meds>> mAllMeds;

    public MedsViewModel(Application application) {
        super(application);
        mRepository = new MedsRepository(application);
        mAllMeds = mRepository.getAllMeds();
    }

    LiveData<List<Meds>> getAllMeds() { return mAllMeds; }

    public void insert(Meds meds) { mRepository.insert(meds); }

    public void deleteMeds(Meds meds) { mRepository.deleteMeds(meds); }

    public void update(Meds meds) { mRepository.update(meds); }
}
