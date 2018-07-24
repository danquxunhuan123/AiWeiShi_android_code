package com.trs.aiweishi.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.MyHolder;
import com.trs.aiweishi.bean.ListData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liufan on 2018/5/17.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<MyHolder> {
    protected List<T> list;
    protected List<T> listMore;
    protected Context context;
    protected OnLoadMoreListener listener;
    private BaseBean loadMorebean = new BaseBean();

    public BaseAdapter(List<T> listData, Context context) {
        listMore = listData;
        if (listData == null)
            list = new ArrayList<>();
        else
            list = listData;
        this.context = context;

//        loadMorebean.setType(BaseBean.ITEM_LOAD_MORE);
//        list.add((T) loadMorebean);
    }

    public void updateData(List<T> listData) {
        listMore = listData;

        if (listData == null) {
            list = new ArrayList<>();
        } else {
            list = listData;
        }

        if (listener != null)
            list.add((T) loadMorebean);

        notifyDataSetChanged();
    }

    public int getSize() {
        return list.size() - 1;
    }

    public void addData(List<T> listData) {
        listMore = listData;
        if (listData != null && listData.size() > 0) {
            list.addAll(list.size() - 1, listData);
            notifyDataSetChanged();
        }else {
            notifyItemRangeChanged(getItemCount() - 1,getItemCount()); //更新最后一个加载更多布局
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof BaseBean)
            return ((BaseBean) list.get(position)).getType();
        else
            return 0;
    }

    public abstract MyHolder getViewHolder(ViewGroup parent, int viewType);

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BaseBean.ITEM_LOAD_MORE)
            return MyHolder.getComViewHolder(parent.getContext(), R.layout.list_load_more_layout, parent);
        else
            return getViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if (getItemViewType(position) == BaseBean.ITEM_LOAD_MORE) {
            //loadmore
            if (listener != null) {
                holder.getItemView().setVisibility(View.VISIBLE);
                //判断加载更多集合是否为空,不为空继续记载更多
                if (listMore != null && listMore.size() > 1) {
                    ((TextView) holder.getView(R.id.tv_load_more)).setText(context.getResources().getString(R.string.load_more));
                    (holder.getView(R.id.progress_bar)).setVisibility(View.VISIBLE);
                    listener.OnLoadMore();
                } else {//如果为空，隐藏加载布局。
                    ((TextView) holder.getView(R.id.tv_load_more)).setText(context.getResources().getString(R.string.no_more));
                    (holder.getView(R.id.progress_bar)).setVisibility(View.GONE);
                }
            }
//            else {
//                holder.getItemView().setVisibility(View.GONE);
//            }
        } else {
            bindMyViewHolder(holder, position);
        }
    }

    protected abstract void bindMyViewHolder(MyHolder holder, int position);

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnLoadMoreListener {
        void OnLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.listener = listener;

        loadMorebean.setType(BaseBean.ITEM_LOAD_MORE);
        list.add((T) loadMorebean);
    }
}
