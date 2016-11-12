package com.guolei.simpledemo.network;

import com.guolei.simpledemo.bean.DemoBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Lei Guo on 2016/11/9.
 */

public interface ApiStores {

    /**
     * 用于获取当前活动信息
     * @param limit 每一个的活动个数
     * @param page 当前页码
     * @return 活动信息的Observable
     */
    @GET("index.php/Api/getAcInfo")
    Observable<List<DemoBean>> getLatestAllAcItemsByPage(@Query("limit") int limit, @Query("page") int page);
}
