package br.com.bunker.helper;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public List<T> adapterList = new ArrayList<>();
    private Context mContext;
    private OnViewHolderClick mListener;

    public interface OnViewHolderClick {
        void onClick(View view, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Map<Integer, View> mMapView;
        private OnViewHolderClick mListener;

        public ViewHolder(View view, OnViewHolderClick listener) {
            super(view);
            mMapView = new HashMap<>();
            mMapView.put(0, view);
            mListener = listener;

            if (mListener != null)
                view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null)
                mListener.onClick(view, getAdapterPosition());
        }

        public void initViewList(int[] idList) {
            for (int id : idList)
                initViewById(id);
        }

        public void initViewById(int id) {
            View view = (getView() != null ? getView().findViewById(id) : null);

            if (view != null)
                mMapView.put(id, view);
        }

        public View getView() {
            return getView(0);
        }

        public View getView(int id) {
            if (mMapView.containsKey(id))
                return mMapView.get(id);
            else
                initViewById(id);

            return mMapView.get(id);
        }
    }

    protected abstract View createView(Context context, ViewGroup viewGroup, int viewType);

    protected abstract void bindView(T item, ViewHolder viewHolder);

    public RecyclerAdapter(Context context) {
        this(context, null);
    }

    public RecyclerAdapter(Context context, OnViewHolderClick listener) {
        super();
        mContext = context;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(createView(mContext, viewGroup, viewType), mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        bindView(getItem(position), viewHolder);
    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public T getItem(int index) {
        return ((adapterList != null && index < adapterList.size()) ? adapterList.get(index) : null);
    }

    public Context getContext() {
        return mContext;
    }

    public void setClickListener(OnViewHolderClick listener) {
        mListener = listener;
    }


}