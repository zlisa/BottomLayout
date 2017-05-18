package com.zlisa.bottomlayout;

import android.os.Bundle;
import android.util.Log;

import com.zlisa.widget.BottomLayout;

public class Main2Activity extends BaseActivity {

    private BottomLayout mBottomLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mBottomLayout = (BottomLayout) findViewById(R.id.bottom_layout);

        mBottomLayout.addTab(new BottomLayout.Tab("Home",
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
                R.color.colorPrimary,
                R.color.colorPrimaryDark));
        mBottomLayout.addTab(new BottomLayout.Tab("Fri",
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
                R.color.colorPrimary,
                R.color.colorPrimaryDark));
        mBottomLayout.addTab(null);
        mBottomLayout.addTab(new BottomLayout.Tab("Msg",
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
                R.color.colorPrimary,
                R.color.colorPrimaryDark));
        mBottomLayout.addTab(new BottomLayout.Tab("Mine",
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
                R.color.colorPrimary,
                R.color.colorPrimaryDark));

        final TestFragment fragment1 = TestFragment.newInstance("Fragment1");
        final TestFragment fragment2 = TestFragment.newInstance("Fragment2");
        final TestFragment fragment3 = TestFragment.newInstance("Fragment3");
        final TestFragment fragment4 = TestFragment.newInstance("Fragment4");

        mBottomLayout.setOnTabSelectListener(new BottomLayout.OnTabSelectListener() {
            @Override
            public void onTabSelected(BottomLayout.Tab tab) {
                Log.i("MainActivity", "tab : " + tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        replaceFragment(R.id.container, fragment1);
                        break;
                    case 1:
                        replaceFragment(R.id.container, fragment2);
                        break;
                    case 2:
                        replaceFragment(R.id.container, fragment3);
                        break;
                    case 3:
                        replaceFragment(R.id.container, fragment4);
                        break;
                }
            }
        });

        mBottomLayout.setUp();
    }
}
