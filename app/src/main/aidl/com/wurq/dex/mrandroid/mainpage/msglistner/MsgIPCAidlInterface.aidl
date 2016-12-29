// MsgIPCAidlInterface.aidl


// Declare any non-default types here with import statements
package com.wurq.dex.mrandroid.mainpage.msglistner;

//
import com.wurq.dex.mrandroid.mainpage.msglistner.MsgRemote;

import com.wurq.dex.mrandroid.mainpage.msglistner.OnNewMsgArriveListener;

interface MsgIPCAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    List<MsgRemote> getMsgList();

    void registerListener(OnNewMsgArriveListener listener);

    void unRegisterListener(OnNewMsgArriveListener listener);
}
