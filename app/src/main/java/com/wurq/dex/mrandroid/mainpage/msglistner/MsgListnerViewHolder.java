package com.wurq.dex.mrandroid.mainpage.msglistner;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wurq.dex.mrandroid.R;

/**
 * Created by wurongqiu on 16/12/20.
 */
public class MsgListnerViewHolder  extends RecyclerView.ViewHolder
{

    TextView tv_num;
    TextView tv_msg;

    public MsgListnerViewHolder(View view)
    {
        super(view);
        tv_num = (TextView) view.findViewById(R.id.moblistner_num);
        tv_msg = (TextView) view.findViewById(R.id.moblistner_msg);
    }
}
