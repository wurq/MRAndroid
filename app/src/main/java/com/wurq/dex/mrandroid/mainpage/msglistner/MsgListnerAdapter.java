package com.wurq.dex.mrandroid.mainpage.msglistner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wurq.dex.mrandroid.R;

import java.util.List;

/**
 * Created by wurongqiu on 16/12/20.
 */
public class MsgListnerAdapter extends RecyclerView.Adapter<MsgListnerViewHolder> {
    //定义一个集合，接收从Activity中传递过来的数据和上下文
    private List<String> mList;
    private Context mContext;


    MsgListnerAdapter(Context context, List<String> list){
        this.mContext = context;
        this.mList = list;
    }


    @Override
    public MsgListnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MsgListnerViewHolder holder = new MsgListnerViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_msg_listner, parent,false));
        return holder;
    }


    @Override
    public void onBindViewHolder(MsgListnerViewHolder holder, int position) {
//        final MsgData.DataItem itemText = mList.get(position);
        holder.tv_num.setText(mList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return mList.size();
    }



}

