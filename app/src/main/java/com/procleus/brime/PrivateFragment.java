package com.procleus.brime;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Ujjwal on 07-07-2016.
 */
public class PrivateFragment extends Fragment {
    final boolean isEmptyPrivate = true;
    private View view;
    ImageView mImageView;	//reference to the ImageView
    int xDim, yDim;		//stores ImageView dimensions
    private bitmapCreate bitmap;
    SharedPreferences sharedPreferences = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setActionBarTitle("Private Notes");
        sharedPreferences = this.getActivity().getSharedPreferences("com.procleus.brime", Context.MODE_PRIVATE);
        Boolean bool = sharedPreferences.getBoolean("loggedin", false);
        if (bool == false) {
            // Alert Dialog
        } else {
            if(isEmptyPrivate == true){
                view = inflater.inflate(R.layout.empty_notes, container, false);
                mImageView = (ImageView)view.findViewById(R.id.empty_avatar);
                xDim=300;
                yDim=300;
                mImageView.setImageBitmap(bitmap.decodeSampledBitmapFromResource(getResources(), R.drawable.empty_buddy, xDim, yDim));
                return view;
            }
            else {
                return inflater.inflate(R.layout.private_fragment, container, false);
            }
        }
        return inflater.inflate(R.layout.private_fragment, container, false);
    }
}
