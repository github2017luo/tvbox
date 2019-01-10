package cn.a88sv.cn.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

import cn.a88sv.cn.R;
import cn.a88sv.cn.adapter.ChannelsAdapter;
import cn.a88sv.cn.bean.Channels;
import cn.a88sv.cn.sql.DataDao;
import cn.a88sv.cn.view.SideslipListView;


public class MainActivity extends Activity {

    private SideslipListView mSideslipListView;

    private ChannelsAdapter channelsAdapter;
    private LinearLayout linearLayout;
    private AlertDialog loaddialog;
    //第一批数据
    private ArrayList<Channels> mfirsrlist;
    //更多数据
    private ArrayList<Channels> mMorelist;


    private DataDao dataDao;

    private int mStartIndex =0;
    private int mMaxCount = 30;
    private int mTotalCont = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        setContentView(R.layout.activity_main);
        dataDao = new DataDao(MainActivity.this);
        if(dataDao.getTotalCount() == 0) {
            System.out.println("正在访问网络");
            new cn.a88sv.cn.DownloadThread(MainActivity.this,dataDao).start();
        }
        initView();
        initData();
        mSideslipListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Channels channelsBean =mfirsrlist.get(position);
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putCharSequence("channelsname",channelsBean.getChannelsname());
                bundle.putCharSequence("channelsurl",channelsBean.getChannelsurl());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    /*加载布局*/
    private void initView(){
        linearLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.loadlistlayout,null);
        mSideslipListView = (SideslipListView) findViewById(R.id.mainlistview);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(linearLayout);
        loaddialog =builder.create();
        mSideslipListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if(scrollState==SCROLL_STATE_IDLE){
                    int position = mSideslipListView.getLastVisiblePosition();
                    if (position == mfirsrlist.size()-1 && position !=mTotalCont -1){
                        mStartIndex +=mMaxCount;
                        new LoadDataTask().execute();
                    }else if(position == mfirsrlist.size()-1 && position == mTotalCont-1){
                        Toast.makeText(MainActivity.this,"没有更多频道了", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }
    /*
    加载数据
     */
    private void initData() {
        mfirsrlist = new ArrayList<Channels>();
        mMorelist = new ArrayList<Channels>();
        new LoadDataTask().execute();
    }
    /*
    加载服务器数据
     */
    class LoadDataTask extends AsyncTask<Void, Void, ArrayList<Channels>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loaddialog.show();
        }

        @Override
        protected ArrayList<Channels> doInBackground(Void... voids) {
            try{
                Thread.sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            if(mTotalCont== -1){
                mTotalCont=dataDao.getTotalCount();
            }
            mMorelist = dataDao.loadMore(mStartIndex,mMaxCount);
            return mMorelist;
        }

        @Override
        protected void onPostExecute(ArrayList<Channels> channelsBeans) {
            super.onPostExecute(channelsBeans);
            loaddialog.dismiss();
            mfirsrlist.addAll(mMorelist);
            if(channelsAdapter == null){
                channelsAdapter = new ChannelsAdapter(MainActivity.this,mfirsrlist);
                mSideslipListView.setAdapter(channelsAdapter);
            }else {
                channelsAdapter.notifyDataSetChanged();
            }
        }
    }
    //检测返回按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}

