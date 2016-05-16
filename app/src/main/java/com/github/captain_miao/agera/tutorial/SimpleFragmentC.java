package com.github.captain_miao.agera.tutorial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.captain_miao.agera.tutorial.base.BaseFragment;
import com.github.captain_miao.agera.tutorial.databinding.LoadImageByPicassoBinding;

/**
 * @author YanLu
 * @since 16/4/26
 */
public class SimpleFragmentC extends BaseFragment implements View.OnClickListener {
    private LoadImageByPicassoBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = LoadImageByPicassoBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBinding.btnChangeImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //mBinding.setImgUrl(MockRandomData.getRandomImage());
    }

}
