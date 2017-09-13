package com.styleomega.styleomegafashion;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class viewOneProduct extends AppCompatActivity {
    databaseConnection dbcon=new databaseConnection(this);
    SharedPreferences sharepref;
    public static final String preference="pref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_one_product);
        Bundle bundle = getIntent().getExtras();
        final String itemid=bundle.getString("itemid");
        sharepref=getSharedPreferences(preference, Context.MODE_PRIVATE);

        final String usrname=sharepref.getString("username", "");

        final String msgtoShareSocialMedia=" from Style Omega ! Recommended by ";
        final Cursor results=dbcon.SearchOneViewProductInformation(itemid);
        final Cursor cmntsOfProduct=dbcon.SearchOneViewProductCmnts(itemid);

        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.lLviewOneProduct);
        String path="";
        String ClothType="";
        String itmid="";
        while(results.moveToNext()) {
                TextView textView = new TextView(this);
                TextView textView2 = new TextView(this);
                textView.setText(results.getString(1));
                String itmNm=results.getString(1);
                textView2.setText("Size "+results.getString(3));
                TextView textView3 = new TextView(this);
                textView3.setText("Price "+results.getString(4));

                itmid=results.getString(0);

                path = results.getString(6);
                int id = getResources().getIdentifier(path, "drawable", getPackageName());
                ImageView imageView = new ImageView(this);
                final EditText editText4 = new EditText(this);
                editText4.setTag(itmid);

                Button wishList = new Button(this);
                Button send = new Button(this);
                send.setText("Add Review");
                Button share = new Button(this);
                share.setText("Share");
                share.setTag(itmNm);
                wishList.setText("Add to WishList");
                wishList.setTag(itmid);
                Button shopCard = new Button(this);
                shopCard.setText("Add to Cart");
                shopCard.setTag(itmid);
                imageView.setImageResource(id);


                wishList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dd=v.getTag().toString();
                        Toast.makeText(viewOneProduct.this, "Product has been added to the WishList ", Toast.LENGTH_SHORT).show();
                        dbcon.addItemstoWishTable(usrname,dd);

                    }
                });

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String itemname=v.getTag().toString();
                    Intent toOpenSocialMedias=new Intent(Intent.ACTION_SEND);
                    toOpenSocialMedias.setType("text/plain");
                    toOpenSocialMedias.putExtra(Intent.EXTRA_SUBJECT,"Style Omega");
                    toOpenSocialMedias.putExtra(Intent.EXTRA_TEXT,"Try "+itemname+msgtoShareSocialMedia+usrname);
                    startActivity(Intent.createChooser(toOpenSocialMedias,"Share via"));
                }
            });

               send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String msg = editText4.getText().toString();
                    dbcon.addItemstoCmntTable(usrname,itemid,msg);
                    Toast.makeText(viewOneProduct.this, "Review Added ", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(viewOneProduct.this, viewOneProduct.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("itemid", itemid);
                    i.putExtras(bundle2);
                    startActivity(i);

                }
            });

                shopCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dd=v.getTag().toString();
                        Toast.makeText(viewOneProduct.this, "Product has been added to the Shopping Cart ", Toast.LENGTH_SHORT).show();
                        dbcon.addItemstoShoppingCardTable(usrname,dd,1);

                    }
                });
                textView.setTextColor(Color.parseColor("#0c0c0c"));
                textView2.setTextColor(Color.parseColor("#0c0c0c"));
                textView3.setTextColor(Color.parseColor("#0c0c0c"));

                linearLayout.addView(imageView);
                linearLayout.addView(textView);
                linearLayout.addView(textView2);
                linearLayout.addView(textView3);

                View v2 = new View(this);
                v2.setLayoutParams(new LinearLayout.LayoutParams(
                     LinearLayout.LayoutParams.MATCH_PARENT,
                    15
                ));

                v2.setBackgroundColor(Color.parseColor("#0612f1"));

                linearLayout.addView(v2);

                TextView productEnquiry = new TextView(this);
                productEnquiry.setText("Product Enquiry");
                productEnquiry.setTextSize(20);
                productEnquiry.setPaintFlags(productEnquiry.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                linearLayout.addView(productEnquiry);

                while(cmntsOfProduct.moveToNext()){

                    TextView usrIDCmnt = new TextView(this);
                    usrIDCmnt.setText(cmntsOfProduct.getString(0)+" : "+cmntsOfProduct.getString(2));
                    usrIDCmnt.setTextColor(Color.parseColor("#0c0c0c"));
                    linearLayout.addView(usrIDCmnt);
                }

                linearLayout.addView(editText4);
                linearLayout.addView(send);
                linearLayout.addView(share);
            if(!usrname.equals("Guest")) {
                linearLayout.addView(wishList);
                linearLayout.addView(shopCard);
            }

                View v = new View(this);
                v.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        15
                ));

                v.setBackgroundColor(Color.parseColor("#f20707"));

                linearLayout.addView(v);
            }




        }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(viewOneProduct.this, Navigation.class));
        finish();

    }

    }

