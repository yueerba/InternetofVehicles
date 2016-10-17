package com.yueerba.internetofvehicles.internetofvehicles.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yueerba.internetofvehicles.internetofvehicles.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicPlayFragment extends Fragment {


    public MusicPlayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music_play, container, false);
    }

}
