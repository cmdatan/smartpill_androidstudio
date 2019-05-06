package com.example.smartpill;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    public WordRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    public LiveData<List<Word>> mAllWords;

    public WordViewModel (Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    LiveData<List<Word>> getAllWords() { return mAllWords; }

    public List<Word> getWordsComplete() { return mRepository.getWordsComplete(); }

    public Word getWordDB(int sid) { return mRepository.getWordDB(sid); }

    public void insert(Word word) { mRepository.insert(word); }

    public void deleteWord(Word word) { mRepository.deleteWord(word); }

    public void update(Word word) { mRepository.update(word); }
}
