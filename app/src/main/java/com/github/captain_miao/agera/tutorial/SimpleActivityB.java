package com.github.captain_miao.agera.tutorial;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.github.captain_miao.agera.tutorial.base.BaseActivity;
import com.github.captain_miao.agera.tutorial.databinding.LoadImageByPicassoBinding;
import com.github.captain_miao.agera.tutorial.helper.MockRandomData;
import com.github.captain_miao.agera.tutorial.observable.OnClickObservable;
import com.github.captain_miao.agera.tutorial.supplier.ImageSupplier;
import com.google.android.agera.Function;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.google.android.agera.Updatable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleActivityB extends BaseActivity implements Updatable {
    private static final String TAG = "SimpleActivityB";
    private LoadImageByPicassoBinding mBinding;

    private ExecutorService networkExecutor;
    @Override
    public void init(Bundle savedInstanceState) {
        mBinding = LoadImageByPicassoBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setUpRepository();
        mBinding.setObservable(mObservable);
    }



    //for agera
    private OnClickObservable mObservable;
    private Repository<Result<Bitmap>> mRepository;


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
        networkExecutor.shutdown();
    }

    private void setUpRepository() {
        networkExecutor = Executors.newSingleThreadExecutor();
        mObservable = new OnClickObservable() {
            @Override
            public void onClick(View view) {
                dispatchUpdate();
            }
        };

        Supplier<String> imageUriSupplier = new Supplier<String>() {
            @NonNull
            @Override
            public String get() {
                return MockRandomData.getRandomImage();
            }
        };

        mRepository = Repositories.repositoryWithInitialValue(Result.<Bitmap>absent())
                .observe(mObservable)
                .onUpdatesPerLoop()
                .getFrom(imageUriSupplier)
                .goTo(networkExecutor)
                .thenTransform(new Function<String, Result<Bitmap>>() {
                    @NonNull
                    @Override
                    public Result<Bitmap> apply(@NonNull String input) {
                        return new ImageSupplier(input).get();
                    }
                })
                .compile();
    }

    @Override
    public void update() {
        Result<Bitmap> result = mRepository.get();
        if(result.succeeded()) {
            mBinding.setBitmap(result.get());
        } else {
            Toast.makeText(this, "load image fail", Toast.LENGTH_LONG).show();
        }
    }

}