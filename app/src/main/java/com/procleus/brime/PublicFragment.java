package com.procleus.brime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

/**
 * Created by Utkarsh on 07-07-2016.
 */
public class PublicFragment extends Fragment {
    final boolean isEmptyPublic = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setActionBarTitle("Public Notes");
        if(isEmptyPublic == true){
            return inflater.inflate(R.layout.empty_notes, container, false);
        }
        else {
            return inflater.inflate(R.layout.public_fragment, container, false);
        }
    }

}
