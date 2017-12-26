package com.example.apple.bakebar.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.apple.bakebar.R;

/**
 * Created by apple on 2017/10/31.
 */

public class XGalleryAdapter extends PagerAdapter {

    private static final int[] DRAWABLES = {
            R.mipmap.cake,
            R.mipmap.coffee
    };

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int drawable = DRAWABLES[position % DRAWABLES.length];

        View view = View.inflate(container.getContext(), R.layout.x_gallery_adapter_view, null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        iv.setImageResource(drawable);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object != null && object instanceof View) {
            container.removeView((View) object);
        }
    }
}
