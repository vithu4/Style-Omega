package com.styleomega.styleomegafashion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class accessories extends AppCompatActivity {
    databaseConnection dbcon = new databaseConnection(this);
    SharedPreferences sharepref;
    public static final String preference = "pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessories);

        sharepref = getSharedPreferences(preference, Context.MODE_PRIVATE);

        final String usrname = sharepref.getString("username", "");
        TextView Header = new TextView(this);
        Header.setGravity(Gravity.CENTER);
        Header.setText("ACCESSORIES");
        Header.setTextSize(25);
        Header.setTextColor(Color.parseColor("#070000"));
        Header.setTypeface(null, Typeface.BOLD);

        final Cursor results = dbcon.SearchAllProductInformation();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lLAccessDis);
        linearLayout.addView(Header);
        String path = "";
        String ClothType = "";
        String itmid = "";
        while (results.moveToNext()) {
            ClothType = results.getString(5);
            if (ClothType.equals("access")) {
                TextView textView = new TextView(this);
                TextView textView2 = new TextView(this);
                textView.setText(results.getString(1));
                textView2.setText("Size " + results.getString(3));
                TextView textView3 = new TextView(this);
                textView3.setText("Price " + results.getString(4));

                itmid = results.getString(0);

                path = results.getString(6);
                int id = getResources().getIdentifier(path, "drawable", getPackageName());
                ImageView imageView = new ImageView(this);

                Button wishList = new Button(this);

                wishList.setText("Add to WishList");
                wishList.setTag(itmid);
                Button shopCard = new Button(this);
                shopCard.setText("Add to Cart");
                shopCard.setTag(itmid);
                imageView.setImageResource(id);

                Button viewmore = new Button(this);
                viewmore.setText("View More Details");
                viewmore.setTag(itmid);

                viewmore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(accessories.this, viewOneProduct.class);
                        Bundle bundle2 = new Bundle();
                        final String itemid = v.getTag().toString();
                        bundle2.putString("itemid", itemid);
                        i.putExtras(bundle2);
                        startActivity(i);

                    }
                });


                wishList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dd = v.getTag().toString();
                        Toast.makeText(accessories.this, "Product has been added to the WishList ", Toast.LENGTH_SHORT).show();
                        dbcon.addItemstoWishTable(usrname, dd);

                    }
                });

                shopCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dd = v.getTag().toString();
                        Toast.makeText(accessories.this, "Product has been added to the Shopping Cart ", Toast.LENGTH_SHORT).show();
                        dbcon.addItemstoShoppingCardTable(usrname, dd, 1);

                    }
                });
                textView.setTextColor(Color.parseColor("#0c0c0c"));
                textView2.setTextColor(Color.parseColor("#0c0c0c"));
                textView3.setTextColor(Color.parseColor("#0c0c0c"));
                linearLayout.addView(imageView);
                linearLayout.addView(textView);
                linearLayout.addView(textView2);
                linearLayout.addView(textView3);
                linearLayout.addView(viewmore);
                linearLayout.addView(wishList);
                linearLayout.addView(shopCard);

                View v = new View(this);
                v.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        15
                ));

                v.setBackgroundColor(Color.parseColor("#f20707"));

                linearLayout.addView(v);
            }


        }

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(accessories.this, Navigation.class));
        finish();

    }
}
