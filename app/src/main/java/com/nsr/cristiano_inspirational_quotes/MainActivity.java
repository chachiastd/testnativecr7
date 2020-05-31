package com.nsr.cristiano_inspirational_quotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {
    Button btn1;
    private Button refreshButton;
    private UnifiedNativeAd nativeAd;
    AdView mAdView;
    Button Tips1,Tips2,Tips3,Tips4,Tips5;

    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        refreshButton = findViewById(R.id.bn_refreshad);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshAd();
            }
        });
        refreshAd();


        Tips1 = (Button)findViewById(R.id.btn1);
        Tips1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Tips_1.class);
                startActivity(i);
            }
        });

        Tips2 = (Button)findViewById(R.id.btn2);
        Tips2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Tips_2.class);
                startActivity(i);
            }
        });

        Tips3 = (Button)findViewById(R.id.btn3);
        Tips3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Tips_3.class);
                startActivity(i);
            }
        });

        Tips4 = (Button)findViewById(R.id.btn4);
        Tips4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Tips_4.class);
                startActivity(i);
            }
        });

        Tips5 = (Button)findViewById(R.id.btn5);
        Tips5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Tips_5.class);
                startActivity(i);
            }
        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed(){
                super.onAdClosed();
                finish();
            }
        });


    }
    //adsnative nsr
    private void refreshAd()
    {
        refreshButton.setEnabled(false);
        AdLoader.Builder builder = new AdLoader.Builder(this,getString(R.string.native_advanced_ad_unit_id));
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                if(nativeAd!=null)
                    nativeAd = unifiedNativeAd;
                CardView cardView = findViewById(R.id.ad_container);
                UnifiedNativeAdView adView =(UnifiedNativeAdView) getLayoutInflater().inflate(R.layout.native_ad_layout, null);
                populateNativeAd(unifiedNativeAd,adView);
                cardView.removeAllViews();
                cardView.addView(adView);
                refreshButton.setEnabled(true);

            }
        });

        AdLoader adLoader = builder.withAdListener(new AdListener(){
            @Override
            public void onAdFailedToLoad(int i){
                refreshButton.setEnabled(true);
                Toast.makeText(MainActivity.this,"Failed to load ad.",Toast.LENGTH_SHORT) .show();

                super.onAdFailedToLoad(i);
            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder() .build());



    }
    private void populateNativeAd(UnifiedNativeAd nativeAdView, UnifiedNativeAdView adView)
    {
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        adView.setBodyView(adView.findViewById(R.id.ad_body_text));
        adView.setStarRatingView(adView.findViewById(R.id.star_rating));
        adView.setMediaView((MediaView) adView.findViewById(R.id.media_view));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.adv_icon));

        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());
        ((TextView)adView.getHeadlineView()).setText(nativeAd.getHeadline());

        if (nativeAd.getBody()==null)
        {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        }
        else
        {
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
            adView.getBodyView().setVisibility(View.VISIBLE);
        }
        if (nativeAd.getAdvertiser()==null)
        {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        }
        else
        {
            ((TextView)adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        if (nativeAd.getStarRating()==null)
        {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        }
        else
        {
            ((RatingBar)adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }
        if (nativeAd.getIcon()==null)
        {
            adView.getIconView().setVisibility(View.GONE);
        }
        else
        {
            ((ImageView)adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }
        if (nativeAd.getCallToAction()==null)
        {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        }
        else
        {
            ((Button)adView.getIconView()).setText(nativeAd.getCallToAction());
        }
        adView.setNativeAd(nativeAd);

    }
    @Override
    protected void onDestroy() {
        if (nativeAd!=null)
            nativeAd.destroy();
        super.onDestroy();
    }
//native end

    public void showInterestial(){
        if(mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }
        else{
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showInterestial();








        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater() .inflate(R.menu.commonmenus,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.mnuhome)
        {
            Toast.makeText(this, "Home menu is Clicked", Toast.LENGTH_SHORT).show();
 startActivity(new Intent(this, Main2Activity.class));
        }
        if (id==R.id.mnusearch)
        {

            Toast.makeText(this, "Search menu is Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Main3Activity.class));

        }

        if (id==R.id.mnuclose)
        {
            Toast.makeText(this, "Close menu is Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Main4Activity.class));


        }
        return super.onOptionsItemSelected(item);


    }
}
