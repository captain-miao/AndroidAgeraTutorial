package com.github.captain_miao.agera.tutorial.recycleview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.github.captain_miao.agera.tutorial.R;
import com.github.captain_miao.agera.tutorial.base.BaseActivity;
import com.github.captain_miao.agera.tutorial.helper.PicassoOnScrollListener;
import com.github.captain_miao.agera.tutorial.model.ApiResult;
import com.github.captain_miao.agera.tutorial.model.GirlInfo;
import com.github.captain_miao.agera.tutorial.supplier.GirlsSupplier;
import com.github.captain_miao.recyclerviewutils.WrapperRecyclerView;
import com.github.captain_miao.recyclerviewutils.common.DefaultLoadMoreFooterView;
import com.github.captain_miao.recyclerviewutils.listener.RefreshRecyclerViewListener;
import com.google.android.agera.Function;
import com.google.android.agera.MutableRepository;
import com.google.android.agera.Receiver;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Updatable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.google.android.agera.Result.absentIfNull;

public class ComplexRecycleViewActivity extends BaseActivity implements RefreshRecyclerViewListener, Updatable, Receiver<ApiResult<GirlInfo>> {
    private static final String TAG = "RecycleViewActivity";

    private WrapperRecyclerView mRefreshRecyclerView;
    private ComplexRvAdapter mAdapter;
    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recycle_view);
        mRefreshRecyclerView = (WrapperRecyclerView) findViewById(R.id.refresh_recycler_view);
        mAdapter = new ComplexRvAdapter();
        mRefreshRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRefreshRecyclerView.setLayoutManager(linearLayoutManager);
        mRefreshRecyclerView.setRecyclerViewListener(this);
        mAdapter.setLoadMoreFooterView(new DefaultLoadMoreFooterView(this));
        mRefreshRecyclerView.setPadding(0, 0, 0, 20);

        mRefreshRecyclerView.getRecyclerView().addOnScrollListener(new PicassoOnScrollListener(this));

        setUpRepository();
        mAdapter.setRepository(Repositories.mutableRepository(new ApiResult<GirlInfo>()));
        //mRefreshRecyclerView.getPtrFrameLayout().autoRefresh();
    }


    @Override
    public void onRefresh() {
        mPagination = 1;
        mMutableRepository.accept(mPagination);
    }

    @Override
    public void onLoadMore(int pagination, int pageSize) {
        mRefreshRecyclerView.showLoadMoreView();
        mPagination = pagination;
        mMutableRepository.accept(mPagination);
    }


    //for agera
    private ExecutorService networkExecutor;
    private MutableRepository<Integer> mMutableRepository;
    private Repository<Result<ApiResult<GirlInfo>>> mLoadDataRepository;

    @Override
     protected void onResume() {
         super.onResume();
        mLoadDataRepository.addUpdatable(this);
     }

     @Override
     protected void onPause() {
         super.onPause();
         mLoadDataRepository.removeUpdatable(this);
     }

     @Override
     protected void onDestroy() {
         super.onDestroy();
         networkExecutor.shutdown();
     }

    private int mPagination = 1;

    private void setUpRepository() {
        networkExecutor = Executors.newSingleThreadExecutor();

        mMutableRepository = Repositories.mutableRepository(mPagination);

        mLoadDataRepository = Repositories.repositoryWithInitialValue(Result.<ApiResult<GirlInfo>>absent())
                .observe(mMutableRepository)
                .onUpdatesPerLoop()
                .goTo(networkExecutor)
                .attemptGetFrom(new GirlsSupplier(mMutableRepository)).orSkip()
                .goLazy()
                .thenTransform(new Function<ApiResult<GirlInfo>, Result<ApiResult<GirlInfo>>>() {
                    @NonNull
                    @Override
                    public Result<ApiResult<GirlInfo>> apply(@NonNull ApiResult<GirlInfo> input) {
                        return absentIfNull(input);
                    }
                })
                .compile();

    }

    @Override
    public void update() {
        Result<ApiResult<GirlInfo>> result = mLoadDataRepository.get();

        result.ifSucceededSendTo(this)
                .ifFailedSendTo(new Receiver<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable value) {
                        Toast.makeText(ComplexRecycleViewActivity.this, "load data fail", Toast.LENGTH_LONG).show();
                        if (mPagination == 1) {
                            mRefreshRecyclerView.refreshComplete();
                        } else {
                            mRefreshRecyclerView.hideFooterView();
                        }
                        mRefreshRecyclerView.loadMoreComplete();
                    }
                });

    }


    @Override
    public void accept(@NonNull ApiResult<GirlInfo> result) {
        if (mPagination == 1) {
            mAdapter.clear();
            mAdapter.addAll(result.results);
            mRefreshRecyclerView.refreshComplete();
        } else {
            if (result.results != null && result.results.size() > 0) {
                mAdapter.getRepository().accept(result);
                //mAdapter.addAll(result.results, false);
                //int size = result.results.size();
                //mAdapter.notifyItemRangeInserted(mAdapter.getItemCount() - size, size);
                mRefreshRecyclerView.hideFooterView();
            } else {
                Toast.makeText(this, "It's no more data.", Toast.LENGTH_LONG).show();
                if(mAdapter.getItemCount() > 0) {
                    mRefreshRecyclerView.showNoMoreDataView();
                } else {
                    mRefreshRecyclerView.hideFooterView();
                }
            }
            mRefreshRecyclerView.loadMoreComplete();
        }
    }
}