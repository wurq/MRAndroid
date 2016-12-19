package com.wurq.dex.mrandroid.mainpage.msg;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wurq.dex.mrandroid.R;

/**
 * Created by wurongqiu on 16/12/18.
 */
class MsgViewHolder extends RecyclerView.ViewHolder
{

     TextView tv_num;
     TextView tv_msg;

    public MsgViewHolder(View view)
    {
        super(view);
        tv_num = (TextView) view.findViewById(R.id.mob_num);
        tv_msg = (TextView) view.findViewById(R.id.mob_msg);
    }
}
