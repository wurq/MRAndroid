package com.wurq.dex.mrandroid.mainpage.msg;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wurq.dex.mrandroid.R;
import com.wurq.dex.mrandroid.mainpage.OnFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MsgFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MsgFragment extends Fragment implements ServiceConnection,MsgService.Callback {
    private static final String TAG = "MsgFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MsgService mMsgService;

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private MsgAdapter mAdapter;

    private List<String> mDatas = new ArrayList<String>() ;

//    private View mRootView ;

    public MsgFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MsgFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MsgFragment newInstance(String param1, String param2) {
        MsgFragment fragment = new MsgFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView =  inflater.inflate(R.layout.fragment_msg, container, false);

        View view =  mRootView.findViewById(R.id.rv_msg_container);
        if (view instanceof RecyclerView) {

            mRecyclerView = (RecyclerView) view;

//        mRecyclerView.setHasFixedSize(true);

            mDatas.add("test1");
            mDatas.add("test2");

            mAdapter = new MsgAdapter(mRootView.getContext(), MsgData.ITEMS);
            mRecyclerView.setAdapter(mAdapter);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext()));
        }
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent bindIntent = new Intent(this.getActivity(),MsgService.class);

        this.getActivity().bindService(bindIntent,  this,Context.BIND_AUTO_CREATE);


    }

    @Override
    public void onPause() {
        super.onPause();
        if(mMsgService != null)  {
            mMsgService.setCallback(null);
            getActivity().unbindService(this);
        }
    }

//    private void initContentView() {
//
//        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.rv_msg_container);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
////        mRecyclerView.setHasFixedSize(true);
//
//        mDatas.add(0,"test1");
//        mDatas.add(1,"test2");
//
//        mAdapter = new MsgAdapter(mRootView.getContext(),mDatas);
//        mRecyclerView.setAdapter(mAdapter);
//    }

    // TODO: Rename method, update argument and hook method into UI event
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
        }
        else {
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
        mMsgService = ((MsgService.MsgBinder)service).getService();
        mMsgService.setCallback(this);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mMsgService = null;
    }

    // Service.Callback
    @Override
    public void onOperationProgress(int progress) {
        Log.d(TAG,"onOperationProgress entering");
    }

    @Override
    public void onOperationCompletion() {
        Log.d(TAG,"onOperationCompletion entering");
        mAdapter.notifyDataSetChanged();
    }
}
