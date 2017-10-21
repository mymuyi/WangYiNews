package com.example.muyi.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;

import static android.content.ContentValues.TAG;

/**
 * Created by muyi on 17-10-20.
 */

public class ChoseAdapter extends RecyclerView.Adapter<ChoseAdapter.ViewHolder> implements onMoveAndSwipedListener{


    public ChoseAdapter() {}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text = MainActivity.choseTabs.get(position);
        holder.text.setText(text);
    }

    @Override
    public int getItemCount() {
        return MainActivity.choseTabs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        public ViewHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.item_text);
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        Log.i(TAG, "fromPosition: " + fromPosition);
        Log.i(TAG, "toPosition: " + toPosition);

        //交换 item 数据的位置
        Collections.swap(MainActivity.choseTabs, fromPosition, toPosition);
        //交换 RecyclerView 列表中 item 的位置
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDisMiss(int position) {

        MainActivity.allTabs.add(MainActivity.allTabs.size(), MainActivity.choseTabs.get(position));
        MainActivity.allAdapter.notifyItemInserted(MainActivity.allTabs.size());

        MainActivity.choseTabs.remove(position);
        notifyItemRemoved(position);
    }
}
