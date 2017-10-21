package com.example.muyi.recyclerview;

/**
 * Created by muyi on 17-10-20.
 */

public interface onMoveAndSwipedListener {

    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDisMiss(int position);

}
