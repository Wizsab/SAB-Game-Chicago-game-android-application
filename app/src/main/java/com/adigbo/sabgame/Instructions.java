package com.adigbo.sabgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class Instructions extends AppCompatActivity {
    AdView mAdview5;
    AdView mAdview6;
    InterstitialAd interstitialAd6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_instuctions);

        MobileAds.initialize(this, "ca-app-pub-4468853554366589~7974439851");
        mAdview5 = (AdView)findViewById(R.id.adView5);
        mAdview6 = (AdView)findViewById(R.id.adView6);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        AdRequest adRequest2 = new AdRequest.Builder().build();

        mAdview5.loadAd(adRequest1);
        mAdview6.loadAd(adRequest2);

        interstitialAd6 = new InterstitialAd(this);
        interstitialAd6.setAdUnitId("ca-app-pub-4468853554366589/3788439370");
        interstitialAd6.loadAd(new AdRequest.Builder().build());

        interstitialAd6.setAdListener(new AdListener(){
            @Override
            public void onAdClosed(){
                startActivity(new Intent(Instructions.this, MainMenu.class));
                interstitialAd6.loadAd(new AdRequest.Builder().build());
            }
        });
        String info1 = "\n\tLEVEL:" +
                "\n\tLevel 2 is the Lowest level." +
                "\n\tLevel 12 is the Highest level.";
        String info2 = "\n\n\tGAME:" +
                "\n\tThe game begins with both players starting from level 2." +
                " When a player rolls a dice which is the same as the present level of that player,"+
                " then the player moves to the next level. The first player to reach level 12 is the winner.";

        TextView display = (TextView)findViewById(R.id.info1);
        TextView display1 = (TextView)findViewById(R.id.info2);
        display1.setText(info2);
        display.setText(info1);
    }
    public void menu(View view) {
        if(interstitialAd6.isLoaded()){
            interstitialAd6.show();
        }else {
            Intent intent = new Intent(getBaseContext(), MainMenu.class);
            startActivity(intent);
        }
    }
}

