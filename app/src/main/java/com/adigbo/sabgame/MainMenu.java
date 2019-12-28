package com.adigbo.sabgame;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainMenu extends AppCompatActivity {
    AdView mAdview7;
    AdView mAdview8;

    InterstitialAd interstitialAd;
    InterstitialAd interstitialAd1;
    InterstitialAd interstitialAd2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);


        MobileAds.initialize(this, "ca-app-pub-4468853554366589~7974439851");
        mAdview7 = (AdView)findViewById(R.id.adView7);
        mAdview8 = (AdView)findViewById(R.id.adView8);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        AdRequest adRequest2 = new AdRequest.Builder().build();

        mAdview7.loadAd(adRequest1);
        mAdview8.loadAd(adRequest2);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-4468853554366589/8849194362");
        interstitialAd.loadAd(new AdRequest.Builder().build());

        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed(){
                Intent intent = new Intent(getBaseContext(), NamesOfPlayers.class);
                startActivity(intent);
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
        interstitialAd1 = new InterstitialAd(this);
        interstitialAd1.setAdUnitId("ca-app-pub-4468853554366589/9567433568");
        interstitialAd1.loadAd(new AdRequest.Builder().build());

        interstitialAd1.setAdListener(new AdListener(){
            @Override
            public void onAdClosed(){
                Intent intent = new Intent(getBaseContext(), Instructions.class);
                startActivity(intent);
                interstitialAd1.loadAd(new AdRequest.Builder().build());
            }
        });
        interstitialAd2 = new InterstitialAd(this);
        interstitialAd2.setAdUnitId("ca-app-pub-4468853554366589/5180755559");
        interstitialAd2.loadAd(new AdRequest.Builder().build());

        interstitialAd2.setAdListener(new AdListener(){
            @Override
            public void onAdClosed(){
                Intent intent = new Intent(getBaseContext(), NameOfPlayer.class);
                startActivity(intent);
                interstitialAd2.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    public void names(View view) {
        if(interstitialAd.isLoaded()){
            interstitialAd.show();
        }else {
            Intent intent = new Intent(getBaseContext(), NamesOfPlayers.class);
            startActivity(intent);
        }
    }
    public void instruct(View view) {
        if(interstitialAd1.isLoaded()){
            interstitialAd1.show();
        }else {
            Intent intent = new Intent(getBaseContext(), Instructions.class);
            startActivity(intent);
        }
    }
    public void name(View view) {
        if(interstitialAd2.isLoaded()){
            interstitialAd2.show();
        }else {
            Intent intent = new Intent(getBaseContext(), NameOfPlayer.class);
            startActivity(intent);
        }
    }



}
