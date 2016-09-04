package com.dos.md.afbcs.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dos.md.R;
import com.dos.md.data.bean.User;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class SectionAdapter extends BaseSectionQuickAdapter<User> {

    public SectionAdapter(Context context, int layoutResId, int sectionHeadResId, List data) {
        super(context, layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, final User item) {
        helper.setText(R.id.header, item.header);
        helper.setOnClickListener(R.id.more, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, item.header + "more..", Toast.LENGTH_LONG).show();
            }
        }).setOnClickListener(R.id.blew, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).setOnClickListener(R.id.above, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                this.toString();
            }
        });
    }


    @Override
    protected void convert(BaseViewHolder helper, User item) {

        helper.setImageUrl(R.id.iv, "http://120.24.220.29/static/media/img/1463647481384.623047033624.jpg");
        helper.setText(R.id.tv, item.userName);
    }
}