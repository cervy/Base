package com.dos.md.afbcs;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dos.md.A_Base;
import com.dos.md.A_Splash;
import com.dos.md.R;
import com.dos.md.data.bean.User;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.MultiItemCommonAdapter;
import com.zhy.base.adapter.recyclerview.MultiItemTypeSupport;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/*DOS
*  */
public class A_Main extends A_Base {
    // private boolean refreshing, loadingMore;
    private RecyclerView recyclerView;
    private List<User> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addView(R.layout.activity_main);
        rightMenu("Main", new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                final int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                switch (id) {
                    case R.id.send:
                    case R.id.gallery:
                    case R.id.Import:
                    case R.id.share:
                    case R.id.tools:
                    case R.id.slideshow:
                        showToast("setting");
                        break;
                }
                return true;

            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mList = new ArrayList<>();
        User u;
        for (int i = 0; i < 25; i++) {
            u = new User("dkjfdjf" + String.valueOf(i), "www." + String.valueOf(i), "id" + String.valueOf(i), i % 3 == 0 ? i : 0);
            if (i % 3 == 0) u.type = i;// TODO: 2016/4/25 0025 根据条件定制bean
            mList.add(u);
        }

        adapter = new MultiItemCommonAdapter<User>(this, mList, new MultiItemTypeSupport<User>() {

            @Override
            public int getItemViewType(int postion, User msg) {//todo:通过初始化bean传入的item类型条件
                if (msg.type % 3 == 0)
                    return A_Splash.THOUSNADANDHALF;//传到getItemViewType

                return 1;
            }

            @Override
            public int getLayoutId(int itemViewType) {
                if (itemViewType == A_Splash.THOUSNADANDHALF)
                    return R.layout.textitem;

                return R.layout.multitextitem;
            }


        }) {

            @Override
            public void convert(ViewHolder holder, User user) {
                switch (holder.getLayoutId()) {// TODO: 2016/4/25 0025 holder.getLayoutId() 或user相关条件设值
                    case R.layout.textitem:
                        holder.setText(R.id.textt, String.valueOf(user.type));

                        break;
                    case R.layout.multitextitem:
                        holder.setText(R.id.multiimage, user.userName + user.userId + user.userUrl + String.valueOf(user.type));

                        break;
                }
            }


        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(new OnItemClickListener<User>() {// TODO:  user.type or position不同（类型或位置）item做不同监听

            @Override
            public void onItemClick(ViewGroup parent, View view, User user, int position) {
                showToast(R.string.app_name);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, User user, int position) {
                showToast(R.string.action_settings);

                return true;// TODO: 2016/4/25 0025 允许长按否
            }
        });
        recyclerView.setAdapter(adapter);
       
        /*recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();                //lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 4 && dy > 0) {
                    if (loadingMore) return;
                    loadPage();//这里多线程也要手动控制isLoadingMore
                    loadingMore = false;
                }
            }
    });*/

        /*(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout).setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh () {
            if (refreshing)
                return;

            loadPage();
            refreshing = false;
        }
    }

    );*/
    }

    MultiItemCommonAdapter<User> adapter;

    private void loadPage() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }


}
