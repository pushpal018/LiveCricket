package com.pushpal.ipl2021;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class LiveTv extends AppCompatActivity {

    Button tsports,starsports,skysports,channel9;

    private AdLoader adLoader;
    private boolean adLoaded = false;
    TemplateView template;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_tv);

        //action bar set
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Live TV");


        //enable back button in action bar
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        // Initializing the Google Admob SDK
        MobileAds.initialize(this, new OnInitializationCompleteListener() {@Override
        public void onInitializationComplete(InitializationStatus initializationStatus) {

            //Showing a simple Toast Message to the user when The Google AdMob Sdk Initialization is Completed
            //Toast.makeText(LiveRadio.this, "AdMob Sdk Initialize " + initializationStatus.toString(), Toast.LENGTH_LONG).show();
        }
        });

        //Initializing the AdLoader   objects
        adLoader = new AdLoader.Builder(this, "ca-app-pub-1549251657688500/5795031166").forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

            private ColorDrawable background;@Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                NativeTemplateStyle styles = new
                        NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                template = findViewById(R.id.nativeTemplateView);
                template.setStyles(styles);
                template.setNativeAd(unifiedNativeAd);
                adLoaded = true;
                // Showing a simple Toast message to user when Native an ad is Loaded and ready to show
                //Toast.makeText(PointTable.this, "Native Ad is loaded ,now you can show the native ad  ", Toast.LENGTH_LONG).show();
            }

        }).build();



        tsports = findViewById(R.id.tsports);
        starsports = findViewById(R.id.starsports);
        skysports = findViewById(R.id.skysports);
        channel9 = findViewById(R.id.channel9);



    }


    }
