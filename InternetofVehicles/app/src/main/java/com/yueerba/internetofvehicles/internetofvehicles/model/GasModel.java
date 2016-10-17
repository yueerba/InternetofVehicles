package com.yueerba.internetofvehicles.internetofvehicles.model;

import com.yueerba.internetofvehicles.internetofvehicles.api.GasService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/9/5.
 */

public class GasModel {
    //http://apis.juhe.cn/oil/local?key=11af0a3b955891bdc68176f262163204&lon=116.403119&lat=39.916042&format=2&r=3000
    public static final String BASE_URL = "http://apis.juhe.cn"; // HOST地址

    private GasService service;

    //获取MedModel实例
    public static GasModel getModel(){
        return GasHolder.gasModel;
    }

    //内部类实现单例模式，延迟加载，线程安全（java中class加载时互斥的）,也减少了内存消耗
    private static class GasHolder{
        private static GasModel gasModel = new GasModel();//单例对象实例
    }

    private GasModel(){//private的构造函数用于避免外界直接使用new来实例化对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //转换服务器数据到对象
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(GasService.class);
    }

    //获取PhoneService实例
    public GasService getService(){
        return service;
    }
}
