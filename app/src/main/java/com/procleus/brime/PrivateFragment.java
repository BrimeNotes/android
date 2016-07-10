package com.procleus.brime;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
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
        final View v = inflater.inflate(R.layout.private_fragment,container,false);
        ((MainActivity) getActivity()).setActionBarTitle("Private Notes");
        sharedPreferences = this.getActivity().getSharedPreferences("com.procleus.brime", Context.MODE_PRIVATE);
        Boolean bool = sharedPreferences.getBoolean("loggedin", false);

        if (bool == false) {
            // Alert Dialog
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
            alertDialogBuilder.setMessage("OOPS... you are not logged in.\n\nPlease login to access this feature");
            alertDialogBuilder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Intent i = new Intent(v.getContext(), SigninActivity.class);
                    startActivity(i);
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();

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
