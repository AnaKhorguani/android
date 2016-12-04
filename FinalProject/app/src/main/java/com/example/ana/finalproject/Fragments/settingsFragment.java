package com.example.ana.finalproject.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.example.ana.finalproject.R;

public class settingsFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.settings, container, false);
        ToggleButton statustoggle;
        statustoggle=(ToggleButton) rootView.findViewById(R.id.statustoggle);
        statustoggle.toggle();

        ToggleButton soundtoggle;
        soundtoggle=(ToggleButton) rootView.findViewById(R.id.soundtoggle);
        soundtoggle.toggle();

        return rootView;
    }
}