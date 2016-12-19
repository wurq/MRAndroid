package com.wurq.dex.mrandroid.mainpage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.wurq.dex.base.AppProfile;
import com.wurq.dex.base.view.FragmentTabHost;
import com.wurq.dex.mrandroid.R;
import com.wurq.dex.mrandroid.mainpage.msg.MsgFragment;
import com.wurq.dex.mrandroid.mainpage.txun.TxunFragment;

public class MainPageActivity extends AppCompatActivity implements TabHost.OnTabChangeListener,
        OnFragmentInteractionListener {
    private static final String TAG = "MainPageActivity";
    public static final String FIRST_ITEM_KEY = "first item key";
    public static final String SPLASH_CLICK_DATA = "splash_click_data";
    private static final int REQUEST_CODE_SPLASH = 0;

    private FragmentTabHost fragmentTabHost;

    private final String[] sTabTexts = new String[]{
            AppProfile.getContext().getResources().getString(R.string.msg_recover),
            AppProfile.getContext().getResources().getString(R.string.tx_recover),
            AppProfile.getContext().getResources().getString(R.string.ok_recover),
    };

    private final int mTabIcons[] = {
            R.drawable.selector_main_page_tab_index,
            R.drawable.selector_main_page_tab_data,
            R.drawable.selector_main_page_tab_knowledge,
    };


    private Class mFragmentClasses[] = {
            MsgFragment.class,
            TxunFragment.class,
            MsgFragment.class,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        initContentView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    private void initContentView() {

        fragmentTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        fragmentTabHost.setup(getBaseContext(), getSupportFragmentManager(), R.id.realtabcontent);

        for (int i = 0; i < mFragmentClasses.length; i++) {
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(sTabTexts[i]).setIndicator(getTabView(i));
            fragmentTabHost.addTab(tabSpec, mFragmentClasses[i], null);
            fragmentTabHost.getTabWidget().setDividerDrawable(android.R.color.transparent);
   /*         fragmentTabHost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.bg_main_page_tab);*/
        }
        setTabSelected(0, true);

        //解决部分手机上tab顶部出现黑条问题
        TabWidget tabWidget = fragmentTabHost.getTabWidget();
        if (tabWidget != null) {
            tabWidget.setPadding(0, 0, 0, 0);
            tabWidget.setBackgroundResource(R.color.white);
        }

        fragmentTabHost.setOnTabChangedListener(this);
    }


    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainPageActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    private View getTabView(int index) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab_view, null);

        TextView text = (TextView) view.findViewById(R.id.tv_main_page_tab_title);
//        text.setVisibility(View.GONE);
        text.setText(sTabTexts[index]);
        ImageView imgIcon = (ImageView) view.findViewById(R.id.img_main_page_tab_icon);
        imgIcon.setImageResource(mTabIcons[index]);
        return view;
    }

    @Override
    public void onTabChanged(String tabId) {
        for (int i = 0; i < sTabTexts.length; i++) {
            if (tabId.equals(sTabTexts[i])) {
                setTabSelected(i, true);
            } else {
                setTabSelected(i, false);
            }
        }
    }

    private void setTabSelected(int index, boolean bSelected) {
        View tabView = fragmentTabHost.getTabWidget().getChildTabViewAt(index);
        if (tabView == null)
            return; // 避免崩溃，发生过一次
        tabView.findViewById(R.id.tv_main_page_tab_title).setSelected(bSelected);
        tabView.findViewById(R.id.img_main_page_tab_icon).setSelected(bSelected);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
