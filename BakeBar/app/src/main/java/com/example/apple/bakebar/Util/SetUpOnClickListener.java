package com.example.apple.bakebar.Util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.apple.bakebar.Entity.ConstantsHttp;
import com.example.apple.bakebar.R;
import com.example.apple.bakebar.activity.ImageShower;
import com.example.apple.bakebar.activity.WRepertoryViewMainActivity;
import com.example.apple.bakebar.modle.UserMessage;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by apple on 2017/12/13.
 */

public class SetUpOnClickListener {

    public static void setImgCollectListener(View view, final String imgUrl, final Activity activity){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(InternetCheck.checkIfUrlExists(imgUrl)){
                    DownLoadImgTask download = new DownLoadImgTask(activity);
                    download.execute(imgUrl);
                }else{
                    Toast.makeText(activity, "internet is gone", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static void setImgDetialListener(View view, final String imgUrl, UserMessage userMessage){
        final UserMessage message = userMessage;
            view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(view.getContext(), ImageShower.class);
                intent.putExtra("userInfo", message);
                Bundle bundle = new Bundle();
                bundle.putString("imgUrl", imgUrl);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
    }

    public static void setImgWatchListener(View view,final Activity activity){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "Sorry,we get nothing", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void setViewMore(View view, final String repertory_name, final UserMessage userMessage){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMore(view,repertory_name,null,userMessage);
            }
        });
    }

    public static void setTypeListener(View view, final String queryKey, final Activity activity,final UserMessage userMessage){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doQuery(view,queryKey,activity,userMessage);
            }
        });
    }

    public static void loadImgArray(List<ImageView> views, final String[] urls){
        DisplayImageOptions userImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_stub)
                .showImageForEmptyUri(R.mipmap.ic_empty)
                .showImageOnFail(R.mipmap.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        for(int i=0;i<views.size();i++){
            ImageLoader.getInstance().displayImage(urls[i], views.get(i), userImageOptions);
        }
    }

    public static void doQuery(final View view, String queryKey, final Activity activity, final UserMessage userMessage){
        if (HttpUtils.isNetworkConnected(activity)) {
            final RequestParams requestParams=new RequestParams();
            requestParams.put("queryKey",queryKey);
            HttpUtils.post(ConstantsHttp.QUERY, requestParams, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    List<String> urls = JsonUtil.jsonsToObjects(new String(responseBody),String.class);
                    if(urls!=null&&urls.size()>0){
                        final String[] urlsStrs = new String[urls.size()];
                        int index=0;
                        for(String url:urls){
                            urlsStrs[index] = url;
                            index++;
                        }
                        viewMore(view,null,urlsStrs,userMessage);
                        if (activity instanceof WRepertoryViewMainActivity){
                            activity.finish();
                        }
                    }else{
                        Toast.makeText(activity, "Sorry,we get nothing", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(activity, "网络出现问题了", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(activity, "没有网络连接!", Toast.LENGTH_LONG).show();
        }
    }

    public static void viewMore(View view, String repertory_name, String[] urls, UserMessage userMessage){
        Intent intent = new Intent();
        intent.setClass(view.getContext(), WRepertoryViewMainActivity.class);
        intent.putExtra("userInfo",userMessage);
        Bundle bundle=new Bundle();
        if(urls!=null){
            bundle.putStringArray("imgUrls", urls);
        }else{
            bundle.putString("repertory_name", repertory_name);
        }
        intent.putExtras(bundle);
        view.getContext().startActivity(intent);
    }
}

