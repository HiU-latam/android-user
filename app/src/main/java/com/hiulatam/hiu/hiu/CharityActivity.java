package com.hiulatam.hiu.hiu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hiulatam.hiu.hiu.adapter.CharityStackAdapter;
import com.hiulatam.hiu.hiu.common.Config;
import com.hiulatam.hiu.hiu.modal.CelebrityItemModal;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.SwipeDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import link.fls.swipestack.SwipeStack;

/**
 * Created by:  Shiny Solutions
 * Created on:  10/22/17
 */

public class CharityActivity extends AppCompatActivity {

    public static final String TAG = "CharityActivity - ";

    DrawerLayout drawerLayout;
    ImageButton imageButtonNavigationView, image_button_settings;
    ImageView imageViewAddCharities;
    Toolbar toolbar;
    SearchView searchViewCelebrity;
    LinearLayout linearLayoutTitle;
    Button buttonNext;
    SwipeStack cardStackViewCharity;

    private CelebrityItemModal celebrityItemModal;

    private CharityStackAdapter charityStackAdapter;


    private int numberOfCardSwiped = 0;
    ArrayList<CelebrityItemModal> celebrityItemModalList = new ArrayList<CelebrityItemModal>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charity_activity);

        bindComponents();
        init();
        addListeners();
    }

    /**
     * Created by:  Shiny Solutions
     * Created on:  10/22/17
     */
    private void bindComponents(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        imageButtonNavigationView = (ImageButton) findViewById(R.id.image_button_navigation_view);

        imageViewAddCharities = (ImageView) findViewById(R.id.imageViewAddCharities);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        searchViewCelebrity = (SearchView) findViewById(R.id.search_view_celebrity);

        linearLayoutTitle = (LinearLayout) findViewById(R.id.linear_layout_title);

        buttonNext = (Button) findViewById(R.id.buttonNext);

        cardStackViewCharity = (SwipeStack) findViewById(R.id.cardStackViewChairty);

        image_button_settings = (ImageButton) findViewById(R.id.image_button_settings);
    }

    /**
     * Created by:  Shiny Solutions
     * Created on:  10/22/17
     */
    private void init(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            if (extras.containsKey(Config.EXTRA_CELEBRITY_ITEM)){
                celebrityItemModal = (CelebrityItemModal) extras.getParcelable(Config.EXTRA_CELEBRITY_ITEM);
            }
        }

        setCelebrityItemModalList();

        setCharityAdapter();

        if (celebrityItemModalList.get(cardStackViewCharity.getCurrentPosition()).getFavorite().equalsIgnoreCase("Yes")) {
            imageViewAddCharities.setImageResource(R.drawable.ic_favorites);
        }else {
            imageViewAddCharities.setImageResource(R.drawable.icon_star);
        }
    }

    /**
     * Created by:  Shiny Solutions
     * Created on:  10/22/17
     */
    private void addListeners(){
        imageButtonNavigationView.setOnClickListener(onClickListener);
        searchViewCelebrity.setOnSearchClickListener(onClickListener);
        searchViewCelebrity.setOnCloseListener(onCloseListener);
        buttonNext.setOnClickListener(onClickListener);
        imageViewAddCharities.setOnClickListener(onClickListener);
        image_button_settings.setOnClickListener(onClickListener);
    }

    /**
     * Created by:  Shiny Solutions
     * Created on:  10/23/17
     */
    private void openPaymentDetail(){
        Intent intent = new Intent();
        intent.setClass(this, PaymentDetailActivity.class);
        intent.putExtra(Config.EXTRA_CELEBRITY_ITEM, celebrityItemModal);
        startActivity(intent);
    }

    private void setCharityAdapter(){
        if (charityStackAdapter == null){
            charityStackAdapter = new CharityStackAdapter(this, celebrityItemModalList);
        }
        cardStackViewCharity.setAdapter(charityStackAdapter);
        charityStackAdapter.setNotifyOnChange(true);
        cardStackViewCharity.setListener(new SwipeStack.SwipeStackListener() {
            @Override
            public void onViewSwipedToLeft(int position) {
                if (charityStackAdapter.getItem(position).getFavorite().equalsIgnoreCase("Yes"))
                    imageViewAddCharities.setImageResource(R.drawable.ic_favorites);
                else
                    imageViewAddCharities.setImageResource(R.drawable.icon_star);
            }

            @Override
            public void onViewSwipedToRight(int position) {
                if (charityStackAdapter.getItem(position).getFavorite().equalsIgnoreCase("Yes"))
                    imageViewAddCharities.setImageResource(R.drawable.ic_favorites);
                else
                    imageViewAddCharities.setImageResource(R.drawable.icon_star);
            }

            @Override
            public void onStackEmpty() {
                cardStackViewCharity.resetStack();
            }
        });
    }

    private void setCelebrityItemModalList(){
        celebrityItemModalList.clear();

        CelebrityItemModal celebrityItemModal = new CelebrityItemModal();
        celebrityItemModal.setImage("bill_melinda_gates_foundation");
        celebrityItemModal.setName("Bill & Melinda Gates Foundation");
        celebrityItemModal.setArticle("Musico");
        celebrityItemModal.setPercentage(6);
        celebrityItemModal.setFavorite("Yes");
        celebrityItemModalList.add(celebrityItemModal);

        celebrityItemModal = new CelebrityItemModal();
        celebrityItemModal.setImage("open_society");
        celebrityItemModal.setName("Open Society Foundations");
        celebrityItemModal.setArticle("Actriz");
        celebrityItemModal.setPercentage(7);
        celebrityItemModal.setFavorite("No");
        celebrityItemModalList.add(celebrityItemModal);

        celebrityItemModal = new CelebrityItemModal();
        celebrityItemModal.setImage("ford_foundation");
        celebrityItemModal.setName("Ford Foundation");
        celebrityItemModal.setArticle("Actriz");
        celebrityItemModal.setPercentage(7);
        celebrityItemModal.setFavorite("Yes");
        celebrityItemModalList.add(celebrityItemModal);

        celebrityItemModal = new CelebrityItemModal();
        celebrityItemModal.setImage("william_and_flora_hewlett_foundation");
        celebrityItemModal.setName("William and Flora Hewlett Foundation");
        celebrityItemModal.setArticle("Actriz");
        celebrityItemModal.setPercentage(7);
        celebrityItemModal.setFavorite("No");
        celebrityItemModalList.add(celebrityItemModal);
    }

    private void openSettings(){
        Intent intent = new Intent();
        intent.setClass(this, SettingsActivity.class);
        startActivity(intent);
    }

    /**
     * Created by:  Shiny Solutions
     * Created on:  10/22/17
     */
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Config.logInfo(TAG + "onClick");
            switch (view.getId()){
                case R.id.image_button_navigation_view:
                    drawerLayout.openDrawer(Gravity.LEFT);
                    break;
                case R.id.search_view_celebrity:
                    imageButtonNavigationView.setVisibility(View.GONE);
                    linearLayoutTitle.setVisibility(View.GONE);
                    break;
                case R.id.buttonNext:
                    openPaymentDetail();
                    break;
                case R.id.imageViewAddCharities:
                    Config.logInfo(TAG + "onClick - existing: " + celebrityItemModalList.get(cardStackViewCharity.getCurrentPosition()).getFavorite());
                    if (celebrityItemModalList.get(cardStackViewCharity.getCurrentPosition()).getFavorite().equalsIgnoreCase("Yes")) {
                        celebrityItemModalList.get(cardStackViewCharity.getCurrentPosition()).setFavorite("No");
                        imageViewAddCharities.setImageResource(R.drawable.icon_star);
                        Config.logInfo(TAG + "onClick - set no");
                    }else {
                        celebrityItemModalList.get(cardStackViewCharity.getCurrentPosition()).setFavorite("Yes");
                        imageViewAddCharities.setImageResource(R.drawable.ic_favorites);
                        Config.logInfo(TAG + "onClick - set yes");
                    }
                    break;
                case R.id.image_button_settings:
                    openSettings();
                    break;
            }
        }
    };

    /**
     * Created by:  Shiny Solutions
     * Created on:  10/22/17
     */
    SearchView.OnCloseListener onCloseListener = new SearchView.OnCloseListener() {
        @Override
        public boolean onClose() {
            imageButtonNavigationView.setVisibility(View.VISIBLE);
            linearLayoutTitle.setVisibility(View.VISIBLE);
            return false;
        }
    };
}
