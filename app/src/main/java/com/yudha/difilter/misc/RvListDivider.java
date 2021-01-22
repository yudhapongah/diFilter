package com.yudha.difilter.misc;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RvListDivider extends RecyclerView.ItemDecoration {

    private final int spaceHeight;

    public RvListDivider(int spaceHeight) {
        this.spaceHeight = spaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view)==0){
            outRect.top = spaceHeight;
        }
        outRect.bottom = spaceHeight;
    }
}
