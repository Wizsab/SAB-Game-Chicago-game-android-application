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

public class NamesOfPlayers extends AppCompatActivity {
    InterstitialAd interstitialAd4;
    AdView mAdview4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_names_of_players);

        MobileAds.initialize(this, "ca-app-pub-4468853554366589~7974439851");
        mAdview4 = (AdView)findViewById(R.id.adView4);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdview4.loadAd(adRequest2);

        interstitialAd4 = new InterstitialAd(this);
        interstitialAd4.setAdUnitId("ca-app-pub-4468853554366589/2818316096");
        interstitialAd4.loadAd(new AdRequest.Builder().build());

        interstitialAd4.setAdListener(new AdListener(){
            @Override
            public void onAdClosed(){
                EditText play1 = findViewById(R.id.player1);
                String nameP1 = play1.getText().toString();
                EditText play2 = findViewById(R.id.player2);
                String nameP2 = play2.getText().toString();

                Bundle basket1 = new Bundle();
                basket1.putString("key1", nameP1);
                Bundle basket2 = new Bundle();
                basket2.putString("key2", nameP2);
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtras(basket1);
                intent.putExtras(basket2);
                startActivity(intent);
                finish();
                interstitialAd4.loadAd(new AdRequest.Builder().build());
            }
        });
    }
    public void names(View view) {
        if(interstitialAd4.isLoaded()){
            interstitialAd4.show();
        }else {
            EditText play1 = findViewById(R.id.player1);
            String nameP1 = play1.getText().toString();
            EditText play2 = findViewById(R.id.player2);
            String nameP2 = play2.getText().toString();

            Bundle basket1 = new Bundle();
            basket1.putString("key1", nameP1);
            Bundle basket2 = new Bundle();
            basket2.putString("key2", nameP2);
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.putExtras(basket1);
            intent.putExtras(basket2);
            startActivity(intent);
            finish();
        }
    }

}
