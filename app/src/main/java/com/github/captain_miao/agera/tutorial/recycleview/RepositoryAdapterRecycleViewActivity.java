package com.github.captain_miao.agera.tutorial.recycleview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;

import com.github.captain_miao.agera.tutorial.R;
import com.github.captain_miao.agera.tutorial.base.BaseActivity;
import com.github.captain_miao.agera.tutorial.listener.SimpleObservable;
import com.github.captain_miao.agera.tutorial.model.ApiResult;
import com.github.captain_miao.agera.tutorial.model.GirlInfo;
import com.github.captain_miao.agera.tutorial.supplier.GirlsSupplier;
import com.github.captain_miao.recyclerviewutils.WrapperRecyclerView;
import com.github.captain_miao.recyclerviewutils.listener.RefreshRecyclerViewListener;
import com.google.android.agera.Function;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.RepositoryConfig;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.google.android.agera.rvadapter.RepositoryAdapter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RepositoryAdapterRecycleViewActivity extends BaseActivity implements RefreshRecyclerViewListener {
    private static final String TAG = "RepositoryAdapterRecycleViewActivity";

    private WrapperRecyclerView mRefreshRecyclerView;
    private RepositoryAdapter mRepositoryAdapter;
    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recycle_view);
        mRefreshRecyclerView = (WrapperRecyclerView) findViewById(R.id.refresh_recycler_view);

        setUpRepository();

        mRepositoryAdapter = RepositoryAdapter.repositoryAdapter()
                .add(mRepository, new GirlInfoPresenter())
                .build();

        mRefreshRecyclerView.getRecyclerView().setAdapter(mRepositoryAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRefreshRecyclerView.setLayoutManager(linearLayoutManager);
        mRefreshRecyclerView.setRecyclerViewListener(this);
        mRefreshRecyclerView.disableLoadMore();
        mRefreshRecyclerView.setPadding(0, 0, 0, 20);
    }


    @Override
    public void onRefresh() {
        mPagination = 1;
        mObservable.update();
        mRefreshRecyclerView.refreshComplete();
    }

    @Override
    public void onLoadMore(int pagination, int pageSize) {

    }

    // Agera
    //for agera
    private ExecutorService networkExecutor;
    private SimpleObservable mObservable;
    private Repository<Result<List<GirlInfo>>> mRepository;

    @Override
     protected void onResume() {
         super.onResume();
        mRepositoryAdapter.startObserving();
     }

     @Override
     protected void onPause() {
         super.onPause();
         mRepositoryAdapter.stopObserving();
     }

     @Override
     protected void onDestroy() {
         super.onDestroy();
         networkExecutor.shutdown();
     }
    private int mPagination = 1;
     private void setUpRepository() {
         networkExecutor = Executors.newSingleThreadExecutor();
         mObservable = new SimpleObservable() { };


         mRepository = Repositories.repositoryWithInitialValue(Result.<List<GirlInfo>>absent())
                 .observe(mObservable)
                 .onUpdatesPerLoop()
                 .goTo(networkExecutor)
                 .getFrom(new GirlsSupplier(new Supplier<Integer>() {
                     @NonNull
                     @Override
                     public Integer get() {
                         return mPagination;
                     }
                 }))
                 .thenTransform(new Function<Result<ApiResult<GirlInfo>>, Result<List<GirlInfo>>>() {
                     @NonNull
                     @Override
                     public Result<List<GirlInfo>> apply(@NonNull Result<ApiResult<GirlInfo>> input) {
                         if (input.succeeded() && !input.get().error) {
                             return Result.success(input.get().results);
                         } else {
                             return Result.absent();
                         }
                     }
                 })
                 .onDeactivation(RepositoryConfig.SEND_INTERRUPT)
                 .compile();
     }

}