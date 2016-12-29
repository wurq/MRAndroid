// OnNewMsgArriveListener.aidl.aidl
package com.wurq.dex.mrandroid.mainpage.msglistner;

import com.wurq.dex.mrandroid.mainpage.msglistner.MsgRemote;
//import com.wurq.dex.mrandroid.mainpage.msglistner.OnNewMsgArriveListener;
 interface OnNewMsgArriveListener {
    void OnNewMsgArrived(in MsgRemote newMsg);
}
