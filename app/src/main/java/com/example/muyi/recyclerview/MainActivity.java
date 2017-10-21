package com.example.muyi.recyclerview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AllAdapter.OnAllTabsListener{

    private static final String TAG = "MainActivity";

    public static List<String> choseTabs = new ArrayList<>();
    public static List<String> allTabs = new ArrayList<>();

    public static ChoseAdapter choseAdapter;
    public static AllAdapter allAdapter;

    private RecyclerView choseRecycle, allRecycle;
    private ItemTouchHelper itemTouchHelper;
    private ItemTouchHelper.Callback callback;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initDatas();

    }

    private void initViews() {

        linearLayout = (LinearLayout) findViewById(R.id.scroll);

        choseRecycle = (RecyclerView) findViewById(R.id.chose_recycle);
        allRecycle = (RecyclerView) findViewById(R.id.all_recycle);

        choseRecycle.setLayoutManager(new GridLayoutManager(this, 4));
        allRecycle.setLayoutManager(new GridLayoutManager(this, 4));

        choseAdapter = new ChoseAdapter();
        choseRecycle.setAdapter(choseAdapter);

        allAdapter = new AllAdapter();
        allRecycle.setAdapter(allAdapter);
        allAdapter.setListener(this);

        //关联 ItemTouchHelper 和 RecyclerView
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(choseAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(choseRecycle);
    }

    private void initDatas() {

        choseTabs.clear();
        allTabs.clear();

        choseTabs.add("头条");
        choseTabs.add("科技");
        choseTabs.add("热点");
        choseTabs.add("政务");
        choseTabs.add("移动互联");
        choseTabs.add("军事");
        choseTabs.add("历史");
        choseTabs.add("社会");
        choseTabs.add("财经");
        choseTabs.add("娱乐");

        allTabs.add("体育");
        allTabs.add("时尚");
        allTabs.add("房产");
        allTabs.add("论坛");
        allTabs.add("博客");
        allTabs.add("健康");
        allTabs.add("轻松一刻");
        allTabs.add("直播");
        allTabs.add("段子");
        allTabs.add("彩票");
        allTabs.add("直播");
        allTabs.add("段子");
        allTabs.add("彩票");
        allTabs.add("直播");
        allTabs.add("段子");
        allTabs.add("彩票");
        allTabs.add("轻松一刻");
        allTabs.add("直播");
        allTabs.add("段子");
        allTabs.add("彩票");
        allTabs.add("直播");
        allTabs.add("段子");
        allTabs.add("彩票");
        allTabs.add("直播");
        allTabs.add("段子");
        allTabs.add("彩票");
    }

    @Override
    public void allTabsItemClick(final View view, final int position) {

        int[] parentLoc = new int[2];
        linearLayout.getLocationInWindow(parentLoc);
        Log.i(TAG, "linearX:" + parentLoc[0]);
        Log.i(TAG, "lineary:" + parentLoc[1]);

        int[] startLoc = new int[2];
        int startX, startY;
        view.getLocationInWindow(startLoc);
        startX = startLoc[0];
        startY = startLoc[1] - parentLoc[1];

        Log.i(TAG, "startX: " + startX);
        Log.i(TAG, "startY: " + startY);

        final View startView = view;
        startView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        allRecycle.removeView(view);
        linearLayout.addView(startView);

        View endView;
        int[] endLoc = new int[2];
        choseRecycle.getLocationInWindow(endLoc);
        Log.i(TAG, "choseRecycleX:" + endLoc[0]);
        Log.i(TAG, "choseRecycley:" + endLoc[1]);
        int endX, endY;
        int size = choseTabs.size();
        if (size % 4 == 0) {
            endLoc[0] += 0;
            endLoc[1] = endLoc[1] + (size / 4) * view.getHeight()- parentLoc[1];
        } else {
            endView = choseRecycle.getChildAt(size - 1);
            endView.getLocationInWindow(endLoc);
            endLoc[0] = endLoc[0] + choseRecycle.getWidth()/4;
            endLoc[1] = endLoc[1] - parentLoc[1];
        }
        endX = endLoc[0];
        endY = endLoc[1];
        Log.i(TAG, "endX:" + endLoc[0]);
        Log.i(TAG, "endy:" + endLoc[1]);

        final float[] currentLoc = new float[2];

        Path path = new Path();
        path.moveTo(startX, startY);
        path.lineTo(endX, endY);

        final PathMeasure measure = new PathMeasure(path, false);

        ValueAnimator animation = ValueAnimator.ofFloat(0, measure.getLength());
        animation.setDuration(500);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value =(float) animation.getAnimatedValue();
                measure.getPosTan(value, currentLoc, null);
                startView.setX(currentLoc[0]);
                startView.setY(currentLoc[1]);
                //Log.i("tag", currentLoc[0] + "@" + currentLoc[1]);
            }
        });
        animation.start();

        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

                allRecycle.setItemAnimator(new DefaultItemAnimator());
                choseRecycle.setItemAnimator(new DefaultItemAnimator());

                choseTabs.add(choseTabs.size(), allTabs.get(position));
                allTabs.remove(position);

                allAdapter.notifyDataSetChanged();
                choseAdapter.notifyDataSetChanged();

                allAdapter.notifyItemRemoved(position);
                choseAdapter.notifyItemInserted(choseTabs.size());

                linearLayout.removeView(startView);
            }
        });
    }
}
