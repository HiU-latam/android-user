package com.hiulatam.hiu.hiu;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.hiulatam.hiu.hiu.fragments.SampleSlideFragment;


 public class Walkthrough extends AppIntro{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_walkthrough);


        addSlide(SampleSlideFragment.newInstance(R.layout.slide1));
        addSlide(SampleSlideFragment.newInstance(R.layout.slide2));
        addSlide(SampleSlideFragment.newInstance(R.layout.slide3));


    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed() {
        super.onDonePressed();
        finish();
    }


 }
