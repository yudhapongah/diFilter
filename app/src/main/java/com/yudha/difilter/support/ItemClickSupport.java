package com.yudha.difilter.support;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yudha.difilter.R;

public class ItemClickSupport {
    private final RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public ItemClickSupport(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        mRecyclerView.setTag(R.id.item_click_support,this);
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener!=null){
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(view);
                mOnItemClickListener.onItemClicked(mRecyclerView,holder.getAdapterPosition(),view);
            }
        }
    };

    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener(){

        @Override
        public boolean onLongClick(View view) {
            if (mOnItemLongClickListener!=null){
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(view);
                mOnItemLongClickListener.onItemLongClicked(mRecyclerView,holder.getAdapterPosition(),view);
            }
            return false;
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener mAttachListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(@NonNull View view) {
            if (mOnItemClickListener!=null){
                view.setOnClickListener(mOnClickListener);
            }
            if (mOnItemLongClickListener!=null){
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(@NonNull View view) {

        }
    };

    public static ItemClickSupport addTo(RecyclerView view){
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (support==null){
            support=new ItemClickSupport(view);
        }
        return support;
    }

    public static ItemClickSupport removeFrom(RecyclerView view){
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (support!=null){
            support.detach(view);
        }
        return support;
    }

    public ItemClickSupport setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
        return this;
    }


    public ItemClickSupport setmOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
        return this;
    }

    private  void  detach(RecyclerView view){
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(R.id.item_click_support,null);
    }
    public interface OnItemClickListener{
        void onItemClicked(RecyclerView recyclerView,int position,View v);
    }
    public interface OnItemLongClickListener{
        void onItemLongClicked(RecyclerView recyclerView,int position,View v);
    }
}
