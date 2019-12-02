package com.placetracker.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.placetracker.R;

import androidx.fragment.app.Fragment;

public class LastSelfie extends Fragment {
    public LastSelfie() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.last_selfie, container, false);
    }
}
