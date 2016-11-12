# SimpleDemo
e瞳App组培训，简单app的示例代码

#### 导入方法

修改gradle文件中的`buildToolsVersion`为你电脑里面有的版本

#### 代码流程

##### 新建工程，引入依赖

```java
    compile 'com.android.support:appcompat-v7:24.2.1'
    compile 'com.android.support:design:24.2.1'
    compile 'com.android.support:cardview-v7:24.2.1'
    compile 'com.android.support:recyclerview-v7:24.2.1'
    compile 'com.squareup.retrofit2:retrofit:2.0.2'
    compile 'com.squareup.retrofit2:converter-gson:2.0.2'
    compile 'com.squareup.retrofit2:adapter-rxjava:2.0.2'
    compile 'io.reactivex:rxandroid:1.1.0'
    compile 'io.reactivex:rxjava:1.1.0'
```

##### 编写Activity的主布局文件(activity_main.xml)

```java
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.guolei.simpledemo.MainActivity">
    <android.support.v4.widget.SwipeRefreshLayout
        android:id="@+id/refresh"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_alignParentTop="true"
        android:layout_alignParentLeft="true"
        android:layout_alignParentStart="true">
        <android.support.v7.widget.RecyclerView
            android:id="@+id/rv_main"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
    </android.support.v4.widget.SwipeRefreshLayout>
</RelativeLayout>
```

##### 编写业务数据结构(DemoBean)

```java
public class DemoBean {
    private String acid;
    private String actitle;
    private String acstart;
    private String acauthor;
    private String acsource;
    private String actext;
    private String acend;
    private String aclimit;
    private String actime;
    private String acnow;
    private String achave;
    private String acstate;
    private String typeid;
    private String actic;
    private String acwhere;
    private String upload;
    public String getAcid() {
        return acid;
    }
    public void setAcid(String acid) {
        this.acid = acid;
    }
    public String getActitle() {
        return actitle;
    }
    public void setActitle(String actitle) {
        this.actitle = actitle;
    }
    public String getAcstart() {
        return acstart;
    }
    public void setAcstart(String acstart) {
        this.acstart = acstart;
    }
    public String getAcauthor() {
        return acauthor;
    }
    public void setAcauthor(String acauthor) {
        this.acauthor = acauthor;
    }
    public String getAcsource() {
        return acsource;
    }
    public void setAcsource(String acsource) {
        this.acsource = acsource;
    }
    public String getActext() {
        return actext;
    }
    public void setActext(String actext) {
        this.actext = actext;
    }
    public String getAcend() {
        return acend;
    }
    public void setAcend(String acend) {
        this.acend = acend;
    }
    public String getAclimit() {
        return aclimit;
    }
    public void setAclimit(String aclimit) {
        this.aclimit = aclimit;
    }
    public String getActime() {
        return actime;
    }
    public void setActime(String actime) {
        this.actime = actime;
    }
    public String getAcnow() {
        return acnow;
    }
    public void setAcnow(String acnow) {
        this.acnow = acnow;
    }
    public String getAchave() {
        return achave;
    }
    public void setAchave(String achave) {
        this.achave = achave;
    }
    public String getAcstate() {
        return acstate;
    }
    public void setAcstate(String acstate) {
        this.acstate = acstate;
    }
    public String getTypeid() {
        return typeid;
    }
    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }
    public String getActic() {
        return actic;
    }
    public void setActic(String actic) {
        this.actic = actic;
    }
    public String getAcwhere() {
        return acwhere;
    }
    public void setAcwhere(String acwhere) {
        this.acwhere = acwhere;
    }
    public String getUpload() {
        return upload;
    }
    public void setUpload(String upload) {
        this.upload = upload;
    }
}
```

##### 编写网络请求框架(ApiStores)

```java
public interface ApiStores {
    @GET("index.php/Api/getAcInfo")
    Observable<List<DemoBean>> getLatestAllAcItemsByPage(@Query("limit") int limit, @Query("page") int page);
}
```

##### 编写item的布局文件(universal_item_layout.xml)

```java
<?xml version="1.0" encoding="utf-8"?>
<android.support.v7.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:id="@+id/card_view_item"
    android:layout_marginTop="8dp">
    <LinearLayout
        android:id="@+id/recycler_view_item"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">
        <TextView
            android:id="@+id/titleTextView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_margin="@dimen/text_margin"
            android:textSize="@dimen/text_size_title"
            android:textColor="@color/primary_text"
            android:maxLines="1"
            android:ellipsize="end"
            android:text="RoboMaster2015|机器人大赛第三场"
            />
        <TextView
            android:id="@+id/whereTextView"
            android:layout_marginLeft="@dimen/text_margin"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="活动地点：思源活动中心"/>
        <TextView
            android:id="@+id/timeTextView"
            android:layout_marginLeft="@dimen/text_margin"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="活动时间：2015年5月24日"/>
        <TextView
            android:id="@+id/typeTextView"
            android:layout_marginLeft="@dimen/text_margin"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="活动类别：主题活动"/>
        <TextView
            android:id="@+id/cardTextView"
            android:layout_marginLeft="@dimen/text_margin"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="检票（刷卡）：是"/>
        <TextView
            android:id="@+id/contentTextView"
            android:layout_margin="@dimen/text_margin"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textColor="@color/primary_text"
            android:ellipsize="end"
            android:maxLines="1"
            android:text="全国首档激战类机器人竞赛 炫丽灯光，震撼音乐，烟雾喷火，互动游戏 现场让你high翻天。 享受顶尖科技盛宴， 见证工程师的青春与热血。 还等什么 赶紧来吧。"/>
    </LinearLayout>
</android.support.v7.widget.CardView>
```

其中在dimens.xml中追加定义

```java
<dimen name="text_margin">16dp</dimen>
<dimen name="text_size_title">15sp</dimen>
```

在colors.xml中追加定义

```java
<color name="primary_text">#212121</color>	
```

##### 编写adapter

```java
public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.DemoItemViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private List<DemoBean> list = new ArrayList<>();
    public DemoAdapter(Context context, List<DemoBean> list){
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public DemoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.universal_item_layout, parent, false);
        DemoItemViewHolder holder = new DemoItemViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(DemoItemViewHolder holder, int position) {
        DemoBean item = list.get(position);
        holder.titleTextView.setText(item.getActitle());
        holder.whereTextView.setText("活动地点：" + item.getAcwhere());
        holder.timeTextView.setText("活动时间：" + item.getActime());
        holder.typeTextView.setText("活动类型：" + item.getTypeid());
        holder.cardTextView.setText("检票（刷卡）：" + (item.getActic().equals("0")?"否":"是"));
        String contentHtml = item.getActext();
        contentHtml = contentHtml.replaceAll("<img .*?/>", "");
        holder.contentTextView.setText(Html.fromHtml(contentHtml));
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class DemoItemViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTextView;
        private TextView whereTextView;
        private TextView timeTextView;
        private TextView typeTextView;
        private TextView cardTextView;
        private TextView contentTextView;
        public DemoItemViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            whereTextView = (TextView) itemView.findViewById(R.id.whereTextView);
            timeTextView = (TextView) itemView.findViewById(R.id.timeTextView);
            typeTextView = (TextView) itemView.findViewById(R.id.typeTextView);
            cardTextView = (TextView) itemView.findViewById(R.id.cardTextView);
            contentTextView = (TextView) itemView.findViewById(R.id.contentTextView);
        }
    }
}
```

##### 编写主要逻辑(MainActivity)

```java
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        adapter = new DemoAdapter(this, list);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        apiStores = retrofit.create(ApiStores.class);
        initViews();
    }
```

```java
private void initViews(){
        recyclerView.setLayoutManager(linearLayoutManager);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark, android.R.color.holo_green_light,android.R.color.holo_orange_light, android.R.color.holo_red_light);
        refreshLayout.setDistanceToTriggerSync(300);
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!list.isEmpty()){
                    list.clear();
                }
                loadData(20, 1);
            }
        });
        loadData(20, 1);
    }
```

```java
private void loadData(int limit, int page){
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected){
            Observable<List<DemoBean>> observable = apiStores.getLatestAllAcItemsByPage(limit,page);
            observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<List<DemoBean>>() {
                        @Override
                        public void onCompleted() {
                            refreshLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    refreshLayout.setRefreshing(false);
                                }
                            });
                            Toast.makeText(MainActivity.this, "数据加载完成", Toast.LENGTH_SHORT).show();
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
            list.clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "请将设备接入互联网", Toast.LENGTH_SHORT).show();
        }
    }
```

##### 最后别忘了加上网络访问权限哦(AndroidManifest.xml)

```java
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```

