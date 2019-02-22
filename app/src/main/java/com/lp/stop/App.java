package com.lp.stop;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * File descripition:
 *
 * @author lp
 * @date 2018/8/22
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/stopword.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
