package com.github.captain_miao.agera.tutorial.base;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YanLu
 * @since 16/5/13
 */
public abstract class BasePagerAdapter<T> extends PagerAdapter {
    protected ArrayList<T> mData = new ArrayList();
    protected LayoutInflater mInflater;
    protected Context mContext;

    public BasePagerAdapter(Context c) {
        mContext = c;
        mInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public abstract Object instantiateItem(ViewGroup container, int position);

    @Override
    public abstract void destroyItem(ViewGroup container, int position, Object object);

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    public void addItem(final T item) {
        mData.add(item);
        notifyDataSetChanged();
    }
    public void addAll(final List<T> items) {
        if (items != null) {
            mData.addAll(items);
        }
        notifyDataSetChanged();
    }
    public void setData(final List<T> items) {
        mData.clear();
        if (items != null) {
            mData.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void addItem(int idx, final T item) {
        mData.add(idx, item);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mData.clear();
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public ArrayList<T> getData() {
        return mData;
    }
}
