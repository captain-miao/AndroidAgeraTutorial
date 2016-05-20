package com.github.captain_miao.agera.tutorial.http;

import com.github.captain_miao.agera.tutorial.model.ApiResult;
import com.github.captain_miao.agera.tutorial.model.GirlInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author YanLu
 * @since 16/5/20
 */
public interface DemoApiService {

    @GET("10/{pagination}")
    Call<ApiResult<GirlInfo>> getGirls(@Path("pagination") int pagination);
}
