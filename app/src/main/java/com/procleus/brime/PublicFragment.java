package com.procleus.brime;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Utkarsh on 07-07-2016.
 */
public class PublicFragment extends Fragment {
    final boolean isEmptyPublic = false;
    ImageView mImageView;    //reference to the ImageView
    int xDim, yDim;        //stores ImageView dimensions
    private View view;
    private bitmapCreate bitmap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.public_fragment,container,false);

        ((MainActivity) getActivity()).setActionBarTitle("Public Notes");
        if(isEmptyPublic == true){
            view = inflater.inflate(R.layout.empty_notes, container, false);
            mImageView = (ImageView)view.findViewById(R.id.empty_avatar);
            xDim=300;
            yDim=300;
            mImageView.setImageBitmap(bitmapCreate.decodeSampledBitmapFromResource(getResources(), R.drawable.empty_buddy, xDim, yDim));
            return view;
        }
        else {

            CustomObject c1 = new CustomObject();
            ArrayList<CustomObject> objects = new ArrayList<>();

/*Mudit see this */
      /*
      * if textNote in line 61 is recieving values perfectly
      *
      * */


            Notes tn = new Notes(getContext());
            List<TextNote> textNote = tn.getTextNoteByOwner(1);
            //Log.i("id",String.valueOf(textNote.get(2).id));
            Iterator itr = textNote.iterator();


            while(itr.hasNext())
            {
                TextNote tnote = (TextNote)itr.next();
                c1.add(tnote.title,tnote.note);
                Log.i("DAYAM",tnote.title+tnote.note);
                objects.add(c1);
            }
            CustomAdapter customAdapter = new CustomAdapter(getContext(),objects);
            ListView listView = (ListView)v.findViewById(R.id.listView);
            listView.setAdapter(customAdapter);
            return v;
        }



    }




    /*CUSTOM ADAPTER IMPLEMENTATION*/



    public class CustomObject {

        private String prop1;
        private String prop2;


        public void add(String prop1,String prop2)
        {
            this.prop1=prop1;
            this.prop2=prop2;

        }


        public String getProp1() {
            return prop1;
        }

        public String getProp2() {
            return prop2;
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

}
