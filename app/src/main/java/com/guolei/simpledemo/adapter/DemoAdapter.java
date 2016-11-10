package com.guolei.simpledemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guolei.simpledemo.bean.DemoBean;
import com.guolei.simpledemo.R;
import com.guolei.simpledemo.util.DateUtil;
import com.guolei.simpledemo.util.EeyesNewsTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lei Guo on 2016/11/9.
 */

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

        holder.timeTextView.setText("活动时间：" + DateUtil.getDateToString(Long.parseLong(item.getActime())));

        holder.typeTextView.setText("活动类型：" + EeyesNewsTypes.getTypeName(Integer.parseInt(item.getTypeid())));

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
