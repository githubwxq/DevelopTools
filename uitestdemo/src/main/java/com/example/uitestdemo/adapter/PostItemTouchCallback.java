package com.example.uitestdemo.adapter;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uitestdemo.bean.PostItem;

import java.util.List;


/**
 * Created by Administrator on 2016/4/12.
 */

//
//isItemViewSwipeEnable : Item是否可以滑动
//        isLongPressDragEnable ：Item是否可以长按
//        getMovementFlags ： 获取移动标志
//        onMove ： 当一个Item被另外的Item替代时回调，也就是数据集的内容顺序改变
//        onMoved ： 当onMove返回true的时候回调
//        onSwiped ： 当某个Item被滑动离开屏幕之后回调
//        setSelectedChange ： 某个Item被长按选中会被回调，当某个被长按移动的Item被释放时也调用
public class PostItemTouchCallback extends ItemTouchHelper.Callback {//ItemTouchHelper.Callback 实现才能拖动 移动处理的回调 adapter  itemTouchAdapter.onMove方法 adapter实现这个方法


    private   List<PostItem> list;

    private ItemTouchAdapter itemTouchAdapter;
    public PostItemTouchCallback(ItemTouchAdapter itemTouchAdapter, List<PostItem> list){ //设置回调监听时间由adapter处理传递参数给adapter
        this.itemTouchAdapter = itemTouchAdapter;
        this.list=list;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        Log.i("wxq","isLongPressDragEnabled");
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {

        Log.i("wxq","isLongPressDragEnabled");
        return false;
    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.i("wxq","getMovementFlags");

      int  currentMovePosition=viewHolder.getAdapterPosition();
//        if (!list.get(currentMovePosition).isCanDrag()) {
//            return makeMovementFlags(0, 0);
//        }
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            //final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.i("wxq","onmove");
        int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
        int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position

//        if (!list.get(fromPosition).isCanDrag()||  !list.get(toPosition).isCanDrag()) {
//            return  false;
//        }
        itemTouchAdapter.onMove(fromPosition,toPosition,viewHolder);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.i("wxq","onSwiped");
        int position = viewHolder.getAdapterPosition();
        itemTouchAdapter.onSwiped(position);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        Log.i("wxq","onChildDraw");
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //滑动时改变Item的透明度
//            final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
//            viewHolder.itemView.setAlpha(alpha);
//            viewHolder.itemView.setTranslationX(dX);
        }
//        else if(viewHolder.getLayoutPosition()==5||viewHolder.getLayoutPosition()==6){
//
//            return;
//        }
        else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        Log.i("wxq","onSelectedChanged");
//        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
//            if (background == null && bkcolor == -1) {
//                Drawable drawable = viewHolder.itemView.getBackground();
//                if (drawable == null) {
//                    bkcolor = 0;
//                } else {
//                    background = drawable;
//                }
//            }
//            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
//            int position=  viewHolder.getAdapterPosition();
//            if (onDragListener!=null){
//                onDragListener.onStartDrag(position,viewHolder); // tuodon
//            }
//            itemTouchAdapter.onStartDrag(position,viewHolder);
//
//        }

//        if (actionState== ItemTouchHelper.ANIMATION_TYPE_SWIPE_CANCEL) {
//            ((RecyclerAdapter.MyViewHolder)viewHolder).item_img_delete.setVisibility(View.GONE);
//        }
//        if (actionState== ItemTouchHelper.ANIMATION_TYPE_DRAG) {
//            ((RecyclerAdapter.MyViewHolder)viewHolder).item_img_delete.setVisibility(View.GONE);
//        }



        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.i("wxq","clearView");
        super.clearView(recyclerView, viewHolder);
//
//        viewHolder.itemView.setAlpha(1.0f);
//        if (background != null) viewHolder.itemView.setBackgroundDrawable(background);
//        if (bkcolor != -1) viewHolder.itemView.setBackgroundColor(bkcolor);
//        //viewHolder.itemView.setBackgroundColor(0);
//        viewHolder.itemView.setBackgroundResource(R.color.common_white);//
//        if (onDragListener!=null){
//            int position=  viewHolder.getAdapterPosition();
//            onDragListener.onFinishDrag(position,viewHolder); // tuodon
//            itemTouchAdapter.onFinishDrag(position,viewHolder);
//        }


   //   ((RecyclerAdapter.MyViewHolder)viewHolder).item_img_delete.setVisibility(View.GONE);
    }

    private Drawable background = null;
    private int bkcolor = -1;

    private OnDragListener onDragListener;
    public PostItemTouchCallback setOnDragListener(OnDragListener onDragListener) {
        this.onDragListener = onDragListener;
        return this;
    }
    public interface OnDragListener{
        void onFinishDrag(int position, RecyclerView.ViewHolder viewHolder);
        void onStartDrag(int position, RecyclerView.ViewHolder viewHolder);
    }

    public interface ItemTouchAdapter {
        void onMove(int fromPosition, int toPosition, RecyclerView.ViewHolder viewHolder);
        void onSwiped(int position);
        void onFinishDrag(int position, RecyclerView.ViewHolder viewHolder);
        void onStartDrag(int position, RecyclerView.ViewHolder viewHolder);

    }
}
