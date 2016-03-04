package com.example.android.requests.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.android.requests.R;
import com.example.android.requests.fragments.Register;
import com.example.android.requests.fragments.Startup;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class MyIntro extends AppIntro {

    // Please DO NOT override onCreate. Use init.
    @Override
    public void init(Bundle savedInstanceState) {

        // Add your slide's fragments here.
        Fragment first_fragment = Startup.newInstance("n n", "nj");
        //Fragment second_fragment = Register.newInstance("n n", "nj");
        // AppIntro will automatically generate the dots indicator and buttons.

        //addSlide(third_fragment);
        //addSlide(fourth_fragment);

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        addSlide(AppIntroFragment.newInstance("title", "description", R.drawable.first, R.color.PrimaryColor));
        addSlide(AppIntroFragment.newInstance("title", "description", R.drawable.second, R.color.PrimaryColor));
        addSlide(first_fragment);
        //addSlide(second_fragment);

        setFadeAnimation();
        setZoomAnimation();
        setFlowAnimation();
        setSlideOverAnimation();
        setDepthAnimation();
        //setCustomTransformer(transformer);

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
//        showSkipButton(false);
//        setProgressButtonEnabled(false);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed() {
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed() {
        // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged() {
        // Do something when the slide changes.
    }

    @Override
    public void onNextPressed() {
        // Do something when users tap on Next button.
    }

}