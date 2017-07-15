/**
 * @author Saad Hassan <hassan.saad.mail@gmail.com>
 * @author Suraj Rawat <suraj.raw120@gmail.com>
 * @author Ujjwal Bhardwaj <ujjwalb1996@gmail.com>
 *
 * @license AGPL-3.0
 *
 * This code is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License, version 3,
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 */

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

import com.procleus.brime.data.NotesDbHelperOld;
import com.procleus.brime.models.NotesModel;
import com.procleus.brime.R;
import com.procleus.brime.adapter.RecyclerAdapter;
import com.procleus.brime.utils.BitmapCreate;

import java.util.ArrayList;
import java.util.List;

public class PrivateFragment extends Fragment  {
    final boolean isEmptyPrivate = false;
    ImageView mImageView;	//reference to the ImageView
    int xDim, yDim;		//stores ImageView dimensions
    private BitmapCreate bitmap;
    SharedPreferences sharedPreferences = null;

    // == == Card View Variables = == = ==

    NotesDbHelperOld helpher;
    List<NotesModel> dbList;
    private View view;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //====== Card View === == = == =


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.private_fragment,container,false);
        ((MainActivity) getActivity()).setActionBarTitle("Private NotesDbHelperOld");
        ((MainActivity) getActivity()).showFloatingActionButton(true);
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
        return view;
    }

    @Override
    public void onResume() {
        initialiseList();
        super.onResume();
    }

    public void initialiseList(){
        helpher = new NotesDbHelperOld(getContext());
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
