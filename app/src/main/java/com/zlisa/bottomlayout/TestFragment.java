package com.zlisa.bottomlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zlisa on 2017/5/8.
 */

public class TestFragment extends Fragment {

    public static final String KEY_TEXT = "text key";

    private TextView mTextView;
    private String text;

    public static TestFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString(KEY_TEXT, text);
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text = getArguments().getString(KEY_TEXT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextView = (TextView) view.findViewById(R.id.text_view);

        mTextView.setText(text);
    }
}
