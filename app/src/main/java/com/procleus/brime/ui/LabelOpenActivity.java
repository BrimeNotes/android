/**
 * @author Swastik Binjola <swastik.binjola2561@gmail.com>
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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.procleus.brime.R;
import com.procleus.brime.adapter.RecyclerAdapter;
import com.procleus.brime.data.NotesDbHelperOld;
import com.procleus.brime.models.NotesModel;

import java.util.ArrayList;
import java.util.List;

public class LabelOpenActivity extends AppCompatActivity {


    NotesDbHelperOld helpher;
    List<NotesModel> dbList;
    private View view;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label__open);
        getSupportActionBar().hide();

        Bundle mainData = getIntent().getExtras();
        String label = mainData.getString("label");
        initialiseList(label);

    }


    public void initialiseList(String label) {

        helpher = new NotesDbHelperOld(this);
        dbList = new ArrayList<NotesModel>();
        dbList = helpher.getDataFromDBWithLabel(label);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview_label);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecyclerAdapter(this, dbList);
        DefaultItemAnimator anim = new DefaultItemAnimator();
        anim.setAddDuration(500);
        anim.setRemoveDuration(700);
        mRecyclerView.setItemAnimator(anim);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecyclerAdapter(this, dbList);
        DefaultItemAnimator anim = new DefaultItemAnimator();
        anim.setAddDuration(500);
        anim.setRemoveDuration(700);
        mRecyclerView.setItemAnimator(anim);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
