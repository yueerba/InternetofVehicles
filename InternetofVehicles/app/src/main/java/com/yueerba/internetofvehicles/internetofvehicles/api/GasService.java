package com.yueerba.internetofvehicles.internetofvehicles.api;

import com.yueerba.internetofvehicles.internetofvehicles.Bean.GasResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/9/5.
 */
/* http://apis.juhe.cn/oil/local?key=11af0a3b955891bdc68176f262163204&lon=116.403119&lat=39.916042&format=2&r=3000*/
public interface GasService {
    @GET("/oil/local")
    Call<GasResult> getGasResult(
            @Query("key") String key,
            @Query("lon") double lon,
            @Query("lat") double lat,
            @Query("r") int r
    );
}
