package com.wurq.dex.mrandroid.mainpage.msglistner;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wurongqiu on 16/12/27.
 */
public class MsgRemote implements Parcelable{
    public int mId;
    public String mContent;

    protected MsgRemote(int id,String content) {
        mId = id;
        mContent = content;
    }

    protected MsgRemote(Parcel in) {
        mId = in.readInt();
        mContent = in.readString();
    }

    public static final Creator<MsgRemote> CREATOR = new Creator<MsgRemote>() {
        @Override
        public MsgRemote createFromParcel(Parcel in) {
            return new MsgRemote(in);
        }

        @Override
        public MsgRemote[] newArray(int size) {
            return new MsgRemote[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mContent);
    }
}
