package com.common.projectcommonframe.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 基类RecyclerView baseAdapter
 */
public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    public Context mContext;
    public List<T> mList; // 数据源
    public LayoutInflater inflater;

    public BaseAdapter(Context context) {
        this.mContext = context;
        this.mList = new ArrayList<>();
        inflater = (LayoutInflater) mContext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     * 得到某个位置的数据对象
     * @param position
     * @return
     */
    public T getItem(int position){
        return mList == null ? null :mList.get(position) ;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateVH(parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH vh, int position) {
        onBindVH(vh, position);
    }

    /**
     * 创建 View Holder
     *
     * @param parent   parent
     * @param viewType item type
     * @return view holder
     */
    public abstract VH onCreateVH(ViewGroup parent, int viewType);

    /**
     * 绑定 View Holder
     *
     * @param vh       view holder
     * @param position position
     */
    public abstract void onBindVH(VH vh, int position);

    /**
     * 刷新数据
     *
     * @param data 数据源
     */
    public void refreshData(List<T> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 加载更多
     *
     * @param data 加载的新数据
     */
    public void loadMoreData(List<T> data) {
        mList.addAll(data);
        notifyDataSetChanged();
    }


    /**
     * 移除一个Item
     * @param adapterPosition
     */
    public void removeItem(int adapterPosition){
        if (adapterPosition==RecyclerView.NO_POSITION)
            return;
        if (adapterPosition >= 0 && adapterPosition < mList.size()) {  //mList为数据集合
            mList.remove(adapterPosition);
            notifyItemRemoved(adapterPosition);
        }
    }

    /**
     * 更新某个Item
     * @param o
     */
    public void update(Object o) {
        if (mList!=null) {
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i)==o) {
                    notifyItemChanged(i);
                    return;
                }
            }
        }
    }
}
