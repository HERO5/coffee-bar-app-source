package com.example.apple.bakebar.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.apple.bakebar.R;
import com.example.apple.bakebar.customInterface.BakeStudioOnFocusListenable;
import com.example.apple.bakebar.fragment.BaseFragment;
import com.example.apple.bakebar.fragment.RepertoryFragment;

import java.util.List;

/**
 * Created by apple on 2017/10/31.
 */

public class WRepertoryViewMainActivity extends AppCompatActivity
        implements BaseFragment.BaseExampleFragmentCallbacks, NavigationView.OnNavigationItemSelectedListener{

    private RepertoryFragment baseFragment;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;
    private String repertory_name;
    private String[] imgUrls;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.repertory_activity_main);
        Bundle bundle = this.getIntent().getExtras();
        repertory_name = bundle.getString("repertory_name");
        imgUrls = bundle.getStringArray("imgUrls");
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = this.getSupportFragmentManager();
        if(imgUrls!=null){
            baseFragment = new RepertoryFragment(imgUrls);
        }else{
            switch (repertory_name) {
                case "studio":
                    baseFragment = new RepertoryFragment(1);
                    break;
                case "categories":
                    baseFragment = new RepertoryFragment(2);
                    break;
                case "shop":
                    baseFragment = new RepertoryFragment(3);
                    break;
                case "cake":
                    baseFragment = new RepertoryFragment(4);
                    break;
                case "coffee":
                    baseFragment = new RepertoryFragment(5);
                    break;
                default:
                    break;
            }
        }
        showFragment(baseFragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (menuItem.getItemId()) {
            case R.id.left_menu_coffee:
//                showFragment(new RepertoryFragment(4));
                baseFragment.setRepertoryId(4);
                return true;
            case R.id.left_menu_cake:
//                showFragment(new RepertoryFragment(5));
                baseFragment.setRepertoryId(5);
                return true;
            case R.id.left_menu_break:
//                showFragment(new RepertoryFragment(5));
                baseFragment.setRepertoryId(5);
                return true;
            default:
                return true;
        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if(currentFragment instanceof BakeStudioOnFocusListenable) {
            ((BakeStudioOnFocusListenable) currentFragment).onWindowFocusChanged(hasFocus);
        }
    }
    @Override
    public void onAttachSearchViewToDrawer(FloatingSearchView searchView) {
        searchView.attachNavigationDrawerToMenuButton(drawerLayout);
    }
    @Override
    public void onBackPressed() {
        List fragments = getSupportFragmentManager().getFragments();
        BaseFragment currentFragment = (BaseFragment) fragments.get(fragments.size() - 1);

        if (!currentFragment.onActivityBackPress()) {
            super.onBackPressed();
        }
    }
    private void showFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();
    }
}
