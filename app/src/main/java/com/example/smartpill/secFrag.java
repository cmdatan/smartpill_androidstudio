package com.example.smartpill;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;


/**
 * A simple {@link Fragment} subclass.
 */
public class secFrag extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sec, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //view.findViewById(R.id.next_frag).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.next_action));
        //view.findViewById(R.id.skip_intro).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.loginActivity));
        ((MainActivity)getActivity()).updateStatusBarColor("#FF00897B");
    }

}
