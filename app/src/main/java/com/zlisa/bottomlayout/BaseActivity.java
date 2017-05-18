package com.zlisa.bottomlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments == null || fragments.size() == 0) {
            onBackPressed();
            return true;
        } else {
            boolean isNull = false;
            for (Fragment fragment : fragments) {
                if (fragment == null) {
                    isNull = true;
                    break;
                }
                if (!fragment.isHidden()) {
                    removeFragment(fragment);
                }
            }
            if (isNull) {
                onBackPressed();
                return true;
            }
        }
        return super.onSupportNavigateUp();
    }

    public void replaceFragment(int containerViewID, Fragment fragment) {
        if (fragment == null){
            fragment = new Fragment();
        }
        if (fragment.isAdded()) {
            showFragment(fragment);
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(containerViewID, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
    }

    public void replaceFragment(int containerViewID, Fragment fragment, String tag) {
        if (fragment == null){
            fragment = new Fragment();
        }
        if (fragment.isAdded()) {
            showFragment(fragment);
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(containerViewID, fragment, tag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
    }

    public void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .show(fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    public void removeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();
    }
}