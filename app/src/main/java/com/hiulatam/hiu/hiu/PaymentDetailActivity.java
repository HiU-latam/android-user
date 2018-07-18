package com.hiulatam.hiu.hiu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringDef;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Space;

import com.an.customfontview.CustomTextView;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.hiulatam.hiu.hiu.common.Config;
import com.hiulatam.hiu.hiu.fragments.PaymentConfirmationDialogFragment;
import com.hiulatam.hiu.hiu.modal.CelebrityItemModal;

/**
 * Created by:  Shiny Solutions
 * Created on:  10/23/17.
 */

public class PaymentDetailActivity extends AppCompatActivity {

    public static final String TAG = "PaymentDetailActivity - ";

    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    ImageView imageViewCelebrity, image_button_settings;
    CustomTextView textViewCelebrityName, textViewCelebrityArticle, textViewCelebrityPercentage, textViewMessageValue, textViewCharityValue, textViewTotalValue;
    SearchView searchViewCelebrity;
    EditText editTextCardNumber;
    Button buttonDone;
    DonutProgress celebrity_rating;

    private CelebrityItemModal celebrityItemModal;

    private boolean deletePressed;
    private int messageValue;
    private int charityValue;
    private int totalValue;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);

        bindComponents();
        init();
        addListeners();
    }

    /**
     * Created by:  Shiny Solutions
     * Created on:  10/23/17.
     */
    private void bindComponents(){
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout_celebrity);

        toolbar = (Toolbar) findViewById(R.id.toolbar_celebrity);

        imageViewCelebrity = (ImageView) findViewById(R.id.image_view_celebrity);

        textViewCelebrityName = (CustomTextView) findViewById(R.id.text_view_celebrity_name);
        textViewCelebrityArticle = (CustomTextView) findViewById(R.id.text_view_celebrity_article);
        textViewCelebrityPercentage = (CustomTextView) findViewById(R.id.text_view_celebrity_percentage);

        searchViewCelebrity = (SearchView) findViewById(R.id.search_view_celebrity);

        editTextCardNumber = (EditText) findViewById(R.id.editTextCardNumber);

        buttonDone = (Button) findViewById(R.id.buttonDone);

        celebrity_rating = (DonutProgress) findViewById(R.id.celebrity_rating);

        textViewMessageValue = (CustomTextView) findViewById(R.id.textViewMessageValue);

        textViewCharityValue = (CustomTextView) findViewById(R.id.textViewCharityValue);

        textViewTotalValue = (CustomTextView) findViewById(R.id.textViewTotalValue);

        image_button_settings = (ImageView) findViewById(R.id.image_button_settings);

    }

    /**
     * Created by:  Shiny Solutions
     * Created on:  10/23/17.
     */
    private void init(){
        charityValue = 5;
        textViewCharityValue.setText(String.format("$%d", charityValue));
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
            celebrity_rating.setDonut_progress(String.valueOf(celebrityItemModal.getPercentage()));
            celebrity_rating.setText(String.valueOf(celebrityItemModal.getPercentage()));



            if (celebrityItemModal.getImage().equalsIgnoreCase("andres_cepeda")) {
                imageViewCelebrity.setBackgroundResource(R.drawable.andres_cepeda);
            }
            if (celebrityItemModal.getImage().equalsIgnoreCase("scarlett_johansson")) {
                imageViewCelebrity.setBackgroundResource(R.drawable.scarlett_johansson);
            }

            if (celebrityItemModal.getValue() != null && celebrityItemModal.getValue().length() > 0){
                messageValue = Integer.valueOf(celebrityItemModal.getValue());
                textViewMessageValue.setText(String.format("$%d", messageValue));
            }
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        totalValue = messageValue + charityValue;
        textViewTotalValue.setText(String.format("$%d", totalValue));
    }

    /**
     * Created by:  Shiny Solutions
     * Created on:  10/23/17.
     */
    private void addListeners(){
        searchViewCelebrity.setOnSearchClickListener(onClickListener);
        searchViewCelebrity.setOnCloseListener(onCloseListener);
        editTextCardNumber.addTextChangedListener(textWatcher);
        editTextCardNumber.setOnKeyListener(onKeyListener);
        buttonDone.setOnClickListener(onClickListener);
        image_button_settings.setOnClickListener(onClickListener);
    }

    private void openSettings(){
        Intent intent = new Intent();
        intent.setClass(this, SettingsActivity.class);
        startActivity(intent);
    }

    /**
     * Created by:  Shiny Solutions
     * Created on:  10/23/17.
     */
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.search_view_celebrity:
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    break;
                case R.id.buttonDone:
                    Bundle arguments = new Bundle();
                    arguments.putString(PaymentConfirmationDialogFragment.EXTRAS_PAYMENT_VALUE, textViewTotalValue.getText().toString());
                    PaymentConfirmationDialogFragment paymentConfirmationDialogFragment = new PaymentConfirmationDialogFragment();
                    paymentConfirmationDialogFragment.setArguments(arguments);
                    paymentConfirmationDialogFragment.show(getSupportFragmentManager(), "PaymentConfirmation");
                    break;
                case R.id.image_button_settings:
                    openSettings();
                    break;
            }
        }
    };

    /**
     * Created by:  Shiny Solutions
     * Created on:  10/23/17.
     */
    SearchView.OnCloseListener onCloseListener = new SearchView.OnCloseListener() {
        @Override
        public boolean onClose() {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            return false;
        }
    };

    /**
     * Created by:  Shiny Solutions
     * Created on:  10/23/17
     */
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Log.i(Config.TAG, TAG + "beforeTextChanged: " + charSequence);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Log.i(Config.TAG, TAG + "onTextChanged: " + charSequence);
            String text = String.valueOf(charSequence);
            if (text.equals(" "))
                deletePressed = true;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            Log.i(Config.TAG, TAG + "afterTextChanged: " + editable);
            String text = editable.toString();
            Log.i(Config.TAG, TAG + "afterTextChanged: " + text.length());
            Log.i(Config.TAG, TAG + "afterTextChanged: " + deletePressed);
            if (!deletePressed){
                if (text.length() == 4 || text.length() == 9 || text.length() == 14){
                    editable.append(" ");
                }
            }

        }
    };

    /**
     * Created by:  Shiny Solutions
     * Created on:  10/23/17
     */
    View.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            Log.i(Config.TAG, TAG + "onKey: " + view);
            Log.i(Config.TAG, TAG + "onKey: " + i);
            Log.i(Config.TAG, TAG + "onKey: " + keyEvent.getKeyCode());
            if (i == keyEvent.KEYCODE_DEL)
                deletePressed = true;
            else
                deletePressed = false;
            return false;
        }
    };
}
