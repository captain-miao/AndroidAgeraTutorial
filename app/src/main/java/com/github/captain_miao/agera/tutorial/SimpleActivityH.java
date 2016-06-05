package com.github.captain_miao.agera.tutorial;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.github.captain_miao.agera.tutorial.base.BaseActivity;
import com.github.captain_miao.agera.tutorial.databinding.ActivityAgeraReservoirBinding;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Reservoir;
import com.google.android.agera.Reservoirs;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.google.android.agera.Updatable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleActivityH extends BaseActivity implements Updatable {

    private ActivityAgeraReservoirBinding mBinding;

    @Override
    public void init(Bundle savedInstanceState) {

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_agera_reservoir);
        setUpRepository();
        mBinding.setObservable(mReservoir);
    }


    //for agera
    private ExecutorService mExecutor;
    private Reservoir<Integer> mReservoir;
    private Repository<Result<Integer>> mRepository;

    private int mCount = 0;
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExecutor.shutdown();
    }

    private void setUpRepository() {
        mExecutor = Executors.newSingleThreadExecutor();
        mReservoir = Reservoirs.reservoir();
        Supplier<Result<Integer>> supplier = new Supplier<Result<Integer>>() {
            @NonNull
            @Override
            public Result<Integer> get() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mReservoir.get();// consume receiver
                return Result.success(++mCount);
            }
        };
        mRepository = Repositories.repositoryWithInitialValue(Result.<Integer>absent())
                .observe(mReservoir)
                .onUpdatesPerLoop()
                .goTo(mExecutor)
                .thenGetFrom(supplier)
                .compile();
    }

    @Override
    public void update() {
        if(mRepository.get().succeeded()) {
            mBinding.setValue(mRepository.get().get());
        }
    }
}
