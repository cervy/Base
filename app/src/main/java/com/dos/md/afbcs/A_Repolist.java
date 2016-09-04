package com.dos.md.afbcs;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dos.md.A_Base;
import com.dos.md.R;
import com.dos.md.data.Http;
import com.dos.md.data.bean.Repo;

import java.util.List;

import rx.Subscriber;

/**
 * Created by DOS on 026/26/3/2016.
 */

public class A_Repolist extends A_Base {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addView(R.layout.activity_repo_list);
        mRvList = (RecyclerView) findViewById(R.id.repos_rv_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvList.setLayoutManager(manager);


        loadData();


    }


    List<Repo> data;
    RecyclerView mRvList;


    private void loadData() {
        Http.getHttp().getRepos(new Subscriber<List<Repo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Repo> repos) {
                data = repos;
//                mRvList.setAdapter(adapter);
            }
        }, "bird1015");
    }
        /*Http.getHttp().getRepos("bird1015").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ArrayList<Repo>>() {
            @Override
            public void onNext(ArrayList<Repo> repos) {//只有onNext方法返回了数据
                showLoading(false);
                data = repos;
                mRvList.setAdapter(new StationListAdapter(mRvList, repos, R.layout.item_repo));
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                showLoading(false);
            }
        });
    }*/





}
