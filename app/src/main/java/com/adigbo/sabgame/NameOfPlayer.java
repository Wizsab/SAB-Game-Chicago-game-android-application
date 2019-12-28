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
import android.widget.EditText;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class NameOfPlayer extends AppCompatActivity {
    InterstitialAd interstitialAd5;
    AdView mAdview3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_name_of_player);

        MobileAds.initialize(this, "ca-app-pub-4468853554366589~7974439851");
        mAdview3 = (AdView)findViewById(R.id.adView3);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdview3.loadAd(adRequest2);

        interstitialAd5 = new InterstitialAd(this);
        interstitialAd5.setAdUnitId("ca-app-pub-4468853554366589/8070642773");
        interstitialAd5.loadAd(new AdRequest.Builder().build());

        interstitialAd5.setAdListener(new AdListener(){
            @Override
            public void onAdClosed(){
                EditText play1 = findViewById(R.id.player1);
                String nameP1 = play1.getText().toString();

                Bundle basket1 = new Bundle();
                basket1.putString("key1", nameP1);
                Intent intent = new Intent(getBaseContext(), Computer.class);
                intent.putExtras(basket1);
                startActivity(intent);
                finish();
                interstitialAd5.loadAd(new AdRequest.Builder().build());
            }
        });
    }
    public void name(View view) {
        if(interstitialAd5.isLoaded()){
            interstitialAd5.show();
        }else {
            EditText play1 = findViewById(R.id.player1);
            String nameP1 = play1.getText().toString();

            Bundle basket1 = new Bundle();
            basket1.putString("key1", nameP1);
            Intent intent = new Intent(getBaseContext(), Computer.class);
            intent.putExtras(basket1);
            startActivity(intent);
            finish();
        }
    }
}
