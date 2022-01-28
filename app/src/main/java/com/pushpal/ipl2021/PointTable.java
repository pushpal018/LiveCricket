package com.pushpal.ipl2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PointTable extends AppCompatActivity {
    // creating a variable for our Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    private AdLoader adLoader;
    private boolean adLoaded = false;
    TemplateView template;


    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_table);


        //action bar set
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Point Table");


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

        loadNativeAd();
        showNativeAd();
        webView = findViewById(R.id.pointTableWeb);


        // below line is used to get the instance
        // of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("pointtable");

        // calling method to initialize
        // our web view.
        initializeWebView();
    }
    private void initializeWebView() {

        // calling add value event listener method for getting the values from database.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime updates in the data.
                // this method is called when the data is changed in our Firebase console.
                // below line is for getting the data from snapshot of our database.
                String webUrl = snapshot.getValue(String.class);

                // after getting the value for our webview url we are
                // setting our value to our webview view in below line.
                webView.loadUrl(webUrl);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(PointTable.this, "Fail to get URL.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadNativeAd() {

        // Creating  an Ad Request
        AdRequest adRequest = new AdRequest.Builder().build();

        // load Native Ad with the Request
        adLoader.loadAd(adRequest);

        // Showing a simple Toast message to user when Native an ad is Loading
        //Toast.makeText(LiveRadio.this, "Native Ad is loading ", Toast.LENGTH_LONG).show();
    }

    private void showNativeAd() {

        if (adLoaded) {
            template.setVisibility(View.VISIBLE);
            // Showing a simple Toast message to user when an Native ad is shown to the user
            //Toast.makeText(LiveRadio.this, "Native Ad  is loaded and Now showing ad  ", Toast.LENGTH_LONG).show();

        }
        else {
            //Load the Native ad if it is not loaded
            loadNativeAd();

            // Showing a simple Toast message to user when Native ad is not loaded
            //Toast.makeText(LiveRadio.this, "Native Ad is not Loaded ", Toast.LENGTH_LONG).show();

        }
    }
    public boolean onSupportNavigateUp() {

        onBackPressed();//go to privious activity on back pressed from actionbar too

        return super.onSupportNavigateUp();
    }
}