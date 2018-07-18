package com.hiulatam.hiu.hiu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.an.customfontview.CustomTextView;
import com.hiulatam.hiu.hiu.adapter.SettingsItemAdapter;
import com.hiulatam.hiu.hiu.common.Config;
import com.hiulatam.hiu.hiu.modal.SettingItemModal;
import com.hiulatam.hiu.hiu.modal.SettingsChildItemModal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by:  Shiny Solutions
 * Created on:  11/22/17.
 */

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity - ";

    CustomTextView customTextViewProfileName;
    RecyclerView recyclerViewSettings;
    Toolbar toolbar;

    SettingsItemAdapter settingsItemAdapter;

    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        bindComponents();
        init();
        addListeners();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Created by:  Shiny Solutions
     * Created on:  11/22/17.
     */
    private void bindComponents(){
        Config.logInfo(TAG + "bindComponents");
        customTextViewProfileName = (CustomTextView) findViewById(R.id.customTextViewProfileName);

        recyclerViewSettings = (RecyclerView) findViewById(R.id.recyclerViewSettings);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    /**
     * Created by:  Shiny Solutions
     * Created on:  11/22/17.
     */
    private void init(){
        Config.logInfo(TAG + "init");
        setSupportActionBar(toolbar);

        layoutManager = new LinearLayoutManager(this);

        RecyclerView.ItemAnimator animator = recyclerViewSettings.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        setSettingsItemAdapter();

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    /**
     * Created by:  Shiny Solutions
     * Created on:  11/22/17.
     */
    private void addListeners(){
    }

    /**
     * Created by:  Shiny Solutions
     * Created on:  11/28/17
     * @return
     */
    private List<SettingItemModal> getSettingItems(){
        Config.logInfo(TAG + "getSettingItems");
        List<SettingItemModal> settingItemModalList = new ArrayList<SettingItemModal>();

        List<SettingsChildItemModal> settingsChildItemModalList = new ArrayList<SettingsChildItemModal>();
        SettingItemModal settingItemModal = new SettingItemModal(getString(R.string.notification), R.drawable.ic_notification, settingsChildItemModalList);
        settingItemModalList.add(settingItemModal);

        Config.logInfo(TAG + "getSettingItems - Group Size: " + settingItemModalList.size());

        return settingItemModalList;
    }

    /**
     * Created by:  Shiny Solutions
     * Created on:  11/28/17
     */
    private void setSettingsItemAdapter(){
        Config.logInfo(TAG + "setSettingsItemAdapter");
        if (settingsItemAdapter == null)
            settingsItemAdapter = new SettingsItemAdapter(this, getSettingItems());

        recyclerViewSettings.setLayoutManager(layoutManager);
        recyclerViewSettings.setAdapter(settingsItemAdapter);
    }

}
