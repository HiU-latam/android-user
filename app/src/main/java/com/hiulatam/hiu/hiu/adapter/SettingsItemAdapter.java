package com.hiulatam.hiu.hiu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.hiulatam.hiu.hiu.R;
import com.hiulatam.hiu.hiu.common.Config;
import com.hiulatam.hiu.hiu.modal.SettingItemModal;
import com.hiulatam.hiu.hiu.modal.SettingsChildItemModal;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.util.List;

/**
 * Created by:  Shiny Solutions
 * Created on:  11/28/17.
 */

public class SettingsItemAdapter extends ExpandableRecyclerViewAdapter<SettingItemViewHolder, ChildViewHolder> {
    private static final String TAG = "SettingsItemAdapter - ";

    public static final int FIRST_VIEW_TYPE = 1;
    public static final int ARTIST_VIEW_TYPE = 4;

    List<SettingItemModal> parentObjectList;
    Context context;

    public SettingsItemAdapter(Context context, List<? extends ExpandableGroup> parentItemList) {
        super(parentItemList);
        this.context = context;
        Config.logInfo(TAG + "SettingsItemAdapter");
    }

    @Override
    public SettingItemViewHolder onCreateGroupViewHolder(ViewGroup viewGroup, int viewType) {
        Config.logInfo(TAG + "onCreateParentViewHolder");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_group_settings, null, false);
        return new SettingItemViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup, int viewType) {
        Config.logInfo(TAG + "onCreateChildViewHolder");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_expanded_settings, viewGroup, false);
        return new SettingsChildItemViewHolder(view);
    }

    @Override
    public void onBindGroupViewHolder(SettingItemViewHolder settingItemViewHolder, int i, ExpandableGroup o) {
        Config.logInfo(TAG + "onBindParentViewHolder");
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelSize(R.dimen.settings_group_item_height));
        settingItemViewHolder.getRelativeLayoutMenu().setLayoutParams(layoutParams);
        settingItemViewHolder.getRelativeLayoutMenu().setGravity(Gravity.CENTER_VERTICAL);
        if (i < (getItemCount() - 1 )){
            settingItemViewHolder.getRelativeLayoutMenu().setBackgroundResource(R.drawable.menu_group_bg);
        }
        SettingItemModal settingItemModal = (SettingItemModal) o;
        settingItemViewHolder.getTextMenuNotification().setVisibility(View.GONE);
        settingItemViewHolder.getImageViewMenuNotification().setVisibility(View.GONE);
        settingItemViewHolder.getCheckboxMenuNotification().setVisibility(View.GONE);
        settingItemViewHolder.getCheckboxMenuNotification().setChecked(Config.getSharedPreferences(context).getBoolean(Config.PREFS_NOTIFICATION, false));
        settingItemViewHolder.getCheckboxMenuNotification().setOnCheckedChangeListener(onCheckedChangeListener);
        settingItemViewHolder.getImageViewMenuIcon().setVisibility(View.VISIBLE);
        if (settingItemModal.getName().equalsIgnoreCase(context.getString(R.string.notification))){
            settingItemViewHolder.getCheckboxMenuNotification().setVisibility(View.VISIBLE);
        }

        settingItemViewHolder.setMenuTitle(settingItemModal);
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder childViewHolder, int i, ExpandableGroup group, int childIndex) {
        Config.logInfo(TAG + "onBindChildViewHolder");
        if (childViewHolder instanceof SettingsChildItemViewHolder){
            SettingsChildItemModal settingsChildItemModal = ((SettingsChildItemModal) group.getItems().get(childIndex));
            SettingsChildItemViewHolder settingsChildItemViewHolder = (SettingsChildItemViewHolder) childViewHolder;
            settingsChildItemViewHolder.onBind(settingsChildItemModal, i);
        }
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Config.getSharedPreferencesEditor(context).putBoolean(Config.PREFS_NOTIFICATION, isChecked).commit();
        }
    };
}
