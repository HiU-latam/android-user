package com.hiulatam.hiu.hiu;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.an.customfontview.CustomTextView;
import com.hiulatam.hiu.hiu.common.Config;
import com.hiulatam.hiu.hiu.modal.CelebrityItemModal;

/**
 * Created by:  Shiny Solutions
 * Created on:  10/12/17
 */

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity - ";

    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    ImageView imageViewCelebrity, image_button_settings;
    CustomTextView textViewCelebrityName, textViewCelebrityArticle, textViewCelebrityPercentage;
    Spinner spinnerValues;
    Button buttonNext;

    private String title, subTitle;
    private String[] values;

    private CelebrityItemModal celebrityItemModal;


    /**
     * Created by:  Shiny Solutions
     * Created on:  10/12/17
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        bindComponents();
        init();
        addListeners();
    }

    /**
     * Created by: Shiny Solutions
     * Created on: 9/30/17
     */
    public void bindComponents(){
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout_celebrity);

        toolbar = (Toolbar) findViewById(R.id.toolbar_celebrity);

        imageViewCelebrity = (ImageView) findViewById(R.id.image_view_celebrity);

        textViewCelebrityName = (CustomTextView) findViewById(R.id.text_view_celebrity_name);
        textViewCelebrityArticle = (CustomTextView) findViewById(R.id.text_view_celebrity_article);
        textViewCelebrityPercentage = (CustomTextView) findViewById(R.id.text_view_celebrity_percentage);

        spinnerValues = (Spinner) findViewById(R.id.spinnerValue);

        buttonNext = (Button) findViewById(R.id.buttonNext);

        image_button_settings = (ImageView) findViewById(R.id.image_button_settings);
    }

    /**
     * Created by: Shiny Solutions
     * Created on: 9/30/17
     */
    public void init(){
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            if (extras.containsKey(Config.EXTRA_CELEBRITY_ITEM)){
                celebrityItemModal = (CelebrityItemModal) extras.getParcelable(Config.EXTRA_CELEBRITY_ITEM);
            }
        }
        setSupportActionBar(toolbar);
        if (celebrityItemModal != null){

            textViewCelebrityName.setText(String.valueOf(celebrityItemModal.getName()));
            textViewCelebrityArticle.setText(String.valueOf(celebrityItemModal.getArticle()));
            textViewCelebrityPercentage.setText(String.valueOf(celebrityItemModal.getPercentage()));



            if (celebrityItemModal.getImage().equalsIgnoreCase("andres_cepeda")) {
                imageViewCelebrity.setBackgroundResource(R.drawable.andres_cepeda);
            }
            if (celebrityItemModal.getImage().equalsIgnoreCase("scarlett_johansson")) {
                imageViewCelebrity.setBackgroundResource(R.drawable.scarlett_johansson);
            }
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        values = getResources().getStringArray(R.array.messageValues);

        setValuesAdapter();
    }

    /**
     * Created by: Shiny Solutions
     * Created on: 9/30/17
     */
    public void addListeners(){
        buttonNext.setOnClickListener(onClickListener);
        spinnerValues.setOnItemSelectedListener(onItemSelectedListener);
        image_button_settings.setOnClickListener(onClickListener);
    }

    /**
     * Created by:  Shiny Solutions
     * Created on:  10/21/17
     */
    public void setValuesAdapter(){
        ArrayAdapter<String> arrayAdapterValues = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        arrayAdapterValues.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerValues.setAdapter(arrayAdapterValues);
    }

    private void openCharitySelection(){
        Intent intent = new Intent();
        intent.setClass(this, CharityActivity.class);
        intent.putExtra(Config.EXTRA_CELEBRITY_ITEM, celebrityItemModal);
        startActivity(intent);
    }

    private void openSettings(){
        Intent intent = new Intent();
        intent.setClass(this, SettingsActivity.class);
        startActivity(intent);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.buttonNext:
                    openCharitySelection();
                    break;
                case R.id.image_button_settings:
                    openSettings();
                    break;
            }

        }
    };

    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Config.logInfo(TAG + "onItemSelected");
            String value = parent.getItemAtPosition(position).toString();
            value = value.substring(0, value.length()-4);
            Config.logInfo(TAG + "onItemSelected - Selected Item: " + value);
            celebrityItemModal.setValue(value);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
