package com.example.smartpill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class dashboardFrag extends Fragment {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_ACTIVITY_REQUEST_CODE = 2;
    public static final int POPUP_ACTIVITY_REQUEST_CODE = 3;

    public WordListAdapter adapter;

    private WordViewModel mWordViewModel;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, null);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        adapter = new WordListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Get a new or existing ViewModel from the ViewModelProvider.
        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(words);
            }
        });

        /*

        // Add the functionality to swipe items in the
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Word myWord = adapter.getWordAtPosition(position);
                        Toast.makeText(getContext(), "Deleting " +
                                myWord.getMedicine(), Toast.LENGTH_LONG).show();

                        // Delete the word
                        mWordViewModel.deleteWord(myWord);
                    }
                });

        helper.attachToRecyclerView(recyclerView);

        */

        adapter.setOnItemClickListener(new WordListAdapter.ClickListener()  {
            @Override
            public void onItemClick(View v, int position) {
                Word word = adapter.getWordAtPosition(position);

                Intent intent = new Intent(getActivity(), popupActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("SID", word.getSID());
                intent.putExtra("medicine", word.getMedicine());
                intent.putExtra("quantity", word.getQuantity());
                intent.putExtra("duration", word.getDuration());
                intent.putExtra("hour", word.getHour());
                intent.putExtra("minute", word.getMinute());
                intent.putExtra("mon", word.getMon());
                intent.putExtra("tues", word.getTue());
                intent.putExtra("wed", word.getWed());
                intent.putExtra("thurs", word.getThurs());
                intent.putExtra("fri", word.getFri());
                intent.putExtra("sat", word.getSat());
                intent.putExtra("sun", word.getSun());
                startActivityForResult(intent, POPUP_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == POPUP_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int position = data.getIntExtra("position", 0);
            Integer update = data.getIntExtra("update", 0);
            Word myWord = adapter.getWordAtPosition(position);

            if (update == 1) {
                Word word = adapter.getWordAtPosition(position);

                Intent intent = new Intent(getActivity(), addScheduleActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("SID", word.getSID());
                intent.putExtra("medicine", word.getMedicine());
                intent.putExtra("quantity", word.getQuantity());
                intent.putExtra("duration", word.getDuration());
                intent.putExtra("hour", word.getHour());
                intent.putExtra("minute", word.getMinute());
                intent.putExtra("mon", word.getMon());
                intent.putExtra("tues", word.getTue());
                intent.putExtra("wed", word.getWed());
                intent.putExtra("thurs", word.getThurs());
                intent.putExtra("fri", word.getFri());
                intent.putExtra("sat", word.getSat());
                intent.putExtra("sun", word.getSun());
                startActivityForResult(intent, UPDATE_ACTIVITY_REQUEST_CODE);

            } else {
                // Delete the word
                mWordViewModel.deleteWord(myWord);
                ((btMaster) getActivity().getApplication()).btSendAll();
            }
        }
        else if (requestCode == UPDATE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getIntExtra("SID", 0),
                    data.getStringExtra(addScheduleActivity.MEDICINE),
                    data.getStringExtra(addScheduleActivity.QUANTITY),
                    data.getIntExtra(addScheduleActivity.DURATION,0),
                    data.getIntExtra(addScheduleActivity.HOUR,0),
                    data.getIntExtra(addScheduleActivity.MINUTE,0),
                    data.getIntExtra(addScheduleActivity.MON,0),
                    data.getIntExtra(addScheduleActivity.TUES,0),
                    data.getIntExtra(addScheduleActivity.WEDNES,0),
                    data.getIntExtra(addScheduleActivity.THURS,0),
                    data.getIntExtra(addScheduleActivity.FRI,0),
                    data.getIntExtra(addScheduleActivity.SATUR,0),
                    data.getIntExtra(addScheduleActivity.SUN,0));
            if (word != null) {
                // Update the word
                mWordViewModel.update(word);
                ((btMaster) getActivity().getApplication()).btSendAll();
            }
        }
        //else {
        //    Toast.makeText(
        //            getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
        //}
    }
}
