package com.zlisa.bottomlayout;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zlisa.adapter.ViewPagerAdapter;
import com.zlisa.widget.BottomLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private BottomLayout mBottomLayout;
    private ViewPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mBottomLayout = (BottomLayout) findViewById(R.id.bottom_layout);

        mBottomLayout.addTab(new BottomLayout.Tab("Home",
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher_round));
        mBottomLayout.addTab(new BottomLayout.Tab("Fri",
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher_round));
        mBottomLayout.addTab(null);
        mBottomLayout.addTab(new BottomLayout.Tab("Msg",
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher_round));
        mBottomLayout.addTab(new BottomLayout.Tab("Mine",
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher_round));

        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mBottomLayout.setUpWithViewPager(mViewPager);

//        mPagerAdapter.addFragment(TestFragment.newInstance());
//        mPagerAdapter.addFragment(TestFragment.newInstance());
//        mPagerAdapter.addFragment(TestFragment.newInstance());
//        mPagerAdapter.addFragment(TestFragment.newInstance());

        mBottomLayout.setOnTabSelectListener(new BottomLayout.OnTabSelectListener() {
            @Override
            public void onTabSelected(BottomLayout.Tab tab) {
                Log.i("MainActivity", "tab : " + tab.getPosition());
            }
        });

        mBottomLayout.setCurrentPosition(0);
    }
}
