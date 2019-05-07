package com.example.smartpill;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.app.Activity.RESULT_OK;


public class medicinesFrag extends Fragment {
    public MedsListAdapter adapter;
    private MedsViewModel mMedsViewModel;

    public static final int MEDUP_ACTIVITY_REQUEST_CODE = 4;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_medicines,null);
    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_medicine);
        adapter = new MedsListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Get a new or existing ViewModel from the ViewModelProvider.
        mMedsViewModel = ViewModelProviders.of(this).get(MedsViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mMedsViewModel.getAllMeds().observe(this, new Observer<List<Meds>>() {
            @Override
            public void onChanged(@Nullable final List<Meds> meds) {
                // Update the cached copy of the words in the adapter.
                adapter.setMeds(meds);
            }
        });

        adapter.setOnItemClickListener(new MedsListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Meds meds = adapter.getMedsAtPosition(position);

                //Intent intent = new Intent(getActivity(), medupActivity.class);
                //intent.putExtra("position", position);
                //intent.putExtra("SID", meds.getSID());
                //intent.putExtra("medicine", meds.getMedicine());
                //intent.putExtra("boxNo", meds.getBoxNo());
                //startActivityForResult(intent, MEDUP_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MEDUP_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            //int position = data.getIntExtra("position", 0);
            //Integer update = data.getIntExtra("update", 0);
            //Word myWord = adapter.getWordAtPosition(position);
        }
    }
}
