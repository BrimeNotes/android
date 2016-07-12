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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Saad on 07-07-2016.
 */
public class PrivateFragment extends Fragment  {
    final boolean isEmptyPrivate = false;
    private View view;
    ImageView mImageView;	//reference to the ImageView
    int xDim, yDim;		//stores ImageView dimensions
    private bitmapCreate bitmap;
    SharedPreferences sharedPreferences = null;

    // == == Card View Variables = == = ==

    Notes helpher;
    List<NotesModel> dbList;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //====== Card View === == = == =


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.private_fragment,container,false);
        ((MainActivity) getActivity()).setActionBarTitle("Private Notes");
        sharedPreferences = this.getActivity().getSharedPreferences("com.procleus.brime", Context.MODE_PRIVATE);
        Boolean bool = sharedPreferences.getBoolean("loggedin", false);

        if (bool == true) {
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

                view = inflater.inflate(R.layout.private_fragment, container, false);
                initialiseList();

           }
        }
        return view;
    }

    @Override
    public void onResume() {
        initialiseList();
        super.onResume();
    }

    public void initialiseList(){
        helpher = new Notes(getContext());
        dbList= new ArrayList<NotesModel>();
        dbList = helpher.getDataFromDB("private",0);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycleview_private);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecyclerAdapter(getContext(),dbList);
        DefaultItemAnimator anim = new DefaultItemAnimator();
        anim.setAddDuration(500);
        anim.setRemoveDuration(700);
        mRecyclerView.setItemAnimator(anim);
        mRecyclerView.setAdapter(mAdapter);
    }
}
