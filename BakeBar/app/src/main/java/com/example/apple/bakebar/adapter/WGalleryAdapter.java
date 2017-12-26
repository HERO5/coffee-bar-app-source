package com.example.apple.bakebar.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.apple.bakebar.Entity.ConstantsImages;
import com.example.apple.bakebar.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.wgallery.android.IWGalleryAdapter;

/**
 * Created by apple on 2017/10/31.
 */

public class WGalleryAdapter extends BaseAdapter implements IWGalleryAdapter {

    @Override
    public int getChangeAlphaViewId() {
        return R.id.border;
    }

    private LayoutInflater inflater;

    private DisplayImageOptions options;

    private int repertoryId;

    private String[] urls;

    public WGalleryAdapter(Context context,int repertoryId) {

        this.repertoryId = repertoryId;

        this.urls = getImageUrl();

        inflater = LayoutInflater.from(context);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_stub)
                .showImageForEmptyUri(R.mipmap.ic_empty)
                .showImageOnFail(R.mipmap.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(20))
                .build();
    }

    public WGalleryAdapter(Context context,String[] urls){
        this.urls = urls;
        inflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_stub)
                .showImageForEmptyUri(R.mipmap.ic_empty)
                .showImageOnFail(R.mipmap.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(20))
                .build();
    }

    @Override
    public int getCount() {
        return urls.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.w_gallery_adapter_view,
                    parent, false);
        }
        ImageView iv_small = (ImageView) convertView.findViewById(R.id.iv);
        ImageLoader.getInstance().displayImage(urls[position], iv_small, options);
        return convertView;
    }

    public String[] getImageUrl(){
        switch (repertoryId){
            case 1:
                return ConstantsImages.BAKE_STUDIO;
            case 2:
                return ConstantsImages.BAKE_CATEGORIES;
            case 3:
                return ConstantsImages.BAKE_SHOP;
            case 4:
                return ConstantsImages.REPERTORY_CAKE;
            case 5:
                return ConstantsImages.REPERTORY_COFFEE;
            default:
                return null;
        }
    }
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.w_gallery_adapter_view,
//                    parent, false);
//        }
//
//        ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
//        iv.setImageResource(
//                parent.getContext().getResources().getIdentifier("icon_cake" + position, "mipmap",
//                        parent.getContext().getPackageName()));
//        return convertView;
//    }

}
