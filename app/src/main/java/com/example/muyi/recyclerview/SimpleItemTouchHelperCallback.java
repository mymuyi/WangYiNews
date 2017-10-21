package com.example.muyi.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by muyi on 17-10-21.
 */

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback{

    private onMoveAndSwipedListener adapter;

    public SimpleItemTouchHelperCallback(onMoveAndSwipedListener listener) {
        this.adapter = listener;
    }

    /**
     * 设置拖动的方向以及侧滑的方向的
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        Log.i(TAG, "getMovementFlags");

        //设置拖拽方向
        final int dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                ItemTouchHelper.UP | ItemTouchHelper.DOWN;

        //设置侧滑方向为从左到右和从右到左都可以，start 代表可以从右向左滑
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 拖动 item 时会回调此方法
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        Log.i(TAG, "onMove");

        //如果两个item不是一个类型的，我们让他不可以拖拽
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return false;
    }

    /**
     * 侧滑 item 时会回调此方法
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.i(TAG, "onSwiped");
        adapter.onItemDisMiss(viewHolder.getAdapterPosition());
    }
}
