package me.yukang.tuling123;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yukang on 17-9-12.
 */

public class MyAdapter extends BaseAdapter {

    private List<ListData> lists;
    private Context context;
    private RelativeLayout layout;

    public MyAdapter(List<ListData> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(context);
        if (lists.get(position).getFlag() == ListData.RECEIVER)
            layout = (RelativeLayout) inflater.inflate(R.layout.left_item, null);
        if (lists.get(position).getFlag() == ListData.SEND)
            layout = (RelativeLayout) inflater.inflate(R.layout.right_item, null);

        TextView tv = layout.findViewById(R.id.tv);
        TextView tvTime = layout.findViewById(R.id.tvTime);
        tvTime.setText(lists.get(position).getTime());
        tv.setText(lists.get(position).getContent());

        return layout;
    }
}
