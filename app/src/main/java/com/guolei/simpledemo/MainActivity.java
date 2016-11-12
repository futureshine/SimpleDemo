package com.guolei.simpledemo;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.guolei.simpledemo.adapter.DemoAdapter;
import com.guolei.simpledemo.bean.DemoBean;
import com.guolei.simpledemo.network.ApiStores;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    /* 加强版的ListView */
    private RecyclerView recyclerView;
    /* 当前的数据 */
    private List<DemoBean> list = new ArrayList<>();
    /* 当前RecyclerView的适配器 */
    private DemoAdapter adapter;
    /* 线性布局，还有其他诸如GridLayoutManager可以做出其他的效果，可以自己百度一下 */
    private LinearLayoutManager linearLayoutManager;
    /* 下拉刷新的组件 */
    private SwipeRefreshLayout refresh;
    /* 当前网络请求的BASE_URL */
    private static final String BASE_URL = "http://ticket.eeyes.net/";
    private ApiStores apiStores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        adapter = new DemoAdapter(this, list);
        /* Retrofit的初始化 */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        apiStores = retrofit.create(ApiStores.class);



        initViews();

    }

    private void initViews(){

        /* 给当前的recyclerView设置线性布局 */
        recyclerView.setLayoutManager(linearLayoutManager);

        /* 下拉刷新的组件初始化 */
        refresh.setColorSchemeResources(android.R.color.holo_blue_dark, android.R.color.holo_green_light,android.R.color.holo_orange_light, android.R.color.holo_red_light);
        refresh.setDistanceToTriggerSync(300);
        refresh.setProgressBackgroundColorSchemeColor(Color.WHITE);
        refresh.setSize(SwipeRefreshLayout.DEFAULT);
        /* 将recyclerView和adapter绑定 */
        recyclerView.setAdapter(adapter);

        /* 给下拉刷新组件设置下拉刷新的回调方法 */
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            /* 每次下拉刷新都将当前的list清空，然后去加载最新的数据 */
            @Override
            public void onRefresh() {
                if (!list.isEmpty()){
                    list.clear();
                }
                loadData(20, 1);
            }
        });
        /* app启动时第一次加载数据 */
        loadData(20,1);

    }

    private void loadData(int limit, int page){
        /* 判断设备是否接入互联网 */
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected){
            /* 网络请求 */
            Observable<List<DemoBean>> observable = apiStores.getLatestAllAcItemsByPage(limit, page);
            observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<List<DemoBean>>() {
                        @Override
                        public void onCompleted() {
                            /* 数据加载完成后让下拉刷新组件停止刷新，并显示提示信息 */
                            refresh.post(new Runnable() {
                                @Override
                                public void run() {
                                    refresh.setRefreshing(false);
                                }
                            });
                            Toast.makeText(MainActivity.this, R.string.load_complete, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            /* 数据加载出错显示提示信息 */
                            Toast.makeText(MainActivity.this, R.string.load_error, Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNext(List<DemoBean> demoBeen) {
                            /* 将获取到的数据放在list里面 */
                            for (int i = 0; i < demoBeen.size(); i++){
                                list.add(demoBeen.get(i));
                            }
                            /* 更新当前adapter */
                            adapter.notifyDataSetChanged();

                        }
                    });
        }else {
            /* 假如没有网络，则清空当前数据，并显示提示信息 */
            list.clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, R.string.no_network, Toast.LENGTH_SHORT).show();
            refresh.post(new Runnable() {
                @Override
                public void run() {
                    refresh.setRefreshing(false);
                }
            });
        }




    }
}
