package com.styleomega.styleomegafashion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class shareInfo extends AppCompatActivity {
    final String msgtoShareSocialMedia=" from Style Omega ! Recommended by ";
    SharedPreferences sharepref;
    public static final String preference="pref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_info);
        sharepref=getSharedPreferences(preference, Context.MODE_PRIVATE);

        final String usrname=sharepref.getString("username", "");

        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.lLShareInfo);

        TextView textView = new TextView(this);
        TextView textView2 = new TextView(this);
        textView.setText("Deep Black Tshirt");
        textView2.setText("Size M ");

        int id = getResources().getIdentifier("shirt3", "drawable", getPackageName());
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(id);

        Button share = new Button(this);
        share.setText("Share");
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemname="Deep Black Tshirt";
                Intent toOpenSocialMedias=new Intent(Intent.ACTION_SEND);
                toOpenSocialMedias.setType("text/plain");
                toOpenSocialMedias.putExtra(Intent.EXTRA_SUBJECT,"Style Omega");
                toOpenSocialMedias.putExtra(Intent.EXTRA_TEXT,"Try "+itemname+msgtoShareSocialMedia+usrname);
                startActivity(Intent.createChooser(toOpenSocialMedias,"Share via"));
            }
        });

        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        linearLayout.addView(textView2);
        linearLayout.addView(share);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(shareInfo.this, Navigation.class));
        finish();

    }
}
