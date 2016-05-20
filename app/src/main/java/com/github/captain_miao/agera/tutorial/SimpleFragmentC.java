package com.github.captain_miao.agera.tutorial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.captain_miao.agera.tutorial.base.BaseFragment;
import com.github.captain_miao.agera.tutorial.databinding.LoadImageByUrlBinding;
import com.github.captain_miao.agera.tutorial.helper.MockRandomData;
import com.github.captain_miao.agera.tutorial.observable.OnClickObservable;
import com.google.android.agera.MutableRepository;
import com.google.android.agera.Repositories;
import com.google.android.agera.Updatable;

/**
 * @author YanLu
 * @since 16/4/26
 */
public class SimpleFragmentC extends BaseFragment implements Updatable {
    private LoadImageByUrlBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = LoadImageByUrlBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpRepository();
        mBinding.setObservable(mObservable);
    }


    //for agera
    private OnClickObservable mObservable;
    private MutableRepository<String> mRepository;


    @Override
    public void onResume() {
        super.onResume();
        mRepository.addUpdatable(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mRepository.removeUpdatable(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setUpRepository() {
        mObservable = new OnClickObservable() {
            @Override
            public void onClick( ) {
                mRepository.accept(MockRandomData.getRandomImage());
            }
        };

        mRepository = Repositories.mutableRepository(MockRandomData.getRandomImage());

        //initialization
        mRepository.accept(MockRandomData.getRandomImage());
    }

    @Override
    public void update() {
        String result = mRepository.get();
        mBinding.setImageUrl(result);
    }

}
