package com.github.captain_miao.agera.tutorial;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.github.captain_miao.agera.tutorial.base.BaseActivity;
import com.github.captain_miao.agera.tutorial.databinding.ChangeTxtColorBinding;
import com.github.captain_miao.agera.tutorial.helper.MockRandomData;
import com.github.captain_miao.agera.tutorial.observable.OnClickObservable;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.google.android.agera.Updatable;

public class SimpleActivityA extends BaseActivity implements Updatable {

    private ChangeTxtColorBinding mBinding;

    @Override
    public void init(Bundle savedInstanceState) {

        mBinding = DataBindingUtil.setContentView(this, R.layout.change_txt_color);
        setUpRepository();
        mBinding.setObservable(mObservable);
    }


    //for agera
    private OnClickObservable mObservable;
    private Repository<Result<Integer>> mRepository;


    @Override
    protected void onResume() {
        super.onResume();
        mRepository.addUpdatable(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRepository.removeUpdatable(this);
    }

    private void setUpRepository() {
        mObservable = new OnClickObservable() {
            @Override
            public void onClick( ) {
                dispatchUpdate();
            }
        };
        Supplier<Result<Integer>> supplier = new Supplier<Result<Integer>>() {
            @NonNull
            @Override
            public Result<Integer> get() {
                return Result.success(MockRandomData.getRandomColor());
            }
        };

        mRepository = Repositories.repositoryWithInitialValue(Result.<Integer>absent())
                .observe(mObservable)
                .onUpdatesPerLoop()
                .thenGetFrom(supplier)
                .compile();
    }

    @Override
    public void update() {
        if(mRepository.get().succeeded()) {
            mBinding.setTxtColor(mRepository.get().get());
        }
    }
}
