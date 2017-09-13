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

public class wishListActivity extends AppCompatActivity {
    databaseConnection dbcon = new databaseConnection(this);
    SharedPreferences sharepref;
    public static final String preference = "pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        sharepref = getSharedPreferences(preference, Context.MODE_PRIVATE);

        final String usrname = sharepref.getString("username", "");

        TextView Header = new TextView(this);
        Header.setGravity(Gravity.CENTER);
        Header.setText("WISH LIST");
        Header.setTextSize(25);
        Header.setTextColor(Color.parseColor("#070000"));
        Header.setTypeface(null, Typeface.BOLD);

        final Cursor results = dbcon.SearchwishListItem(usrname);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lLwishList);
        linearLayout.addView(Header);
        String path = "";
        String ClothType = "";
        String itmid = "";
        if(results.getCount()>0) {
            while (results.moveToNext()) {

                TextView textView = new TextView(this);
                TextView textView2 = new TextView(this);
                TextView textView3 = new TextView(this);
                TextView textView4 = new TextView(this);
                TextView textView5 = new TextView(this);
                ImageView imageView = new ImageView(this);
                Button shopCard = new Button(this);
                Button remove = new Button(this);

                textView.setText("Date Added : " + results.getString(0));
                textView2.setText(results.getString(1));
                textView3.setText("Size : " + results.getString(2));
                textView4.setText("Qty Remaining : " + results.getString(3));
                textView5.setText("Price : " + results.getString(4));

                itmid = results.getString(6);
                path = results.getString(5);
                int id = getResources().getIdentifier(path, "drawable", getPackageName());

                shopCard.setText("Add to Shopping Cart");
                shopCard.setTag(itmid);
                remove.setText("Remove");
                remove.setTag(itmid);

                imageView.setImageResource(id);
                shopCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dd = v.getTag().toString();
                        Toast.makeText(wishListActivity.this, "Product has been added to the Shopping Cart", Toast.LENGTH_SHORT).show();
                        dbcon.addItemstoShoppingCardTable(usrname, dd, 1);
                        dbcon.removeItemFromWishList(usrname, dd);
                        Intent i = new Intent(wishListActivity.this, wishListActivity.class);
                        startActivity(i);
                    }
                });

                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dd = v.getTag().toString();
                        Toast.makeText(wishListActivity.this, "Product has been removed from Wish List", Toast.LENGTH_SHORT).show();
                        dbcon.removeItemFromWishList(usrname, dd);
                        Intent i = new Intent(wishListActivity.this, wishListActivity.class);
                        startActivity(i);

                    }
                });
                textView.setTextColor(Color.parseColor("#0c0c0c"));
                textView2.setTextColor(Color.parseColor("#0c0c0c"));
                textView3.setTextColor(Color.parseColor("#0c0c0c"));
                textView4.setTextColor(Color.parseColor("#0c0c0c"));
                textView5.setTextColor(Color.parseColor("#0c0c0c"));
                linearLayout.addView(imageView);
                linearLayout.addView(textView);
                linearLayout.addView(textView2);
                linearLayout.addView(textView3);
                linearLayout.addView(textView4);
                linearLayout.addView(textView5);
                linearLayout.addView(shopCard);
                linearLayout.addView(remove);
                View v2 = new View(this);
                v2.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        15
                ));

                v2.setBackgroundColor(Color.parseColor("#f20707"));

                linearLayout.addView(v2);


            }
        }
        else{
            TextView textView10 = new TextView(this);
            textView10.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            textView10.setText("No Item Found");
            textView10.setPadding(40,600,40,500);
            textView10.setTextSize(50);
            linearLayout.addView(textView10);
        }

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(wishListActivity.this, Navigation.class));
        finish();

    }
}