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

    private RecyclerView recyclerView;
    private List<DemoBean> list = new ArrayList<>();
    private DemoAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout refresh;
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        apiStores = retrofit.create(ApiStores.class);



        initViews();

    }

    private void initViews(){

        recyclerView.setLayoutManager(linearLayoutManager);

        refresh.setColorSchemeResources(android.R.color.holo_blue_dark, android.R.color.holo_green_light,android.R.color.holo_orange_light, android.R.color.holo_red_light);
        refresh.setDistanceToTriggerSync(300);
        refresh.setProgressBackgroundColorSchemeColor(Color.WHITE);
        refresh.setSize(SwipeRefreshLayout.DEFAULT);
        recyclerView.setAdapter(adapter);


        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!list.isEmpty()){
                    list.clear();
                }
                loadData(20, 1);
            }
        });

        loadData(20,1);

    }

    private void loadData(int limit, int page){

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected){
            Observable<List<DemoBean>> observable = apiStores.getLatestAllAcItemsByPage(limit, page);
            observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<List<DemoBean>>() {
                        @Override
                        public void onCompleted() {
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

                        }

                        @Override
                        public void onNext(List<DemoBean> demoBeen) {
                            for (int i = 0; i < demoBeen.size(); i++){
                                list.add(demoBeen.get(i));
                            }
                            adapter.notifyDataSetChanged();

                        }
                    });
        }else {
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
