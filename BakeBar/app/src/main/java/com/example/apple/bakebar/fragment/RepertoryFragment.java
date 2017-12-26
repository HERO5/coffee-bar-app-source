package com.example.apple.bakebar.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.apple.bakebar.R;
import com.example.apple.bakebar.Util.SetUpOnClickListener;
import com.example.apple.bakebar.activity.CallSystemCamera;
import com.example.apple.bakebar.activity.ImageSelectActivity;
import com.example.apple.bakebar.adapter.WGalleryAdapter;
import com.example.apple.bakebar.modle.UserMessage;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.wgallery.android.WGallery;

/**
 * Created by apple on 2017/10/31.
 */

public class RepertoryFragment extends BaseFragment  implements AppBarLayout.OnOffsetChangedListener {

    private FloatingSearchView mSearchView;
    private AppBarLayout mAppBar;
    private WGallery gallery;
    private WGalleryAdapter wGalleryAdapter;
    private int repertoryId;
    private String[] urls;
    private View view;
    private ImageView naive;
    private ImageView advanced;
    private ImageView master;
    private UserMessage userMessage;

    public RepertoryFragment(int repertoryId){
        super();
        this.repertoryId = repertoryId;
    }

    public RepertoryFragment(String[] urls){
        super();
        this.urls = urls;
    }

    public void setRepertoryId(int repertoryId){
        this.repertoryId = repertoryId;
        gallery.setAdapter(initGalleryAdapter(view,null));
    }

    public void setUrls(String[] urls){
        this.urls = urls;
        gallery.setAdapter(initGalleryAdapter(view,urls));
    }

    public int getRepertoryId() {
        return repertoryId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.repertory_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getActivity().getIntent().getSerializableExtra("userInfo")!=null){
            userMessage = (UserMessage) (getActivity().getIntent().getSerializableExtra("userInfo"));
        }
        this.view = view;
        mSearchView = (FloatingSearchView) view.findViewById(R.id.floating_search_view);
        mAppBar = (AppBarLayout) view.findViewById(R.id.appbar);
        mAppBar.addOnOffsetChangedListener(this);

        gallery = (WGallery)view.findViewById(R.id.wgallery);
        naive = (ImageView) view.findViewById(R.id.naive);
        advanced = (ImageView) view.findViewById(R.id.advanced);
        master = (ImageView) view.findViewById(R.id.master);

        gallery.setAdapter(initGalleryAdapter(view,urls));
        initImageLoader(view.getContext());

        setTypeOnClick();
        setupDrawer();
        setupSearchBar(view);
    }

    @Override
    public boolean onActivityBackPress() {
        //if mSearchView.setSearchFocused(false) causes the focused search
        //to close, then we don't want to close the activity. if mSearchView.setSearchFocused(false)
        //returns false, we know that the search was already closed so the call didn't change the focus
        //state and it makes sense to call supper onBackPressed() and close the activity
        if (!mSearchView.setSearchFocused(false)) {
            return false;
        }
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        mSearchView.setTranslationY(verticalOffset);
    }

    private void setupSearchBar(final View view){

        //handle menu clicks the same way as you would
        //in a regular activity
        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {

                Intent intent = new Intent();
                switch (item.getItemId()){
                    case R.id.search_menu:
                        String queryStr = mSearchView.getQuery().toString();
                        SetUpOnClickListener.doQuery(view,queryStr,getActivity(),userMessage);
                        break;
                    case R.id.call_camera:
                        Toast.makeText(getActivity().getApplicationContext(), item.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        intent.setClass(view.getContext(), CallSystemCamera.class);
                        //第一个参数就是获取当前的context。在activity类中可以用CurrentActivity.this代替
                        //这里第二个参数是将跳到的activity
                        view.getContext().startActivity(intent);
                        break;
                    case R.id.upload_img:
                        intent.setClass(view.getContext(), ImageSelectActivity.class);
                        intent.putExtra("userInfo", userMessage);
                        view.getContext().startActivity(intent);
                        break;
                    default:
                        break;
                }

            }
        });
    }

    private void setupDrawer() {
        attachSearchViewActivityDrawer(mSearchView);
    }

    protected BaseAdapter initGalleryAdapter(View view, String[] urls) {
//        throw new RuntimeException("必须重写该方法");
        if(urls!=null){
            return new WGalleryAdapter(view.getContext(),urls);
        }else{
            return new WGalleryAdapter(view.getContext(),repertoryId);
        }
    }

    private void setTypeOnClick(){
        SetUpOnClickListener.setTypeListener(naive,"naive",getActivity(),userMessage);
        SetUpOnClickListener.setTypeListener(advanced,"advanced",getActivity(),userMessage);
        SetUpOnClickListener.setTypeListener(master,"master",getActivity(),userMessage);
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
}
