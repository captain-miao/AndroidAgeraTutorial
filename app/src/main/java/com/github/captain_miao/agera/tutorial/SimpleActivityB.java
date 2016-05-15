package com.github.captain_miao.agera.tutorial;

import android.os.Bundle;
import android.view.View;

import com.github.captain_miao.agera.tutorial.base.BaseActivity;
import com.github.captain_miao.agera.tutorial.databinding.LoadImageByPicassoBinding;
import com.github.captain_miao.agera.tutorial.helper.MockRandomData;

public class SimpleActivityB extends BaseActivity implements View.OnClickListener {
    private LoadImageByPicassoBinding mBinding;
    @Override
    public void init(Bundle savedInstanceState) {
        mBinding = LoadImageByPicassoBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.btnChangeImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mBinding.setImgUrl(MockRandomData.getRandomErrorImage());
    }

}