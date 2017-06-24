package com.procleus.brime.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.procleus.brime.data.NotesDbHelper;
import com.procleus.brime.models.NotesModel;
import com.procleus.brime.R;
import com.procleus.brime.adapter.RecyclerAdapter;
import com.procleus.brime.utils.BitmapCreate;

import java.util.ArrayList;
import java.util.List;

public class TrashFragment extends Fragment  {
    private View view;
    ImageView mImageView;	//reference to the ImageView
    int xDim, yDim;		//stores ImageView dimensions
    private BitmapCreate bitmap;
    SharedPreferences sharedPreferences = null;

    // == == Card View Variables = == = ==

    NotesDbHelper helpher;
    List<NotesModel> dbList;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //====== Card View === == = == =


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.trash_fragment,container,false);
        ((MainActivity) getActivity()).setActionBarTitle("Trash");
        view = inflater.inflate(R.layout.trash_fragment, container, false);
        initialiseList();

        return view;
    }

    @Override
    public void onResume() {
        initialiseList();
        super.onResume();
    }

    public void initialiseList(){
        helpher = new NotesDbHelper(getContext());
        dbList= new ArrayList<NotesModel>();
        dbList = helpher.getDataFromDB("both",1);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycleview_trash);

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
