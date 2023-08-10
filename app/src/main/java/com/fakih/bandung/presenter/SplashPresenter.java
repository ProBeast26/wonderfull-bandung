package com.fakih.bandung.presenter;

import android.content.Context;
import android.content.Intent;

import com.fakih.bandung.activities.SlideActivity;
import com.fakih.bandung.activities.SplashActivity;

public class SplashPresenter {
    Context context;

    public SplashPresenter(SplashActivity splashActivity) {
    }

    Intent getSlide = new Intent();

    public Intent setSlide() {
        Intent intent = new Intent(context, SlideActivity.class);
        return intent;
    }
}
