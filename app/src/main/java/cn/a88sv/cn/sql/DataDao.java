package cn.a88sv.cn.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import cn.a88sv.cn.bean.Channels;


public class DataDao {

    private DataBaseHelper dataBaseHelper1;
    private DataBaseHelper dataBaseHelper2;

    public DataDao(Context context){
        dataBaseHelper1 = new DataBaseHelper(context);
        dataBaseHelper2 = new DataBaseHelper(context,2);
    }



    /*
    * 添加频道
    * */
    public boolean addChannels(Channels channels){
        SQLiteDatabase db =  dataBaseHelper2.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("channelsname",channels.getChannelsname());
        values.put("channelsUrl", channels.getChannelsurl());
        values.put("channelsImage", channels.getChanelsImageurl());
        long rowId = db.insert("channels", null,values);
        db.close();
        if(rowId == -1) {
            return false;
        }else {
            return true;
        }
    }

    /*
    * 分批加载
    * */
    public ArrayList<Channels> loadMore(int startIndex, int maxCount){
        // limit 表示 限制当前有多少数据
        // offset 表示 跳过，从第几条开始
        // sql 语句含义：
        // 假设 startIndex 为 19， maxCount 为 20：
        // 查询从第 (19 + 1) = 20 条数据开始，往后的 20 条数据
        SQLiteDatabase db = dataBaseHelper2.getReadableDatabase();
        Cursor cursor = db.rawQuery("select *from channels" +" limit? offset?", new String[]{String.valueOf(maxCount) ,String.valueOf(startIndex)});
        ArrayList<Channels> moredataList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                String channels_name = cursor.getString(cursor.getColumnIndex("channelsname"));
                String channels_url = cursor.getString(cursor.getColumnIndex("channelsUrl"));
                String channenls_Image = cursor.getString(cursor.getColumnIndex("channelsImage"));
                Channels channelsBean = new Channels(channels_name,channels_url,channenls_Image);
                moredataList.add(channelsBean);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return moredataList;
    }
    /*
    * 查询总数
    * */
    public int getTotalCount(){
        int totalCount=-1;
        SQLiteDatabase db= dataBaseHelper2.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(channelsname) from " + "channels;",null);
        if(cursor.moveToFirst()) {
            totalCount = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return totalCount;
    }

}
