package com.github.captain_miao.agera.tutorial.recycleview;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.captain_miao.agera.tutorial.BR;
import com.github.captain_miao.agera.tutorial.R;
import com.github.captain_miao.agera.tutorial.model.ApiResult;
import com.github.captain_miao.agera.tutorial.model.GirlInfo;
import com.github.captain_miao.recyclerviewutils.BaseWrapperRecyclerAdapter;
import com.google.android.agera.MutableRepository;
import com.google.android.agera.Updatable;

import java.util.List;


/**
 * @author YanLu
 * @since 16/4/27
 */
public class ComplexRvAdapter extends BaseWrapperRecyclerAdapter<GirlInfo, RecyclerView.ViewHolder> implements Updatable{

    public ComplexRvAdapter() {
    }

    public ComplexRvAdapter(List<GirlInfo> items) {
        addAll(items);
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_view, parent, false);

        return new ComplexRvAdapter.ViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final GirlInfo info = getItem(position);
        if(holder instanceof ViewHolder){
            ViewDataBinding binding = ((ViewHolder) holder).getBinding();
            binding.setVariable(BR.info, info);

            binding.executePendingBindings();
        }
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public ViewDataBinding getBinding() {
            return mBinding;
        }
    }

    private MutableRepository<ApiResult<GirlInfo>> mRepository;

    @Override
    public void update() {
        ApiResult<GirlInfo> result = mRepository.get();
        if (!result.error) {

            if (result.results != null && result.results.size() > 0) {
                addAll(result.results, false);
                int size = result.results.size();
                notifyItemRangeInserted(getItemCount() - size, size);
            }
        }

    }

    public MutableRepository<ApiResult<GirlInfo>> getRepository() {
        return mRepository;
    }

    public void setRepository(MutableRepository<ApiResult<GirlInfo>> repository) {
        mRepository = repository;
        mRepository.addUpdatable(this);
    }
}
