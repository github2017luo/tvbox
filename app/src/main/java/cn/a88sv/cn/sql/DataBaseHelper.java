package cn.a88sv.cn.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DataBaseHelper extends SQLiteOpenHelper{

    private Context mContext;
    public static String CREAT_TVBOX = "create table channels (" + "id integer primary key autoincrement," + "channelsname text,"+"channelsUrl text, " + "channelsImage text)";
    public DataBaseHelper(Context context){
        super(context,"TVBOX.db",null,1);
        this.mContext=context;
    }
    public DataBaseHelper(Context context, int version){
        super(context,"TVBOX.db",null,version);
        this.mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_TVBOX);
        Toast.makeText(mContext,"初始化节目数据库成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
