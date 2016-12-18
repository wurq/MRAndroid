package com.wurq.dex.mrandroid.mainpage.msg;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wurq.dex.mrandroid.R;

import java.util.List;

/**
 * Created by wurongqiu on 16/12/17.
 */
public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MsgViewHolder> {
    //定义一个集合，接收从Activity中传递过来的数据和上下文
    private List<String> mList;
    private Context mContext;


    MsgAdapter(Context context, List<String> list){
        this.mContext = context;
        this.mList = list;
    }


    @Override
    public MsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MsgViewHolder holder = new MsgViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_msg, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MsgViewHolder holder, int position)
    {
        final String itemText = mList.get(position);
        holder.tv.setText(itemText);
    }

    @Override
    public int getItemCount()
    {
        return mList.size();
    }

    class MsgViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv;

        public MsgViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.id_num);
        }
    }

}
