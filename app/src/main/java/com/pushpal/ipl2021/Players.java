package com.pushpal.ipl2021;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Players extends AppCompatActivity {

    private Boolean mytryagaing = true;
    private AdView mAdView;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        //action bar set
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Players");


        //enable back button in action bar
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //for banner ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adview);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if(mytryagaing){
                    mAdView.loadAd(new AdRequest.Builder().build());
                    mytryagaing = false;
                }
            }
            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

        });


        webView  =findViewById(R.id.playerweb);

        Intent intent = getIntent();
        String str = intent.getStringExtra("message");

        if(str.equals("csk")){
            webView.loadUrl("file:///android_asset/csk.html");
        }

        if(str.equals("dc")){
            webView.loadUrl("file:///android_asset/dc.html");
        }
        if(str.equals("kkr")){
            webView.loadUrl("file:///android_asset/kkr.html");
        }
        if(str.equals("mi")){
            webView.loadUrl("file:///android_asset/mi.html");
        }
        if(str.equals("kxip")){
            webView.loadUrl("file:///android_asset/pbks.html");
        }
        if(str.equals("srh")){
            webView.loadUrl("file:///android_asset/srh.html");
        }
        if(str.equals("rcb")){
            webView.loadUrl("file:///android_asset/rcb.html");
        }
        if(str.equals("rr")){
            webView.loadUrl("file:///android_asset/rr.html");
        }
    }
    public boolean onSupportNavigateUp() {

        onBackPressed();//go to privious activity on back pressed from actionbar too

        return super.onSupportNavigateUp();
    }
}