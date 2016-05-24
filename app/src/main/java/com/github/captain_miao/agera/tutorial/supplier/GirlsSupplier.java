package com.github.captain_miao.agera.tutorial.supplier;

import android.support.annotation.NonNull;

import com.github.captain_miao.agera.tutorial.http.DemoApiService;
import com.github.captain_miao.agera.tutorial.http.RetrofitServiceFactory;
import com.github.captain_miao.agera.tutorial.model.ApiResult;
import com.github.captain_miao.agera.tutorial.model.GirlInfo;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;

import java.io.IOException;

/**
 * @author YanLu
 * @since 16/5/16
 */
public class GirlsSupplier implements Supplier<Result<ApiResult<GirlInfo>>> {
    private String baseUrl = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/";
    private Supplier<Integer> mSupplierPagination;

    public GirlsSupplier(@NonNull Supplier<Integer> supplier ) {
        mSupplierPagination = supplier;
    }

    @NonNull
    @Override
    public Result<ApiResult<GirlInfo>> get() {
        return loadGirls();
    }

    private Result<ApiResult<GirlInfo>> loadGirls() {
        DemoApiService apiService = RetrofitServiceFactory.createService(DemoApiService.class, baseUrl);
        ApiResult<GirlInfo> girlInfos = null;
        try {
            girlInfos = (apiService.getGirls(mSupplierPagination.get()).execute().body());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(girlInfos != null){
            return !girlInfos.error ? Result.success(girlInfos) : Result.<ApiResult<GirlInfo>>failure();
        } else {
            return Result.failure();
        }
    }


}
