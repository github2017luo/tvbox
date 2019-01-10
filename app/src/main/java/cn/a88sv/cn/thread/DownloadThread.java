package cn.a88sv.cn;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.a88sv.cn.bean.Channels;
import cn.a88sv.cn.sql.DataDao;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadThread extends Thread {

    private Context mContent;
    private DataDao dataDao;

    OkHttpClient client = new OkHttpClient();
    String tvurl="http://music.88sv.cn/12.txt";
    String tvlisturl;
    Response response;
    Request request;

    public DownloadThread(Context context, DataDao dataDao){
        this.mContent=context;
        this.dataDao=dataDao;
    }
    @Override
    public void run() {
        super.run();
        request = new Request.Builder().url(tvurl).build();
        try {
            response = client.newCall(request).execute();
            tvlisturl =response.body().string();
            JSONObject jsonObject = null;

            jsonObject = new JSONObject(tvlisturl);
            JSONArray jsonArray = jsonObject.getJSONArray("channels");
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObjectSon= (JSONObject)jsonArray.opt(i);
                Channels channels=new Channels(jsonObjectSon.getString("name"),jsonObjectSon.getString("url"),"1121");
                dataDao.addChannels(channels);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
