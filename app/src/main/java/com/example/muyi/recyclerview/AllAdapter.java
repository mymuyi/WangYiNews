package com.example.muyi.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by muyi on 17-10-21.
 */

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.ViewHolder>{

    private OnAllTabsListener listener;

    public interface OnAllTabsListener {
        void allTabsItemClick(View view, int position);
    }

    public void setListener(OnAllTabsListener listener) {
        this.listener = listener;
    }

    public AllAdapter() {}

    @Override
    public AllAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AllAdapter.ViewHolder holder, final int position) {
        String text = MainActivity.allTabs.get(position);
        holder.text.setText(text);
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.allTabsItemClick(holder.itemView, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return MainActivity.allTabs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView text;

        public ViewHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.item_text);
        }
    }
}
