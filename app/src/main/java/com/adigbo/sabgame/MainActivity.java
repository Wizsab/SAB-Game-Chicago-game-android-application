package com.adigbo.sabgame;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.ClipDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    AdView mAdview;
    ImageView imageView;
    int tor;
    TextView player1;
    TextView player2;
    TextView turns;
    String disText;
    int com = 0;
    int imggg;
    int dice;
    int levelP1 = 2;
    int levelP2 = 2;
    Bundle gotBasket1;
    String display1;
    Bundle gotBasket2;
    String display2;
    private boolean player1Turn = true;
    private boolean player2Turn = true;

    Random  r = new Random();
    Integer[] images = {
            R.drawable.onexone, R.drawable.onextwo, R.drawable.onexthree,
            R.drawable.onexfour,  R.drawable.onexfive,  R.drawable.onexsix,
            R.drawable.twoxone, R.drawable.twoxtwo,R.drawable.twoxthree,R.drawable.twoxfour,
            R.drawable.twoxfive,R.drawable.twoxsix,R.drawable.threexone,R.drawable.threextwo,
            R.drawable.threexthree,R.drawable.threexfour,R.drawable.threexfive,R.drawable.threexsix,
            R.drawable.fourxone,R.drawable.fourxtwo,R.drawable.fourxthree,R.drawable.fourxfive,
            R.drawable.fourxfour,R.drawable.fourxsix,R.drawable.fivexone,R.drawable.fivextwo,
            R.drawable.fivexthree,R.drawable.fivexfour,R.drawable.fivexfive,R.drawable.fivexsix,
            R.drawable.sixxone,R.drawable.sixxtwo,R.drawable.sixxthree,R.drawable.sixxfour,R.drawable.sixxfive,
            R.drawable.sixxsix,R.drawable.onexone,R.drawable.twoxtwo, R.drawable.threexthree,
            R.drawable.fourxfour,R.drawable.fivexfive, R.drawable.sixxsix
    };
    ImageView img;
    ImageView img1;
    HomeWatcher mHomeWatcher;

    private ClipDrawable mImageDrawable;
    private ClipDrawable mImageDrawable1;
    Button button;
    private int mLevel = 0;
    private int fromLevel = 0;
    private int toLevel = 0;
    private int mLevel1 = 0;
    private int fromLevel1 = 0;
    private int toLevel1 = 0;
    public static final int LEVEL_DIFF = 910;
    public static final int MAX_LEVEL = 10000;
    public static final int DELAY = 30;
    Intent music;
    int m = 0;
    Button musicIcon;

    private Handler mUpHandler = new Handler();
    private Runnable animateUpImage = new Runnable() {

        @Override
        public void run() {
            doTheUpAnimation(fromLevel, toLevel);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        musicIcon = findViewById(R.id.musicIcon);
        musical();
        music = new Intent();
        music.setClass(this, MusicService.class);
        musicIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (m==1) {
                    doUnbindService();
                    stopService(music);
                    musicIcon.setBackgroundResource(R.drawable.ic_volume_up_black_24dp);
                    m-=1;
                }
                else if (m==0){
                    doBindService();
                    startService(music);
                    musicIcon.setBackgroundResource(R.drawable.ic_volume_off_black_24dp);
                    m+=1;
                }
            }
        });
        MobileAds.initialize(this, "ca-app-pub-4468853554366589~7974439851");
        mAdview = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);

        img = (ImageView) findViewById(R.id.imageView1);
         img1 = (ImageView) findViewById(R.id.imageView3);

        player2 = (TextView) findViewById(R.id.player2);
        player1 = (TextView) findViewById(R.id.player1);
        mImageDrawable = (ClipDrawable) img.getDrawable();
        mImageDrawable.setLevel(0);
        mImageDrawable1 = (ClipDrawable) img1.getDrawable();
        mImageDrawable1.setLevel(0);
         gotBasket1 = getIntent().getExtras();
         display1 = gotBasket1.getString("key1");

         gotBasket2 = getIntent().getExtras();
         display2 = gotBasket2.getString("key2");
        player1.setText(display1);
        player2.setText(display2);
        turns = (TextView)findViewById(R.id.turns);
        imageView = (ImageView) findViewById(R.id.imageView);
        roll();

    }
    public void roll(){
        Glide.with(this)
                .load(R.drawable.roll)
                .into(imageView);

    }

    public void thrown(View view){
        imageView.setImageResource(R.drawable.aft);
        player1Turn = !player1Turn;
        turnByturn();

    }
    public void show(View view){

        if(tor ==0){
        if(com == 1) {
            com--;
           diceToNum();
            checkP1Win();
            disText = display1 + " turn " + "\nlevel: " + levelP1 + "\ndice: " + dice;
            turns.setText(disText);
        }else{
            Toast.makeText(this, "Throw the dice first ", Toast.LENGTH_SHORT).show();
        }}
        if(tor==1){
            if(com == 1) {
                com--;
               diceToNum();
                checkP2Win();
                disText = display2 + " turn " + "\nlevel: " + levelP2 + "\ndice: " + dice;
                turns.setText(disText);
            }else{
                Toast.makeText(this, "Throw the dice first ", Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void doTheUpAnimation(int fromLevel, int toLevel) {
        mLevel += LEVEL_DIFF;
        mImageDrawable.setLevel(mLevel);
        if (mLevel <= toLevel) {
            mUpHandler.postDelayed(animateUpImage, DELAY);
        } else {
            mUpHandler.removeCallbacks(animateUpImage);
            MainActivity.this.fromLevel = toLevel;
        }
    }
    private void doTheUpAnimation1(int fromLevel1, int toLevel1) {
        mLevel1 += LEVEL_DIFF;
        mImageDrawable1.setLevel(mLevel1);
        if (mLevel1 <= toLevel1) {
            mUpHandler.postDelayed(animateUpImage, DELAY);
        } else {
            mUpHandler.removeCallbacks(animateUpImage);
            MainActivity.this.fromLevel1 = toLevel1;
        }
    }
    public void diceToNum(){
        imggg = images[r.nextInt(images.length)];
        imageView.setImageResource(imggg);
        imageView.setTag(imggg);
        switch(Integer.valueOf((Integer) imageView.getTag())){
            case R.drawable.onexone:
                dice = 2;
                break;
            case R.drawable.onextwo:
                dice = 3;
                break;
            case R.drawable.onexthree:
                dice = 4;
                break;
            case R.drawable.onexfour:
                dice = 5;
                break;
            case R.drawable.onexfive:
                dice = 6;
                break;
            case R.drawable.onexsix:
                dice = 7;
                break;
            case R.drawable.twoxone:
                dice = 3;
                break;
            case R.drawable.twoxtwo:
                dice = 4;
                break;
            case R.drawable.twoxthree:
                dice = 5;
                break;
            case R.drawable.twoxfour:
                dice = 6;
                break;
            case R.drawable.twoxfive:
                dice = 7;
                break;
            case R.drawable.twoxsix:
                dice = 8;
                break;
            case R.drawable.threexone:
                dice = 4;
                break;
            case R.drawable.threextwo:
                dice = 5;
                break;
            case R.drawable.threexthree:
                dice = 6;
                break;
            case R.drawable.threexfour:
                dice = 7;
                break;
            case R.drawable.threexfive:
                dice = 8;
                break;
            case R.drawable.threexsix:
                dice = 9;
                break;
            case R.drawable.fourxone:
                dice = 5;
                break;
            case R.drawable.fourxtwo:
                dice = 6;
                break;
            case R.drawable.fourxthree:
                dice = 7;
                break;
            case R.drawable.fourxfour:
                dice = 8;
                break;
            case R.drawable.fourxfive:
                dice = 9;
                break;
            case R.drawable.fourxsix:
                dice = 10;
                break;
            case R.drawable.fivexone:
                dice = 6;
                break;
            case R.drawable.fivextwo:
                dice = 7;
                break;
            case R.drawable.fivexthree:
                dice = 8;
                break;
            case R.drawable.fivexfour:
                dice = 9;
                break;
            case R.drawable.fivexfive:
                dice = 10;
                break;
            case R.drawable.fivexsix:
                dice = 11;
                break;
            case R.drawable.sixxone:
                dice = 7;
                break;
            case R.drawable.sixxtwo:
                dice = 8;
                break;
            case R.drawable.sixxthree:
                dice = 9;
                break;
            case R.drawable.sixxfour:
                dice = 10;
                break;
            case R.drawable.sixxfive:
                dice = 11;
                break;
            case R.drawable.sixxsix:
                dice = 12;
                break;
        }
    }

    public void turnByturn(){

        if(player1Turn) {
            if(com == 0) {
                com++;
                tor = 0;
                disText = display1 +" turn " + "\nlevel: " + levelP1;
                turns.setText(disText);

            }}
        else if(player2Turn){
            if(com == 0) {
                com++;
                tor = 1;
                disText = display2 + " turn " + "\nlevel: " + levelP2;
                turns.setText(disText);

            }
        }

    }
    private int checkP1Win(){
        if(dice == levelP1) {
            String msg = "Since dice " + dice + " and level is " + dice + "\n" +display1 +" has moved to the next level." ;

            new AlertDialog.Builder(this).setTitle("Next Level").
                    setMessage(msg).
                    setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            if(levelP1 < 12){
                                levelP1++;
                            doTheUpAnimation(fromLevel, toLevel);
                            }else if(levelP1 == 12){
                                doTheUpAnimation(fromLevel, toLevel);

                            }
                        }
                    }).show();
        }
        if(levelP1 == 12 && dice == 12){
            String msg = display1 + " is the Winner";

            new AlertDialog.Builder(this).setTitle("Winner").
                    setMessage(msg).
                    setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            reset();
                        }
                    }).show();
        }
        return levelP1;
    }
    private int checkP2Win(){

        if(dice == levelP2){
            String msg = "Since dice "+ dice+" and level is "  + dice + "\n" +display2 +" has moved to the next level.";

            new AlertDialog.Builder(this).setTitle("Next Level").
                    setMessage(msg).
                    setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            if(levelP2 < 12) {
                                levelP2++;
                                doTheUpAnimation1(fromLevel1, toLevel1);
                            }else if(levelP2 == 12){
                                doTheUpAnimation1(fromLevel1, toLevel1);
                            }
                        }
                    }).show();
            }
            if(levelP2 == 12 && dice == 12){
            String msg = display2 + " is the Winner";

            new AlertDialog.Builder(this).setTitle("Winner").
                    setMessage(msg).
                    setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            reset();
                        }
                    }).show();
        }
        return levelP2;
    }
    public void quit(View view){
        String msg = "You are about to Quit the Game";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Quit").
                        setMessage(msg).

        setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getBaseContext(), MainMenu.class);
                                startActivity(intent);
                                finish();
                            }
                        }).
                        setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).
                        show();
    }
    public void reset(){
        Intent intent = new Intent(getBaseContext(), MainMenu.class);
        startActivity(intent);
        finish();
    }
    public void musical(){
        doBindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);
        m+=1;
        //Start HomeWatcher
        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                if (mServ != null) {
                    mServ.pauseMusic();
                }
            }
            @Override
            public void onHomeLongPressed() {
                if (mServ != null) {
                    mServ.pauseMusic();
                }
            }
        });
        mHomeWatcher.startWatch();


    }

    //Bind/Unbind music service
    private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon,Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mServ != null) {
            mServ.resumeMusic();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Detect idle screen
        PowerManager pm = (PowerManager)
                getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = false;
        if (pm != null) {
            isScreenOn = pm.isScreenOn();
        }

        if (!isScreenOn) {
            if (mServ != null) {
                mServ.pauseMusic();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //UNBIND music service
        doUnbindService();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        stopService(music);
        m-=1;

    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("mLevel",mLevel );
        outState.putInt("mLevel1",mLevel1 );
        outState.putInt("levelP1",levelP1 );
        outState.putInt("levelP2",levelP2 );
        outState.putString("display1",display1);
        outState.putString("display2",display2);
        outState.putInt("dice",dice);
        outState.putBoolean("player1Turn",player1Turn);
        outState.putBoolean("player2Turn",player2Turn);
        outState.putInt("com",com);
        outState.putInt("m",m);
        outState.putInt("imggg",imggg);

    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        mLevel = savedInstanceState.getInt("mLevel");
        mLevel1 = savedInstanceState.getInt("mLevel1");
        levelP1 = savedInstanceState.getInt("levelP1");
        levelP2 = savedInstanceState.getInt("levelP2");
        display1 = savedInstanceState.getString("display1");
        display2 = savedInstanceState.getString("display2");
        dice = savedInstanceState.getInt("dice");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
        player2Turn = savedInstanceState.getBoolean("player2Turn");
        com = savedInstanceState.getInt("com");
        m = savedInstanceState.getInt("m");
        imggg =savedInstanceState.getInt("imggg");

    }
}
