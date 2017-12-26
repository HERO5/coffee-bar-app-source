package com.example.apple.bakebar.Util;

import android.graphics.Bitmap;
import android.os.Environment;

import com.example.apple.bakebar.Entity.ConstantsImages;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by apple on 2017/12/12.
 */

public class FileUtil {

    public static String saveTextToLocal(String url){
        String filePath = null;
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) { // SD卡根目录的hello.text
            filePath = Environment.getExternalStorageDirectory().toString() + File.separator + ConstantsImages.BASE_LOCAL_TEXT_URL + UuId.getIdByDate()+".txt";
        } else {// 系统下载缓存根目录的hello.text
            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + ConstantsImages.BASE_LOCAL_TEXT_URL +UuId.getIdByDate()+".txt";
        }
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(url.getBytes());
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public static String saveImageToLocal(Bitmap mBitmap){
        String filePath = null;
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) { // SD卡根目录的hello.text
            filePath = Environment.getExternalStorageDirectory().toString() + File.separator + ConstantsImages.BASE_LOCAL_IMG_URL;
        } else {// 系统下载缓存根目录的hello.text
            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + ConstantsImages.BASE_LOCAL_IMG_URL;
        }
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.mkdir();
            }
            FileOutputStream outStream = new FileOutputStream(filePath+ UuId.getIdByDate()+".jpg");
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            filePath=null;
            e.printStackTrace();
        }
        return filePath;
    }
}
