package com.hiulatam.hiu.hiu;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.an.customfontview.CustomTextView;
import com.hiulatam.hiu.hiu.adapter.CelebrityFragmentPagerAdapter;
import com.hiulatam.hiu.hiu.common.Config;
import com.hiulatam.hiu.hiu.fragments.CelebrityFragment;
import com.hiulatam.hiu.hiu.utils.circleTransform;
import com.hiulatam.hiu.hiu.utils.profileUser;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.internal.TwitterApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Shiny Solutions
 * Created on: 9/30/17
 */

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    public static final String TAG = "DashboardActivity - ";

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    LinearLayout linearLayoutTitle;
    ImageButton imageButtonNavigationView, image_button_settings;
    SearchView searchViewCelebrity;
    TabLayout tabLayoutCelebrity;
    ViewPager viewPagerContent;
    private NavigationView navigationView;

    CelebrityFragmentPagerAdapter celebrityFragmentPagerAdapter;
    private profileUser pefil;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        pefil=MyApplication.profile;

        bindComponents();
        init();
        addListeners();
        if(profileUser.isLoggedInFAacebook(this)){//loged by facebook
            facebookprofilefill();
        }else if(profileUser.isLoggedInInstagram(this)){
            Instagramprofilefill();
        }
        else if(profileUser.isLoggedInTwitter(this)){
            Twitterprofilefill();
        }
    }

    /**
     * Created by: Shiny Solutions
     * Created on: 9/30/17
     */
    public void bindComponents(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        linearLayoutTitle = (LinearLayout) findViewById(R.id.linear_layout_title);

        imageButtonNavigationView = (ImageButton) findViewById(R.id.image_button_navigation_view);

        searchViewCelebrity = (SearchView) findViewById(R.id.search_view_celebrity);

        tabLayoutCelebrity = (TabLayout) findViewById(R.id.tab_layout_celebrity);

        viewPagerContent = (ViewPager) findViewById(R.id.view_pager_content);

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        image_button_settings = (ImageButton) findViewById(R.id.image_button_settings);
    }

    /**
     * Created by: Shiny Solutions
     * Created on: 9/30/17
     */
    public void init(){

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setViewPagerAdapter();

        tabLayoutCelebrity.setupWithViewPager(viewPagerContent);

        setDivider();
        changeTabsFont();

    }

    /**
     * Created by: Shiny Solutions
     * Created on: 9/30/17
     */
    public void addListeners(){
        imageButtonNavigationView.setOnClickListener(onClickListener);
        searchViewCelebrity.setOnSearchClickListener(onClickListener);
        searchViewCelebrity.setOnCloseListener(onCloseListener);
        searchViewCelebrity.setOnQueryTextListener(onQueryTextListener);
        navigationView.setNavigationItemSelectedListener(this);
        image_button_settings.setOnClickListener(onClickListener);
    }



    /**
     * Created by:  Shiny Solutions
     * Created on:  10/5/17
     */
    private void setViewPagerAdapter(){
        if (celebrityFragmentPagerAdapter == null) {
            celebrityFragmentPagerAdapter = new CelebrityFragmentPagerAdapter(getSupportFragmentManager(), this);
        }
        viewPagerContent.setAdapter(celebrityFragmentPagerAdapter);
    }

    /**
     * Created by:  Shiny Solutions
     * Created on:  10/6/17
     */
    private void setDivider(){
        View root = tabLayoutCelebrity.getChildAt(0);
        if (root instanceof LinearLayout){
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.colorPrimaryDark));
            drawable.setSize(1, 1);
            ((LinearLayout) root).setDividerPadding(15);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }
    }

    /**
     * Created by:  Shiny Solutions
     * Created on:  10/7/17
     */
    private void changeTabsFont() {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Nanami-Regular.otf");
        ViewGroup vg = (ViewGroup) tabLayoutCelebrity.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(font);
                    ((TextView) tabViewChild).setTextSize(15);

                }
            }
        }
    }

    private void openSettings(){
        Intent intent = new Intent();
        intent.setClass(this, SettingsActivity.class);
        startActivity(intent);
    }

    /**
     * Created by: Shiny Solutions
     * Created on: 10/03/17
     */
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.image_button_navigation_view:
                    drawerLayout.openDrawer(Gravity.LEFT);
                    break;
                case R.id.search_view_celebrity:
                    imageButtonNavigationView.setVisibility(View.GONE);
                    linearLayoutTitle.setVisibility(View.GONE);

                    break;

                case R.id.image_button_settings:
                    openSettings();
                    break;
            }
        }
    };

    /**
     * Created by: Shiny Solutions
     * Created on: 10/12/17
     */
    SearchView.OnCloseListener onCloseListener = new SearchView.OnCloseListener() {
        @Override
        public boolean onClose() {
            Config.logInfo(TAG + "onClose");
            imageButtonNavigationView.setVisibility(View.VISIBLE);
            linearLayoutTitle.setVisibility(View.VISIBLE);
            Intent searchBroadcastIntent = new Intent();
            searchBroadcastIntent.setAction(Config.ACTION_SEARCH_QUERY);
            searchBroadcastIntent.putExtra(Config.EXTRA_SEARCH_QUERY, Config.kAll);
            sendBroadcast(searchBroadcastIntent);
            return false;
        }
    };


    SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            Config.logInfo(TAG + "onQueryTextSubmit");
            Intent searchBroadcastIntent = new Intent();
            searchBroadcastIntent.setAction(Config.ACTION_SEARCH_QUERY);
            searchBroadcastIntent.putExtra(Config.EXTRA_SEARCH_QUERY, query);
            sendBroadcast(searchBroadcastIntent);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            Config.logInfo(TAG + "onQueryTextChange");
            Intent searchBroadcastIntent = new Intent();
            searchBroadcastIntent.setAction(Config.ACTION_SEARCH_QUERY);
            searchBroadcastIntent.putExtra(Config.EXTRA_SEARCH_QUERY, newText);
            sendBroadcast(searchBroadcastIntent);
            return false;
        }
    };


    private void facebookprofilefill() {
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.username);
        nav_user.setText(pefil.profilefacebok.getName());
        ImageView img_user = (ImageView) hView.findViewById(R.id.imageView);
        Picasso.with(this)
                .load(pefil.profilefacebok.getProfilePictureUri(140,140))
                .placeholder(R.drawable.com_facebook_button_login_logo)
                .error(android.R.drawable.sym_def_app_icon)
                .transform(new circleTransform())
                .into(img_user);
        TextView mail_user = (TextView)hView.findViewById(R.id.textView);
        mail_user.setText("");
    }

    private void Instagramprofilefill() {
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.username);
        nav_user.setText(pefil.profileInstagram.getData().getFullName());
        ImageView img_user = (ImageView) hView.findViewById(R.id.imageView);
        Picasso.with(this)
                .load(pefil.profileInstagram.getData().getProfilePicture())
                .placeholder(R.drawable.instagram_button)
                .error(android.R.drawable.sym_def_app_icon)
                .transform(new circleTransform())
                .into(img_user);
        TextView mail_user = (TextView)hView.findViewById(R.id.textView);
        mail_user.setText("");
    }
    private void Twitterprofilefill() {
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.username);
        nav_user.setText(pefil.profileTwitter.name);
        ImageView img_user = (ImageView) hView.findViewById(R.id.imageView);
       //String  a= pefil.profileTwitter.profileBackgroundImageUrl;
        String  b= pefil.profileTwitter.profileImageUrl;
        Picasso.with(this)
                .load(b.replace("_normal",""))
                .placeholder(R.drawable.tw__composer_logo_white)
                .error(android.R.drawable.sym_def_app_icon)
                .transform(new circleTransform())
                .into(img_user);
        TextView mail_user = (TextView)hView.findViewById(R.id.textView);
        mail_user.setText("");
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }else if(id == R.id.log_out){
            if(profileUser.isLoggedInFAacebook(this)){
                profileUser.callFacebookLogout(this);
                Intent intent= new Intent(this,LoginActivity.class);
                startActivity(intent);
            }else if(profileUser.isLoggedInInstagram(this)){
                profileUser.callInstagramLogout(this);
                Intent intent= new Intent(this,LoginActivity.class);
                startActivity(intent);
            }
            else if(profileUser.isLoggedInTwitter(this)){
                TwitterCore.getInstance().getSessionManager().clearActiveSession();
                Intent intent= new Intent(this,LoginActivity.class);
                startActivity(intent);
            }else{
                Intent intent= new Intent(this,LoginActivity.class);
                startActivity(intent);
            }


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finishAffinity();
        }
    }
}
