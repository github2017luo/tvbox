package cn.a88sv.cn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import cn.a88sv.cn.R;
import cn.a88sv.cn.bean.Channels;


public class ChannelsAdapter extends BaseAdapter {

    private ArrayList<Channels> list;
    private Context mContext;
    public ChannelsAdapter(Context context, ArrayList<Channels> list){
        this.list=list;
        this.mContext=context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView==null){
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.listitem,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView =(ImageView) convertView.findViewById(R.id.imageview);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.channels_name);
            viewHolder.txtv_delete = (TextView) convertView.findViewById(R.id.txtv_delete);
            convertView.setTag(viewHolder);
        }else{
            viewHolder =(ViewHolder)convertView.getTag();
        }
        Channels channels = list.get(position);
        viewHolder.imageView.setImageResource(R.drawable.icon);
        viewHolder.textView.setText(channels.getChannelsname());
        viewHolder.txtv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
    class ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView txtv_delete;
    }
}
