package com.example.apple.bakebar.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.apple.bakebar.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by apple on 2017/10/30.
 */

public class CallSystemCamera extends AppCompatActivity{

    public final int TYPE_TAKE_PHOTO = 1;//Uri获取类型判断

    public final int REQUEST_THUMBNAIL = 1;// 请求缩略图信号标识
    public final int REQUEST_ORIGINAL = 2;// 请求原图信号标识

    private ImageView imageView;

    private Uri photoUri;

    private boolean granted = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_view);

        imageView = (ImageView) findViewById(R.id.camera_result);
//        checkSelfPermission();
        if (granted){
            startCamera();
        }else {
            this.finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        switch (requestCode) {
            case REQUEST_THUMBNAIL:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        if (data.hasExtra("data")) {
                            bitmap = data.getParcelableExtra("data");
                        }
                    } else {
                        if (Build.VERSION.SDK_INT >= 24){
                            try {
                                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(photoUri));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }else {
                            bitmap = BitmapFactory.decodeFile(photoUri.getPath());
                        }
                    }
                }
                break;
        }
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
        }else{
            finish();
        }
    }

    public void startCamera(){
        if (Build.VERSION.SDK_INT >= 24) {
            Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoUri = get24MediaFileUri(TYPE_TAKE_PHOTO);
            this.photoUri = photoUri;
            takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            takeIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
            takeIntent.putExtra("return-data", true);
            startActivityForResult(takeIntent, REQUEST_THUMBNAIL);
        } else {
            Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoUri = getMediaFileUri(TYPE_TAKE_PHOTO);
            this.photoUri = photoUri;
            takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            takeIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
            takeIntent.putExtra("return-data", true);
            startActivityForResult(takeIntent, REQUEST_THUMBNAIL);
        }
    }

    public Uri getMediaFileUri(int type){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Bake Bar Album");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        //创建Media File
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == TYPE_TAKE_PHOTO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return Uri.fromFile(mediaFile);
    }

    /**
     * 版本24以上
     * 注意:get24MediaFileUri()与getMediaFileUri()唯一的不同为：api24以下，使用的是Uri.fromFile(File)获取的Uri，api24及以上必须使用FileProvider，
     * 调用FileProvider.getUriForFile(this, getPackageName()+".fileprovider", File)来获取Uri。
     */
    public Uri get24MediaFileUri(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "相册名字");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        //创建Media File
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == TYPE_TAKE_PHOTO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return FileProvider.getUriForFile(this, getPackageName()+".CallSystemCamera", mediaFile);
    }

    /**
     * 请求权限的回调
     *
     * 参数1：requestCode-->是requestPermissions()方法传递过来的请求码。
     * 参数2：permissions-->是requestPermissions()方法传递过来的需要申请权限
     * 参数3：grantResults-->是申请权限后，系统返回的结果，PackageManager.PERMISSION_GRANTED表示授权成功，PackageManager.PERMISSION_DENIED表示授权失败。
     * grantResults和permissions是一一对应的
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0) {
            granted = grantResults[0] == PackageManager.PERMISSION_GRANTED;//是否授权，可以根据permission作为标记
        }
    }
}
