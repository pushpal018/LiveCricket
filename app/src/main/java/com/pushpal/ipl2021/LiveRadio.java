package com.pushpal.ipl2021;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
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

import java.io.IOException;

public class LiveRadio extends AppCompatActivity {

    private AdLoader adLoader;
    private boolean adLoaded = false;
    TemplateView template;
    Button hideNativeBtn;

    FirebaseDatabase database;
    DatabaseReference myRef;
    Button b_play;
    //String stram = "https://radiobhumilive.radioca.st/;stream.mp3";
    MediaPlayer mediaPlayer;

    boolean prepared =false;
    boolean started = false;


    private Boolean mytryagaing = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_radio);
        //action bar set
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Live Radio");


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
                //Toast.makeText(LiveRadio.this, "Native Ad is loaded ,now you can show the native ad  ", Toast.LENGTH_LONG).show();
            }

        }).build();


        b_play = findViewById(R.id.button);
        b_play.setEnabled(false);
        b_play.setText("loading...");

        hideNativeBtn = findViewById(R.id.hideNativeBtn);
        hideNativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideNativeAd();
            }
        });


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("radio");

        //myRef.setValue("https://radiobhumilive.radioca.st/;stream.mp3");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);

                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                new PlayerTask().execute(value);

                b_play.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        loadNativeAd();
                        showNativeAd();

                        if(started){
                            started = false;
                            mediaPlayer.pause();
                            b_play.setText("PLAY");



                        }else {
                            started =true;
                            mediaPlayer.start();
                            b_play.setText("PAUSE");
                        }


                    }

                });


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(LiveRadio.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
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
            hideNativeBtn.setVisibility(View.VISIBLE);
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

    private void hideNativeAd() {

        if (adLoaded) {
            // hiding the Native Ad when it is loaded
            template.setVisibility(View.GONE);
            hideNativeBtn.setVisibility(View.GONE);

            // Showing a simple Toast message to user when Native ad
           // Toast.makeText(LiveRadio.this, "Native Ad is hidden ", Toast.LENGTH_LONG).show();

        }
    }


    class PlayerTask extends AsyncTask<String,Void,Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {

            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.prepare();
                prepared = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return prepared;
        }

        protected void onPostExecute(Boolean aBoolean){

            super.onPostExecute(aBoolean);
            b_play.setEnabled( true);
            b_play.setText("PLAY");

        }


    }


    @Override
    protected void onPause() {
        super.onPause();
        if(started){
            mediaPlayer.start();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(started){
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(prepared){

            mediaPlayer.release();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();//go to privious activity on back pressed from actionbar too

        return super.onSupportNavigateUp();
    }
}
