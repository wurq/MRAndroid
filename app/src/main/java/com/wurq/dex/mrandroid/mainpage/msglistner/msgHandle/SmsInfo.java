package com.wurq.dex.mrandroid.mainpage.msglistner.msgHandle;

/**
 * Created by wurongqiu on 16/12/28.
 */
public class SmsInfo {
    public String _id = "";
    public String thread_id = "";
    public String smsAddress = "";
    public String smsBody = "";
    public String read = "";
    public int action = 0;// 1代表设置为已读，2表示删除短信
    @Override
    public String toString() {
        return "SmsInfo [_id=" + _id + ", thread_id=" + thread_id
                + ", smsAddress=" + smsAddress + ", smsBody=" + smsBody
                + ", read=" + read + ", action=" + action + "]";
    }
}
