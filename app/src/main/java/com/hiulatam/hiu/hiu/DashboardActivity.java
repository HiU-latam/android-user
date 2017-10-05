package com.hiulatam.hiu.hiu;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hiulatam.hiu.hiu.adapter.CelebrityFragmentPagerAdapter;
import com.hiulatam.hiu.hiu.common.Config;
import com.hiulatam.hiu.hiu.fragments.CelebrityFragment;
import com.hiulatam.hiu.hiu.utils.circleTransform;
import com.hiulatam.hiu.hiu.utils.profileFacebook;
import com.squareup.picasso.Picasso;

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
    ImageButton imageButtonNavigationView;
    SearchView searchViewCelebrity;
    TabLayout tabLayoutCelebrity;
    ViewPager viewPagerContent;

    CelebrityFragmentPagerAdapter celebrityFragmentPagerAdapter;
    private profileFacebook pefil;
    private NavigationView navigationView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        pefil=new profileFacebook();

        if (extras != null) {
            pefil= (profileFacebook)extras.getParcelable("profile");
            // and get whatever type user account id is
        }


        bindComponents();
        init();
        addListeners();
        if(pefil.profile!=null){//loged by facebook
            facebookprofilefill();
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


    }

    /**
     * Created by: Shiny Solutions
     * Created on: 9/30/17
     */
    public void addListeners(){
        imageButtonNavigationView.setOnClickListener(onClickListener);
        searchViewCelebrity.setOnSearchClickListener(onClickListener);
        searchViewCelebrity.setOnCloseListener(onCloseListener);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Created by:  Shiny Solutions
     * Created on:  10/5/17
     * @return
     */
    private List<Fragment> getFragments(){
        List<Fragment> fragmentList = new ArrayList<Fragment>();

        fragmentList.add(new CelebrityFragment().newInstance("Name"));
        fragmentList.add(new CelebrityFragment().newInstance("Country"));
        fragmentList.add(new CelebrityFragment().newInstance("Category"));
        return fragmentList;
    }

    /**
     * Created by:  Shiny Solutions
     * Created on:  10/5/17
     */
    private void setViewPagerAdapter(){
        List<Fragment> fragmentList = getFragments();
        if (celebrityFragmentPagerAdapter == null)
            celebrityFragmentPagerAdapter = new CelebrityFragmentPagerAdapter(getSupportFragmentManager(), this, fragmentList);
        viewPagerContent.setAdapter(celebrityFragmentPagerAdapter);
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
            }
        }
    };

    SearchView.OnCloseListener onCloseListener = new SearchView.OnCloseListener() {
        @Override
        public boolean onClose() {
            imageButtonNavigationView.setVisibility(View.VISIBLE);
            linearLayoutTitle.setVisibility(View.VISIBLE);
            return false;
        }
    };



    private void facebookprofilefill() {
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.username);
        nav_user.setText(pefil.profile.getName());
        ImageView img_user = (ImageView) hView.findViewById(R.id.imageView);
        Picasso.with(this)
                .load(pefil.profile.getProfilePictureUri(280,280))
                .placeholder(R.drawable.com_facebook_button_login_logo)
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
            if(profileFacebook.isLoggedIn()){
                profileFacebook.callFacebookLogout(this);
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
