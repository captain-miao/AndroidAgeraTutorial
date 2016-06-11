package com.github.captain_miao.agera.tutorial;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.github.captain_miao.agera.tutorial.base.BaseActivity;
import com.github.captain_miao.agera.tutorial.databinding.ActivityAgeraFunctionsBinding;
import com.github.captain_miao.agera.tutorial.helper.MockRandomData;
import com.github.captain_miao.agera.tutorial.observable.OnClickObservable;
import com.github.captain_miao.agera.tutorial.supplier.ImageSupplier;
import com.google.android.agera.Function;
import com.google.android.agera.Functions;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.google.android.agera.Updatable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.github.captain_miao.agera.tutorial.helper.MockRandomData.sImageSize;

public class SimpleActivityI extends BaseActivity implements Updatable {

    private ActivityAgeraFunctionsBinding mBinding;

    @Override
    public void init(Bundle savedInstanceState) {

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_agera_functions);
        setUpRepository();
        mBinding.setObservable(mObservable);
    }


    //for agera
    private ExecutorService mExecutor;
    private OnClickObservable mObservable;
    private Function<String, Result<Bitmap>> mFunction;
    private Repository<Result<Bitmap>> mRepository;

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
        mObservable = new OnClickObservable() {
            @Override
            public void onClick() {
                mCount++;
                dispatchUpdate();
            }
        };

        mFunction = Functions
                .functionFrom(String.class)
                .apply(new Function<String, String>() {
                    @NonNull
                    @Override
                    public String apply(@NonNull String input) {
                        return input.replace(sImageSize[0], sImageSize[mCount % 3]);
                    }
                })
                .apply(new Function<String, Result<Bitmap>>() {
                    @NonNull
                    @Override
                    public Result<Bitmap> apply(@NonNull String input) {
                        return new ImageSupplier(input).get();
                    }
                })
                .thenApply(new Function<Result<Bitmap>, Result<Bitmap>>() {
                    @NonNull
                    @Override
                    public Result<Bitmap> apply(@NonNull Result<Bitmap> input) {
                        return input;
                    }
                });


        mRepository = Repositories.repositoryWithInitialValue(Result.<Bitmap>absent())
                .observe(mObservable)
                .onUpdatesPerLoop()
                .goTo(mExecutor)
                .getFrom(new Supplier<String>() {
                    @NonNull
                    @Override
                    public String get() {
                        return MockRandomData.sImages[5];
                    }
                })
                .thenTransform(mFunction)// works on work thread(mExecutor)
                .compile();
    }

    @Override
    public void update() {
        if(mRepository.get().succeeded()) {
            mBinding.setBitmap(mRepository.get().get());
        }
    }
}
