package com.dos.md.afbcs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.dos.md.R;
import com.dos.md.data.bean.User;

import java.util.List;

/**
 * Created by DOS on 2016/5/24 0024.
 */
public class A_RefreshLoad extends AppCompatActivity   {

    SwipeToLoadLayout swipeToLoadLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.a_refreshload);

        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.swipe_target);

       /* swipeToLoadLayout.setOnRefreshListener(this);

        swipeToLoadLayout.setOnLoadMoreListener(this);*/


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // autoRefresh();
    }

    /*@Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(false);

            }
        });
    }*/

    private List<User> mStrings;
  /*  @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setLoadingMore(false);

    }*/

    private void autoRefresh() {
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
    }

}
