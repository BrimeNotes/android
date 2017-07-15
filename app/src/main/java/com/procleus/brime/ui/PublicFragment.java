/**
 * @author Mudit Gupta <mudit@jkgupta.com>
 * @author Saad Hassan <hassan.saad.mail@gmail.com>
 * @author Suraj Rawat <suraj.raw120@gmail.com>
 * @author Swastik Binjola <swastik.binjola2561@gmail.com>
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

public class PublicFragment extends Fragment {
    final boolean isEmptyPublic = false;
    ImageView mImageView;    //reference to the ImageView
    int xDim, yDim;        //stores ImageView dimensions
    View v;
    private View view;
    private BitmapCreate bitmap;

    // == == Card View Variables = == = ==

    NotesDbHelperOld helpher;
    List<NotesModel> dbList;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //====== Card View === == = == =

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       final View v = inflater.inflate(R.layout.public_fragment, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("Public NotesDbHelperOld");
        ((MainActivity) getActivity()).showFloatingActionButton(true);
        if(isEmptyPublic == true){
            view = inflater.inflate(R.layout.empty_notes, container, false);
            mImageView = (ImageView)view.findViewById(R.id.empty_avatar);
            xDim=300;
            yDim=300;
            mImageView.setImageBitmap(BitmapCreate.decodeSampledBitmapFromResource(getResources(), R.drawable.empty_buddy, xDim, yDim));

        }
        else {
            view = inflater.inflate(R.layout.public_fragment, container, false);
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
        dbList = helpher.getDataFromDB("public",0);


        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycleview_public);

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
/** ============================= Swastik Implementation ==========================
    public void fillList() {

        ArrayList<CustomObject> objects = new ArrayList<>();

         //Check this Swagstik

        NotesDbHelperOld tn = new NotesDbHelperOld(getContext());
        List<TextNote> textNote = tn.getTextNoteByOwner(1);
        //Log.i("id",String.valueOf(textNote.get(2).id));
        Iterator itr = textNote.iterator();


        while (itr.hasNext()) {
            TextNote tnote = (TextNote) itr.next();
            CustomObject c1 = new CustomObject();
            c1.add(tnote.title, tnote.note);
            objects.add(c1);

        }
        CustomAdapter customAdapter = new CustomAdapter(getContext(), objects);
        ListView listView = (ListView) v.findViewById(R.id.listView);
        listView.setAdapter(customAdapter);

    }


    //CUSTOM ADAPTER IMPLEMENTATION
    @Override
    public void onResume() {
        fillList();
        super.onResume();
    }


    public class CustomObject {

        private String prop1;
        private String prop2;


        public void add(String prop1,String prop2)
        {
            this.prop1=prop1;
            this.prop2=prop2;

        }


        public String getProp1() {
            return this.prop1;
        }

        public String getProp2() {
            return this.prop2;
        }
    }

    public class CustomAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private ArrayList<CustomObject> objects;

        public CustomAdapter(Context context, ArrayList<CustomObject> objects) {
            inflater = LayoutInflater.from(context);
            this.objects = objects;
        }

        public int getCount() {
            return objects.size();
        }

        public CustomObject getItem(int position) {
            return objects.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.notes_list, null);
                holder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
                holder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView1.setText(objects.get(position).getProp1());
            holder.textView2.setText(objects.get(position).getProp2());

            return convertView;
        }

        private class ViewHolder {
            TextView textView1;
            TextView textView2;
        }
    }
    **/

}
