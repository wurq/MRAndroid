package com.wurq.dex.mrandroid.mainpage.msglistner;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wurq.dex.mrandroid.R;
import com.wurq.dex.mrandroid.mainpage.OnFragmentInteractionListener;
import com.wurq.dex.mrandroid.mainpage.msglistner.msgHandle.SmsHandler;
import com.wurq.dex.mrandroid.mainpage.msglistner.msgHandle.SmsObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MsgListnerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MsgListnerFragment extends Fragment implements ServiceConnection{
    private static final String TAG = "MsgListnerFragment";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    MsgIPCAidlInterface mService;

    private List<String> mDatas = new ArrayList<String>() ;

    private RecyclerView mRecyclerView;
    private MsgListnerAdapter mAdapter;


    private Handler mSMShandler = new SmsHandler(getActivity());
    private SmsObserver mObserver;

    public MsgListnerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MsgListnerFragment.
     */
    public static MsgListnerFragment newInstance(String param1, String param2) {
        MsgListnerFragment fragment = new MsgListnerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

//        Log.d(TAG,"Start SmsObserver ContentResolver");
//        ContentResolver resolver = getActivity().getContentResolver();
//        Log.d(TAG,"SmsObserver get ContentResolver");
//
//        mObserver = new SmsObserver(resolver, mSMShandler );
//        resolver.registerContentObserver(Uri.parse("content://sms"), true,mObserver);
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent bindIntent = new Intent(this.getActivity(),MsgListnerService.class);
//        getActivity().startService(bindIntent);
        this.getActivity().bindService(bindIntent,  this,Context.BIND_AUTO_CREATE);
    }


    @Override
    public void onPause() {
        super.onPause();
        if(mService != null)  {
//            mMsService.setCallback(null);
//            getActivity().unbindService(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Intent innerIntent = new Intent(this.getActivity(), MsgListnerService.class);
//        getActivity().startService(innerIntent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView =  inflater.inflate(R.layout.fragment_msg_listner, container, false);

        View view =  mRootView.findViewById(R.id.rv_msg_listner_container);
        if (view instanceof RecyclerView) {
            mRecyclerView = (RecyclerView) view;

//        mRecyclerView.setHasFixedSize(true);
//            mDatas.add("test1");
//            mDatas.add("test2");

            mAdapter = new MsgListnerAdapter(mRootView.getContext(),mDatas);
            mRecyclerView.setAdapter(mAdapter);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext()));
        }
        return mRootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mService = MsgIPCAidlInterface.Stub.asInterface(service);
        try {
            List<MsgRemote> mros = mService.getMsgList();//
            for (MsgRemote mro:mros) {
                mDatas.add(mro.mContent);
            }
//            mDatas.add(mro.get(0).mContent);
//            mDatas.add(mro.get(1).mContent);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

}
