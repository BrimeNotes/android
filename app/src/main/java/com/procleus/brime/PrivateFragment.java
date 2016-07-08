package com.procleus.brime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Utkarsh on 07-07-2016.
 */
public class PrivateFragment extends Fragment {
    final boolean isEmptyPrivate = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(isEmptyPrivate == true){
            return inflater.inflate(R.layout.empty_notes, container, false);
        }
        else {
            return inflater.inflate(R.layout.private_fragment, container, false);
        }
    }

}
