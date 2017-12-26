package com.example.apple.bakebar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.apple.bakebar.Util.HttpUtils;
import com.example.apple.bakebar.Util.PrefUtils;
import com.example.apple.bakebar.customInterface.BakeStudioOnFocusListenable;
import com.example.apple.bakebar.fragment.BakeStudioFragment;
import com.example.apple.bakebar.fragment.BaseFragment;
import com.example.apple.bakebar.fragment.MainFragment;
import com.example.apple.bakebar.fragment.PersonalPageFragment;
import com.example.apple.bakebar.modle.UserMessage;
import com.loopj.android.http.PersistentCookieStore;

import java.util.List;

import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;

public class MainActivity extends AppCompatActivity
        implements BaseFragment.BaseExampleFragmentCallbacks, NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;
    private UserMessage userMessage;
    public static MainActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = this.getSupportFragmentManager();

        userMessage = (UserMessage) getIntent().getSerializableExtra("userInfo");
        Log.i("login", "===========================main-main\n"+userMessage.getState());

        if (userMessage.getState()==UserMessage.UPDATE_SUCCEED) {
            Toast.makeText(this, "重新登录", Toast.LENGTH_LONG).show();
            PersistentCookieStore myCookieStore = new PersistentCookieStore(getApplicationContext());
            HttpUtils.client.setCookieStore(myCookieStore);
            List<Cookie> cookies = myCookieStore.getCookies();
            String JSESSIONID = "";
            if (cookies.isEmpty()) {
                Log.i("session", "None");
            } else {
                for (int i = 0; i < cookies.size(); i++) {
                    if ("JSESSIONID".equals(cookies.get(i).getName())) {
                        JSESSIONID = cookies.get(i).getValue();  //第二种方法 通过JSESSIONID
                        System.out.println(JSESSIONID);
                        break;
                    }
                }
                PrefUtils.set("user", "session", JSESSIONID, getBaseContext());
                Log.i("session", "保存" + JSESSIONID);
            }
            BasicClientCookie newCookie = new BasicClientCookie("userId", userMessage.getUser().getUserId());
            newCookie.setVersion(1);
            newCookie.setDomain("114.215.135.153");
            newCookie.setPath("/");
            myCookieStore.addCookie(newCookie);
        }

        showFragment(new MainFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (menuItem.getItemId()) {
            case R.id.left_menu_main:
                showFragment(new MainFragment());
                return true;
            case R.id.left_menu_bake_studio:
                showFragment(new BakeStudioFragment());
                return true;
//            case R.id.left_menu_categories:
//                showFragment(new BakeCategoriesFragment());
//                return true;
//            case R.id.left_menu_bake_shop:
//                showFragment(new BakeShopFragment());
//                return true;
            case R.id.left_menu_personal:
                showFragment(new PersonalPageFragment(1));
                return true;
            default:
                return true;
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if(currentFragment instanceof BakeStudioOnFocusListenable) {
            ((BakeStudioOnFocusListenable) currentFragment).onWindowFocusChanged(hasFocus);
        }
    }

    private void showFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();
    }

//    public void switchContent(Fragment from, Fragment to) {
//        if (mContent != to) {
//            mContent = to;
//            FragmentTransaction transaction = mFragmentMan.beginTransaction().setCustomAnimations(
//                    android.R.anim.fade_in, R.anim.slide_out);
//            if (!to.isAdded()) {    // 先判断是否被add过
//                transaction.hide(from).add(R.id.content_frame, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
//            } else {
//                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
//            }
//        }
//    }

//    //init方法是对FloatingSearchView的一些设置，如果已经实现了NavigationView中相对应的方法，本方法就没必要执行
//    private void initFloatingSearchView(){
////        Listen to hamburger button clicks:
//        floatingSearchView.setOnLeftMenuClickListener(new FloatingSearchView.OnLeftMenuClickListener() {
//            @Override
//            public void onMenuOpened() {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//
//            @Override
//            public void onMenuClosed() {
//                drawerLayout.closeDrawer(GravityCompat.START);
//            }
//        });
////        Listen for item selections equals onNavigationItemSelected
//        floatingSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener(){
//            @Override
//            public void onActionMenuItemSelected(MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.sliding_list_example:
//                        showFragment(new SlidingSearchResultsFragment());
//                    case R.id.sliding_search_bar_example:
//                        showFragment(new SlidingSearchViewFragment());
//                    case R.id.scrolling_search_bar_example:
//                        showFragment(new ScrollingSearchFragment());
//                    default:
//                }
//            }
//        });
//
////        To quickly connect your NavigationDrawer to the hamburger button:
//        floatingSearchView.attachNavigationDrawerToMenuButton(drawerLayout);
//
////        Listen to home (back arrow) button clicks:
//        floatingSearchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
//            @Override
//            public void onHomeClicked() {
//
//            }
//        });
////        Set a callback for when a given suggestion is bound to the suggestion list.
////        For the history icons to show, you would need to implement this. Refer to the sample app for an example implementation.
//        floatingSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
//              @Override
//              public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {
//
//              //here you can set some attributes for the suggestion's left icon and text. For example,
//              //you can choose your favorite image-loading library for setting the left icon's image.
//              }
//
//        });
//

}
