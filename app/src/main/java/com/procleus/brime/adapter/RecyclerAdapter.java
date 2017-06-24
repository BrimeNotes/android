package com.procleus.brime.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.procleus.brime.ui.EditNotesActivity;
import com.procleus.brime.R;
import com.procleus.brime.data.NotesDbHelper;
import com.procleus.brime.models.NotesModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>  {
    static List<NotesModel> dbList;
    static Context context;
    public RecyclerAdapter(Context context, List<NotesModel> dbList ){
        this.dbList = new ArrayList<NotesModel>();
        this.context = context;
        this.dbList = dbList;

    }
    private int lastPosition = -1;

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_view_list, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }
    public void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
            animation.setDuration(1000);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

        holder.noteHead.setText(dbList.get(position).getTitle());
        holder.noteContent.setText(dbList.get(position).getDesc());
        String access = dbList.get(position).getAccess_type();
        Log.d("Access Value" ,access);
        if( access.equals("public")){
            holder.acces_specify_btn.setImageResource(R.drawable.ic_lock_open_24dp);
        }
        setAnimation(holder.itemView, position);
        Log.d("DBLIST ACCESS TYPE : ", dbList.get(position).getAccess_type().toString());
    }

    @Override
    public int getItemCount() {

        return dbList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView noteHead,noteContent;
        private ImageButton del_notes;
        private ImageButton acces_specify_btn;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            noteHead = (TextView) itemLayoutView
                    .findViewById(R.id.note_head);
            noteContent = (TextView)itemLayoutView.findViewById(R.id.note_content);
            del_notes = (ImageButton) itemView.findViewById(R.id.deleteNotesBtn);
            acces_specify_btn = (ImageButton) itemView.findViewById(R.id.accessSpecifyBtn);
            itemLayoutView.setOnClickListener(this);

            del_notes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteNotes(getLayoutPosition(),v);
                }
            });
            acces_specify_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeAccess(getLayoutPosition(),v);
                }
            });

        }

        @Override
        public void onClick(View v) {
          //  Intent intent = new Intent(context,DetailsActivity.class);

         //   Bundle extras = new Bundle();
        //    extras.putInt("position",getAdapterPosition());
         //   intent.putExtras(extras);

            /*
            int i=getAdapterPosition();
            intent.putExtra("position", getAdapterPosition());*/
        //    context.startActivity(intent);
            Intent intent = new Intent(context,EditNotesActivity.class);
            Bundle extras = new Bundle();
            extras.putInt("pos", getAdapterPosition());
            extras.putInt("id", dbList.get(getAdapterPosition()).getId());
            extras.putString("access_type", dbList.get(getAdapterPosition()).getAccess_type());
            intent.putExtras(extras);
            context.startActivity(intent);
           // Toast.makeText(RecyclerAdapter.context, "you have clicked Row " + getAdapterPosition(), Toast.LENGTH_LONG).show();
        }


    }
    public void changeAccess(int pos,View v){
        NotesDbHelper tn = new NotesDbHelper(context);
        String access = dbList.get(pos).getAccess_type();
        Log.d("Access Value" ,access);
        if( access.equals("public")){

            tn.accessChange(dbList.get(pos).getId(),"private");
            Snackbar snackbar = Snackbar
                    .make(v, "Moved To Private", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else {

            tn.accessChange(dbList.get(pos).getId(),"public");
            Snackbar snackbar = Snackbar
                    .make(v, "Moved To Public", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
      //  Toast.makeText(RecyclerAdapter.context, "Access "+ dbList.get(pos).getAccess_type()+" dbList. title "+dbList.get(pos).getTitle(),Toast.LENGTH_LONG).show();
        dbList.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, dbList.size());

    }
    public void deleteNotes(int pos,View v){
        Snackbar snackbar = Snackbar
                .make(v, "Note Moved to Trash", Snackbar.LENGTH_LONG)
                /*.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "NotesDbHelper Restored!", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                })
                */;

        snackbar.show();
        int id = dbList.get(pos).getId();
        NotesDbHelper tn = new NotesDbHelper(context);
        tn.moveToTrash(id);
        dbList.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, dbList.size());
      //  Toast.makeText(RecyclerAdapter.context, "Trash Val"+ dbList.get(pos).getIsDeleted(),Toast.LENGTH_LONG).show();

    }
}
