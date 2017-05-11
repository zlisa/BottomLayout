package com.zlisa.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.zlisa.R;
import com.zlisa.util.DpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部导航栏
 * Created by zlisa on 2017/5/9.
 */

public class BottomLayout extends LinearLayout {

    private ViewPager mViewPager;
    //所有Tab真实视图
    private List<TabView> mTabs;
    //当前选择项
    private int currentPosition = 0;
    //上一次选择项，默认为0
    private int lastPosition = currentPosition;
    //Tab点击监听器
    private OnTabSelectListener onTabSelectListener;

    public BottomLayout(Context context) {
        this(context, null);
    }

    public BottomLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //设置横向布局
        setOrientation(HORIZONTAL);
        //设置布局居中排布
        setGravity(Gravity.CENTER);
        //设置布局最小高度
        setMinimumHeight(DpUtil.dip2px(getContext(), 56));

        mTabs = new ArrayList<>();
    }

    /**
     * 添加Tab
     *
     * @param tab
     */
    public void addTab(Tab tab) {
        addTab(tab, mTabs.size());
    }

    /**
     * 添加Tab到指定位置
     *
     * @param tab
     * @param position
     */
    public void addTab(Tab tab, int position) {
        addTab(tab, position, false);
    }

    /**
     * 添加Tab到指定位置，并指定是否选中
     *
     * @param tab
     * @param position
     * @param isSelected
     */
    public void addTab(Tab tab, int position, boolean isSelected) {
        tab.setPosition(position);
        tab.setSelected(isSelected);
        TabView tabView = createTabView(tab);

        mTabs.add(tabView);
    }

    /**
     * 设置当前选择项
     *
     * @param currentPosition
     */
    public void setCurrentPosition(int currentPosition) {
        //更新上一选择项
        this.lastPosition = this.currentPosition;
        //更新当前选择项
        this.currentPosition = currentPosition;
        //取消上一选择项
        mTabs.get(lastPosition).setUnSelected();
        //选中当前选择项
        mTabs.get(currentPosition).setSelected();

        mViewPager.setCurrentItem(currentPosition);
    }

    /**
     * 绑定Tab选择监听器
     *
     * @param onTabSelectListener
     */
    public void setOnTabSelectListener(OnTabSelectListener onTabSelectListener) {
        this.onTabSelectListener = onTabSelectListener;
    }

    public void setUpWithViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Log.i("BottomLayout", "onPageSelected : " + position);
                setCurrentPosition(position);
            }
        });
    }

    /**
     * 创建一个新的Tab视图
     *
     * @param tab
     * @return
     */
    private TabView createTabView(Tab tab) {
        TabView tabView = new TabView(this);
        tabView.setTab(tab);
        return tabView;
    }

    /**
     * Tab视图
     */
    private class TabView {
        //动画播放时长
        private static final int DURATION = 120;
        //选中状态放大尺寸
        private static final float MAX_SCALE = 1.1f;
        //点击缩小尺寸
        private static final float MIN_SCALE = 0.5f;
        //正常大小
        private static final float NORMAL_SCALE = 1f;
        //点击最小透明度
        private static final float MIN_ALPHA = 0.5f;
        //正常透明度
        private static final float MAX_ALPHA = 1f;
        //绑定的Tab内容
        private Tab mTab;
        //真实容器
        private LinearLayout mTabContainer;
        //Tab图标
        private AppCompatImageView mTabImage;
        //Tab文字
        private AppCompatTextView mTabText;

        TabView(ViewGroup containerLayout) {
            mTabContainer = (LinearLayout) LayoutInflater.from(containerLayout.getContext())
                    .inflate(R.layout.layout_bottom_tab, containerLayout, false);
            mTabImage = (AppCompatImageView) mTabContainer.findViewById(R.id.tab_image);
            mTabText = (AppCompatTextView) mTabContainer.findViewById(R.id.tab_text);
            //设置真实容器的布局属性
            LayoutParams lp = (LayoutParams) mTabContainer.getLayoutParams();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            //在父容器中均匀分布的必要
            lp.weight = 1;
            //Tab中内容分布居中
            lp.gravity = Gravity.CENTER;
            //添加Tab视图到BottomLayout中去
            containerLayout.addView(mTabContainer);
            //Tab点击事件，用于更新选中和未选中状态
            mTabContainer.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTabClick();
                }
            });
            //Tab触摸事件监听，控制动画
            mTabContainer.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            animSmall();
                            break;
                        case MotionEvent.ACTION_UP:
                            animBig();
                            break;
                    }
                    return false;
                }
            });
        }

        /**
         * 设置Tab
         *
         * @param tab
         */
        void setTab(Tab tab) {
            mTab = tab;
            update();
        }

        /**
         * 更新Tab
         */
        void update() {
            mTabText.setText(mTab.getText());
            mTabText.setTextColor(mTab.getTextColor());
            mTabImage.setImageResource(mTab.getImgResId());
            mTab.setSelected(false);
        }

        /**
         * 设置当前Tab视图为选中状态
         */
        void setSelected() {
            mTabText.setText(mTab.getText());
            mTabText.setTextColor(mTab.getFocusTextColor());
            mTabImage.setImageResource(mTab.getFocusImgResId());
            mTab.setSelected(true);
        }

        /**
         * 取消当前Tab视图选中状态
         */
        void setUnSelected() {
            update();

            animInitial();
        }

        /**
         * 处理Tab点击事件
         */
        private void onTabClick() {
            setCurrentPosition(mTab.getPosition());
            if (onTabSelectListener != null) {
                onTabSelectListener.onTabSelected(mTab);
            }
        }

        /**
         * 按下缩小动画
         */
        private void animSmall() {
            ObjectAnimator alpha = ObjectAnimator.ofFloat(mTabContainer, View.ALPHA, MAX_ALPHA, MIN_ALPHA);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(mTabContainer, View.SCALE_X, NORMAL_SCALE, MIN_SCALE);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(mTabContainer, View.SCALE_Y, NORMAL_SCALE, MIN_SCALE);
            AnimatorSet set = new AnimatorSet();
            set.setDuration(DURATION);
            set.setInterpolator(new DecelerateInterpolator(2));
            set.play(scaleX).with(scaleY).with(alpha);
            set.start();
        }

        /**
         * 抬起放大动画
         */
        private void animBig() {
            ObjectAnimator alpha = ObjectAnimator.ofFloat(mTabContainer, View.ALPHA, MIN_ALPHA, MAX_ALPHA);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(mTabContainer, View.SCALE_X, MIN_SCALE, MAX_SCALE);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(mTabContainer, View.SCALE_Y, MIN_SCALE, MAX_SCALE);
            AnimatorSet set = new AnimatorSet();
            set.setDuration(DURATION);
            set.setInterpolator(new DecelerateInterpolator(2));
            set.play(scaleX).with(scaleY).with(alpha);
            set.start();
        }

        /**
         * 取消选中，恢复正常动画
         */
        private void animInitial() {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(mTabContainer, View.SCALE_X, MAX_SCALE, NORMAL_SCALE);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(mTabContainer, View.SCALE_Y, MAX_SCALE, NORMAL_SCALE);
            AnimatorSet set = new AnimatorSet();
            set.setDuration(DURATION);
            set.setInterpolator(new DecelerateInterpolator(2));
            set.play(scaleX).with(scaleY);
            set.start();
        }
    }

    /**
     * Tab内容
     */
    public static class Tab {

        private String text;

        private int imgResId;
        private int focusImgResId;

        private int textColor;
        private int focusTextColor;

        private boolean isSelected = false;
        private int position = 0;

        public Tab(String text,
                   @DrawableRes int imgResId,
                   @DrawableRes int focusImgResId,
                   @ColorInt int textColor,
                   @ColorInt int focusTextColor) {
            this.text = text;
            this.imgResId = imgResId;
            this.focusImgResId = focusImgResId;
            this.textColor = textColor;
            this.focusTextColor = focusTextColor;
        }

        public String getText() {
            return text;
        }

        public int getImgResId() {
            return imgResId;
        }

        public int getFocusImgResId() {
            return focusImgResId;
        }

        public int getTextColor() {
            return textColor;
        }

        public int getFocusTextColor() {
            return focusTextColor;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public boolean isSelected() {
            return isSelected;
        }
    }

    public interface OnTabSelectListener {
        void onTabSelected(Tab tab);
    }

}
