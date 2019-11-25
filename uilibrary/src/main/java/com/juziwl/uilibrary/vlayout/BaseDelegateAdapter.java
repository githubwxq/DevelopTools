package com.juziwl.uilibrary.vlayout;

import android.content.Context;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * vlayoutadapter的简单封装
 * @param <T>
 * @param <VH>
 */
public abstract class BaseDelegateAdapter<T, VH extends RecyclerView.ViewHolder> extends DelegateAdapter.Adapter<VH>{

    private static final String TAG = BaseDelegateAdapter.class.getSimpleName();


    public Context mContext;
    private LayoutInflater mLayoutInflater;


    private int mLayoutResId;

    private List<T> mData;

    private LayoutHelper mLayoutHelper;

    public BaseDelegateAdapter(Context context,@LayoutRes int layoutResId, @Nullable List<T> data, LayoutHelper layoutHelper) {
        mContext=context;
        this.mData = data == null ? new ArrayList<>() : data;
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
        this.mLayoutHelper = layoutHelper;
    }

    public BaseDelegateAdapter(Context context,@LayoutRes int layoutResId, @Nullable List<T> data) {
        mContext=context;
        this.mData = data == null ? new ArrayList<>() : data;
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
        this.mLayoutHelper = getLayoutHelp();
    }

    /**
     * 不传layouthelp你需要 自己重写getlayouthelp方法
     * @return
     */
    public LayoutHelper getLayoutHelp() {
        return null;
    }


//    public BaseDelegateAdapter( @Nullable List<T> data) {
//
//    }


    /**
     * 删除一条数据
     */
    public void removeItem(int position) {
        mData.remove(position);
        // 暴力刷新
//        notifyDataSetChanged();
        // 差量刷新
        notifyItemRemoved(position);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VH baseViewHolder = null;
//        this.mContext = parent.getContext();
        this.mLayoutInflater = LayoutInflater.from(mContext);
        baseViewHolder = onCreateDefViewHolder(parent, viewType);
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        VH viewHolder = holder;
        bindViewClickListener(viewHolder,position);
        convert(holder, getItem(position));
    }

    @NonNull
    public List<T> getData() {
        return mData;
    }

    @Nullable
    public T getItem(@IntRange(from = 0) int position) {
        if (position >= 0 && position < mData.size())
            return mData.get(position);
        else
            return null;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void bindViewClickListener( VH baseViewHolder,int position) {
        if (baseViewHolder == null) {
            return;
        }
        final View view = baseViewHolder.itemView;
        if (view == null) {
            return;
        }
        if (listener != null) {
            view.setOnClickListener(v -> listener.onItemClick(this,v,position));
        }
        if (longClickListener != null) {
            view.setOnLongClickListener(v -> longClickListener.onItemLongClick(BaseDelegateAdapter.this,v,position));
        }
    }
    protected VH onCreateDefViewHolder(ViewGroup parent, int viewType) {
        int layoutId = mLayoutResId;
        return createBaseViewHolder(parent, layoutId);
    }

    protected VH createBaseViewHolder(ViewGroup parent, int layoutResId) {
        return createViewHolder(getItemView(layoutResId, parent));
    }
    protected VH createViewHolder(View view) {
        Class temp = getClass();
        Class z = null;
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp);
            temp = temp.getSuperclass();
        }
        VH k;
        // 泛型擦除会导致z为null
        if (z == null) {
            k = (VH) new BaseDelegateViewHolder(view);
        } else {
            k = createGenericKInstance(z, view);
        }
        return k != null ? k : (VH) new BaseDelegateViewHolder(view);
    }
    /**
     * get generic parameter K
     *
     * @param z
     * @return
     */
    private Class getInstancedGenericKClass(Class z) {
        Type type = z.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            for (Type temp : types) {
                if (temp instanceof Class) {
                    Class tempClass = (Class) temp;
                    if (BaseDelegateViewHolder.class.isAssignableFrom(tempClass)) {
                        return tempClass;
                    }
                } else if (temp instanceof ParameterizedType) {
                    Type rawType = ((ParameterizedType) temp).getRawType();
                    if (rawType instanceof Class && BaseDelegateViewHolder.class.isAssignableFrom((Class<?>) rawType)) {
                        return (Class<?>) rawType;
                    }
                }
            }
        }
        return null;
    }
    /**
     * try to create Generic K instance
     *
     * @param z
     * @param view
     * @return
     */
    @SuppressWarnings("unchecked")
    private VH createGenericKInstance(Class z, View view) {
        try {
            Constructor constructor;
            // inner and unstatic class
            if (z.isMemberClass() && !Modifier.isStatic(z.getModifiers())) {
                constructor = z.getDeclaredConstructor(getClass(), View.class);
                constructor.setAccessible(true);
                return (VH) constructor.newInstance(this, view);
            } else {
                constructor = z.getDeclaredConstructor(View.class);
                constructor.setAccessible(true);
                return (VH) constructor.newInstance(view);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    protected View getItemView(@LayoutRes int layoutResId, ViewGroup parent) {
        return mLayoutInflater.inflate(layoutResId, parent, false);
    }

    //item点击事件
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {

        void onItemClick(BaseDelegateAdapter adapter,View view,int position);

    }
    //item长按事件
    private OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    public interface OnItemLongClickListener {

        boolean onItemLongClick(BaseDelegateAdapter adapter,View view,int position);

    }

    public abstract void convert(VH holder, T item);

}
