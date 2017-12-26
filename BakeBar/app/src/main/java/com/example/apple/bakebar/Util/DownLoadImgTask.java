package com.example.apple.bakebar.Util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by apple on 2017/12/12.
 */

public class DownLoadImgTask extends AsyncTask<String, Integer, Void> {

    private Bitmap bitmap;
    private Activity activity;

    public DownLoadImgTask(Activity activity){
        this.activity = activity;
    }

    protected Void doInBackground(String... params) {
        bitmap=GetImageInputStream((String)params[0]);
        return null;
    }

    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        File file = new File(FileUtil.saveImageToLocal(bitmap));
        if(file!=null){
            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            LocalBroadcastManager.getInstance(activity).sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            Toast.makeText(activity, "Save succeed", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(activity, "Save Failed", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 获取网络图片
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
    public Bitmap GetImageInputStream(String imageurl){
        URL url;
        HttpURLConnection connection=null;
        Bitmap bitmap=null;
        try {
            url = new URL(imageurl);
            connection=(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(6000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream=connection.getInputStream();
            bitmap= BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
