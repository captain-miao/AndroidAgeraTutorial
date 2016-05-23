package com.github.captain_miao.agera.tutorial.recycleview;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.github.captain_miao.agera.tutorial.BR;
import com.github.captain_miao.agera.tutorial.R;
import com.github.captain_miao.agera.tutorial.databinding.RecyclerItemViewBinding;
import com.github.captain_miao.agera.tutorial.model.GirlInfo;
import com.google.android.agera.Result;
import com.google.android.agera.rvadapter.RepositoryPresenter;

import java.util.List;

/**
 * @author YanLu
 * @since 16/5/23
 */
public class GirlInfoPresenter extends RepositoryPresenter<Result<List<GirlInfo>>> {

    @Override
    public int getItemCount(@NonNull Result<List<GirlInfo>> data) {
        if (data.succeeded()) {
            return data.get().size();
        }
        return 0;
    }

    @Override
    public int getLayoutResId(@NonNull Result<List<GirlInfo>> data, int index) {
        return R.layout.recycler_item_view;
    }

    @Override
    public void bind(@NonNull Result<List<GirlInfo>> data, int index, @NonNull RecyclerView.ViewHolder holder) {
        if (data.isAbsent() || data.failed()) {
            return;
        }
        final GirlInfo info = data.get().get(index);
        final RecyclerItemViewBinding binding = DataBindingUtil.bind(holder.itemView);
        binding.setVariable(BR.info, info);
    }
}
